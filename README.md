# SearchTypeAhead
A high-performance Spring Boot application that provides real-time search suggestions using a Trie-based in-memory cache.

🔹 Loads data from MySQL (via Spring Data JPA & Hibernate) into a Trie on startup

🔹 Maintains top suggestions per prefix for efficient autocomplete

🔹 Background thread syncs cache with DB every 30 seconds

🔹 ~99.94% faster than JPQL-based DB queries
