import os
from typing import List, Optional

from langchain_community.document_loaders import (
    PyPDFLoader,
    Docx2txtLoader,
    UnstructuredFileLoader
)
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain.schema import Document


SUPPORTED_EXTENSIONS = {".pdf", ".doc", ".docx", ".txt"}


def load_document(file_path: str) -> Optional[List[Document]]:
    """
    Load PDF, DOCX, or TXT documents and return a list of Document objects.

    Args:
        file_path (str): Path to the file.

    Returns:
        Optional[List[Document]]: List of Document objects if successful, otherwise None.
    """
    if not os.path.exists(file_path):
        print(f"Error: File '{file_path}' does not exist.")
        return None

    name, extension = os.path.splitext(file_path)
    extension = extension.lower()

    loader = None
    if extension == ".pdf":
        print(f"Loading PDF file: {file_path}")
        loader = PyPDFLoader(file_path)
    elif extension in [".doc", ".docx"]:
        print(f"Loading Word file: {file_path}")
        loader = Docx2txtLoader(file_path)
    elif extension == ".txt":
        print(f"Loading Text file: {file_path}")
        loader = UnstructuredFileLoader(file_path)
    else:
        print(f"Skipping '{file_path}' - Unsupported extension '{extension}'")
        return None

    try:
        data = loader.load()
        if not data:
            print(f"Warning: No content found in '{file_path}'.")
            return None
        return data  # List[Document]
    except Exception as e:
        print(f"Error: Failed to load '{file_path}'. Reason: {e}")
        return None


def load_documents_from_directory(directory_path: str) -> List[Document]:
    """
    Recursively scan a directory, load all supported files, and return a combined list of Documents.

    Args:
        directory_path (str): Path to the directory containing documents.

    Returns:
        List[Document]: A list of Document objects loaded from the directory.
    """
    all_documents: List[Document] = []

    if not os.path.isdir(directory_path):
        print(f"Error: '{directory_path}' is not a valid directory.")
        return all_documents

    for root, dirs, files in os.walk(directory_path):
        for file in files:
            file_path = os.path.join(root, file)
            _, ext = os.path.splitext(file_path)

            if ext.lower() in SUPPORTED_EXTENSIONS:
                docs = load_document(file_path)
                if docs:
                    all_documents.extend(docs)
            else:
                # Optionally skip unsupported extensions silently or with a print
                pass

    return all_documents


def chunk_data(
    documents: List[Document],
    chunk_size: int = 512,
    chunk_overlap: int = 20
) -> List[Document]:
    """
    Split documents into smaller chunks.

    Args:
        documents (List[Document]): The documents to be split.
        chunk_size (int): Maximum number of characters for each chunk.
        chunk_overlap (int): Overlap in characters between consecutive chunks.

    Returns:
        List[Document]: A list of Document objects after splitting.
    """
    if not documents:
        print("Warning: No documents to split.")
        return []

    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=chunk_size,
        chunk_overlap=chunk_overlap
    )

    chunks: List[Document] = []
    for doc in documents:
        split_docs = text_splitter.split_documents([doc])
        chunks.extend(split_docs)

    return chunks


def load_and_chunk_from_directory(
    directory_path: str,
    chunk_size: int = 512,
    chunk_overlap: int = 20
) -> List[Document]:
    """
    Wrapper that loads all supported files from a directory and then splits them into chunks.

    Args:
        directory_path (str): Path to the directory containing documents.
        chunk_size (int): Number of characters for each chunk.
        chunk_overlap (int): Overlap in characters between consecutive chunks.

    Returns:
        List[Document]: A list of chunked Document objects.
    """
    docs = load_documents_from_directory(directory_path)
    if not docs:
        return []
    return chunk_data(docs, chunk_size=chunk_size, chunk_overlap=chunk_overlap)

