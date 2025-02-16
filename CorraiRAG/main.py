import os
from typing import List, Tuple
from enum import Enum

from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from EnhancedRetriever import EnhancedRetriever
# GPT branch dependencies
from langchain_community.chat_models import ChatOpenAI
from langchain.prompts import PromptTemplate
from langchain_openai import OpenAIEmbeddings
from langchain.chains import LLMChain
from langchain.docstore.document import Document
from langchain_community.vectorstores.faiss import FAISS, DistanceStrategy

# Custom utility for loading and chunking documents
from utils import load_and_chunk_from_directory

# DeepSeek branch dependency using the OpenAI package
from openai import OpenAI

# Load environment variables from .env file
load_dotenv()

# Define supported model types
class ModelType(str, Enum):
    gpt = "gpt"
    deepseek = "deepseek"

# Define the request body model
class QueryRequest(BaseModel):
    chat_history: List[dict]
    model: ModelType

MULTI_TURN_PROMPT_TEMPLATE = """
You are a helpful assistant.

Conversation History:
{history_context}

Current Question:
{question}

Retrieved Context:
{retrieved_context}

Instructions:
- Provide a clear, detailed, and accurate answer.
- If the Retrieved Context is not empty and makes sense to you, base your answer on it and you must add proper referencs.
- If the Retrieved Context is empty or mostly irrelevant/noisy, ignore it and answer the question solely based on your internal knowledge.

Answer:
"""


MULTI_TURN_PROMPT = PromptTemplate(
    input_variables=["history_context", "retrieved_context", "question"],
    template=MULTI_TURN_PROMPT_TEMPLATE
)

def format_chat_history(chat_history: List[dict]) -> Tuple[str, str]:
    """
    Format the chat history into a single string for context,
    and extract the current question from the last user message.
    Returns a tuple: (history_context, current_question)
    """
    if not chat_history:
        return "", ""
    
    history_lines = []
    for message in chat_history:
        role = message.get("role", "").capitalize()
        content = message.get("content", "")
        history_lines.append(f"{role}: {content}")
    
    # Assume the last message from the user is the current question
    if chat_history[-1].get("role", "").lower() == "user":
        current_question = chat_history[-1].get("content", "")
        history_context = "\n".join(history_lines[:-1])
    else:
        current_question = ""
        history_context = "\n".join(history_lines)
    
    return history_context, current_question

def get_embedding():
    """
    Get OpenAI Embeddings for text vectorization.
    """
    return OpenAIEmbeddings(openai_api_key=os.getenv("OPENAI_API_KEY"))

def create_embeddings_faiss(vector_db_path: str, chunks: List[Document]) -> FAISS:
    """
    Build a FAISS vector database from document chunks and save it locally.
    """
    embeddings = get_embedding()
    db = FAISS.from_documents(chunks, embeddings, normalize_L2=True, distance_strategy=DistanceStrategy.MAX_INNER_PRODUCT)
    if not os.path.isdir(vector_db_path):
        os.mkdir(vector_db_path)
    db.save_local(folder_path=vector_db_path)
    return db



def load_embeddings_faiss(vector_db_path: str) -> FAISS:
    """
    Load a previously saved FAISS vector database from local storage.
    """
    embeddings = get_embedding()
    db = FAISS.load_local(
        folder_path=vector_db_path,
        embeddings=embeddings,
        allow_dangerous_deserialization=True
    )
    return db

def gpt_generate_answer(history_context: str, current_question: str, retriever: EnhancedRetriever, retrieved_context: str) -> str:
    """
    Generate an answer using the GPT model via LangChain, leveraging the EnhancedRetriever.
    """
    
    llm = ChatOpenAI(
        model="gpt-3.5-turbo",
        temperature=0,
        openai_api_key=os.getenv("OPENAI_API_KEY"),
        base_url=os.getenv("OPENAI_BASE_URL")
    )
    
    chain = LLMChain(llm=llm, prompt=MULTI_TURN_PROMPT)
    answer = chain.run({
        "history_context": history_context,
        "retrieved_context": retrieved_context,
        "question": current_question
    })
    return answer

def deepseek_generate_answer(history_context: str, current_question: str, retriever: EnhancedRetriever, retrieved_context: str) -> str:
    """
    Generate an answer using the DeepSeek Reasoner model via its API, leveraging the EnhancedRetriever.
    """
    
    
    content_message = (
        f"Conversation History:\n{history_context}\n\n"
        f"Current Question:\n{current_question}\n\n"
        f"Retrieved Context:\n{retrieved_context}\n\n"
        f"Instructions:\n"
        f"- If the \"Retrieved Context\" section is not empty and makes sense to you, base your answer on it and reference the source names (in parentheses) where applicable.\n"
        f"- If the \"Retrieved Context\" section is empty or mostly irrelevant/noisy, ignore it and answer the question solely based on your internal knowledge.\n"
        f"- Provide a clear, detailed, and accurate answer.\n"
        f"- Do not fabricate sources or information.\n\n"
        f"Answer:"
    )
    
    
    client = OpenAI(api_key=os.getenv("DEEPSEEK_API_KEY"), base_url="https://api.deepseek.com")
    messages = [
        {"role": "system", "content": "You are a helpful assistant."},
        {"role": "user", "content": content_message}
    ]
    
    try:
        response = client.chat.completions.create(
            model="deepseek-reasoner",
            messages=messages
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"DeepSeek API call failed: {str(e)}")
    
    answer = response.choices[0].message.content
    return answer

# Initialize the FastAPI app
app = FastAPI()

@app.post("/generate_answer/")
async def generate_answer(request: QueryRequest):
    # Extract chat history and model selection from the request
    chat_history = request.chat_history
    model_choice = request.model
    
    # Format chat history into history_context and current question
    history_context, current_question = format_chat_history(chat_history)
    
    if not current_question:
        raise HTTPException(status_code=400, detail="No current question found in chat history.")
    
    # Load or create the FAISS index
    data_directory = "new_data"  # Directory where documents are stored
    vector_db_path = "faiss_index"  # Directory to store FAISS index
    if not os.path.exists(vector_db_path):
        # Load and chunk documents from data directory
        chunked_docs = load_and_chunk_from_directory(
            directory_path=data_directory,
            chunk_size=512,
            chunk_overlap=30
        )
        if not chunked_docs:
            raise HTTPException(status_code=400, detail="No documents were loaded or chunked.")
        # Move processed files from new_data_folder to old_data_folder using move but not rename
        for file_name in os.listdir(data_directory):
            src_path = os.path.join(data_directory, file_name)
            dst_path = os.path.join("old_data", file_name)
            os.rename(src_path, dst_path)
        
            
        for file_name in os.listdir(data_directory):
            file_path = os.path.join(data_directory, file_name)
            os.remove(file_path)
        
        create_embeddings_faiss(vector_db_path, chunked_docs)
        vector_store = load_embeddings_faiss(vector_db_path)
    else:
        vector_store = load_embeddings_faiss(vector_db_path)
        
    enhanced_retriever = EnhancedRetriever(vector_store, mode="vector")
    
    hybrid_results = enhanced_retriever.search(current_question, k=5)
    hybrid_results = [result for result in hybrid_results if result[1] >= 0.75]
    
    retrieved_context_parts = []
    for doc, score in hybrid_results:
        source_label = doc.metadata.get("source", "Unknown")
        content = doc.page_content
        retrieved_context_parts.append(f"[{source_label} - Score: {score:.2f}]\n{content}")
    retrieved_context = "\n\n".join(retrieved_context_parts)
    # Generate answer based on the selected model
    if model_choice == ModelType.gpt:
        answer = gpt_generate_answer(history_context, current_question, enhanced_retriever, retrieved_context)
    elif model_choice == ModelType.deepseek:
        answer = deepseek_generate_answer(history_context, current_question, enhanced_retriever, retrieved_context)
    else:
        raise HTTPException(status_code=400, detail="Unsupported model selection.")
    

    return {'answer': answer}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
