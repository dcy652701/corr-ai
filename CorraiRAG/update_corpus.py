import os
from dotenv import load_dotenv
from langchain_community.vectorstores import FAISS
from langchain_openai import OpenAIEmbeddings
from utils import load_and_chunk_from_directory  # Assuming you have this utility function

# Load environment variables
load_dotenv()

def get_embedding():
    """
    Returns an OpenAI embedding model object.
    """
    return OpenAIEmbeddings(openai_api_key=os.getenv("OPENAI_API_KEY"))

def create_embeddings_faiss(
    vector_db_path: str,
    embedding_name: str,
    chunks: list
) -> FAISS:
    """
    Build a FAISS vector store from Document chunks, then save it to disk.

    Args:
        vector_db_path (str): Path to the folder where FAISS index will be stored.
        embedding_name (str): Name of the embedding type to use (e.g., "openai").
        chunks (list): List of document chunks to embed.

    Returns:
        FAISS: The FAISS vector store instance.
    """
    embeddings = get_embedding()
    db = FAISS.from_documents(chunks, embeddings)

    if not os.path.isdir(vector_db_path):
        os.mkdir(vector_db_path)

    db.save_local(folder_path=vector_db_path)
    return db

def update_faiss_index(new_data_folder: str, old_data_folder: str, vector_db_path: str):
    """
    Update FAISS index with new data, move processed data to old_data_folder,
    and delete processed data from new_data_folder.

    Args:
        new_data_folder (str): Path to folder containing new data files.
        old_data_folder (str): Path to folder where processed data will be archived.
        vector_db_path (str): Path to the folder where FAISS index is stored.
    """
    # Load and chunk documents from new_data_folder
    chunked_docs = load_and_chunk_from_directory(new_data_folder, chunk_size=512, chunk_overlap=30)
    if not chunked_docs:
        print("No documents were loaded or chunked.")
        return

    # Load existing FAISS index
    vector_store = FAISS.load_local(
        folder_path=vector_db_path,
        embeddings=get_embedding(),
        allow_dangerous_deserialization=True
    )

    # Add new documents to FAISS index
    vector_store.add_documents(chunked_docs)

    # Save updated FAISS index
    vector_store.save_local(folder_path=vector_db_path)

    # Move processed files from new_data_folder to old_data_folder
    for file_name in os.listdir(new_data_folder):
        src_path = os.path.join(new_data_folder, file_name)
        dst_path = os.path.join(old_data_folder, file_name)
        os.rename(src_path, dst_path)

    # Optionally, delete processed files from new_data_folder
    for file_name in os.listdir(new_data_folder):
        file_path = os.path.join(new_data_folder, file_name)
        os.remove(file_path)

    print("FAISS index updated successfully.")

def main():
    # Define directories
    new_data_folder = "new_data"
    old_data_folder = "old_data"
    vector_db_path = "faiss_index"

    # Update FAISS index with new data and manage directories
    update_faiss_index(new_data_folder, old_data_folder, vector_db_path)

if __name__ == "__main__":
    main()
