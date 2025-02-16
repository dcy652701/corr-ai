import os
import numpy as np
from typing import List, Tuple
from langchain_core.documents import Document
from langchain_community.vectorstores import FAISS
from langchain_community.retrievers import BM25Retriever
from langchain.retrievers import EnsembleRetriever


class EnhancedRetriever:
    def __init__(self, vector_store: FAISS, mode: str = "hybrid"):
        """
        Initialize an enhanced retriever with hybrid capabilities.

        Args:
            vector_store: An initialized FAISS vector store.
            mode: Retrieval mode ("hybrid", "vector", or "bm25").
        """
        self.vector_store = vector_store
        self.mode = mode
        self._initialize_retrievers()

    def _initialize_retrievers(self):
        """Initialize component retrievers from LangChain."""
        # Vector retriever using FAISS
        self.vector_retriever = self.vector_store.as_retriever(
            search_type="similarity",
            search_kwargs={"k": 10, "score_threshold": 0.7}
            
        )
        # BM25 retriever from all documents in the FAISS docstore
        all_docs = list(self.vector_store.docstore._dict.values())
        self.bm25_retriever = BM25Retriever.from_documents(all_docs)
        self.bm25_retriever.k = 10
        # Ensemble retriever combining BM25 and vector retriever
        self.ensemble_retriever = EnsembleRetriever(
            retrievers=[self.bm25_retriever, self.vector_retriever],
            weights=[0.3, 0.7]
        )
    def search(self, query: str, k: int = 10) -> List[Tuple[Document, float]]:
            """
            Retrieve documents using the selected retrieval mode.
            """
            if self.mode == "vector":
                return self.vector_store.similarity_search_with_score(query, k=k)
            elif self.mode == "bm25":
                return self.bm25_retriever.search(query, k=k)
            elif self.mode == "hybrid":
                docs = self.ensemble_retriever.invoke(query)
                results = [(doc, 1.0 - idx * 0.01) for idx, doc in enumerate(docs)]
                return results[:k]
            else:
                raise ValueError(f"Unsupported retrieval mode: {self.mode}")

