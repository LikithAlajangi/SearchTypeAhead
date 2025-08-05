package com.likith_pp.SearchTypeAhead.Repository;

import com.likith_pp.SearchTypeAhead.Suggestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryFrequencyRepository extends JpaRepository<FrequencyCounts,Long>{

FrequencyCounts findByQuery(String query);


@Query(value = "SELECT name,frequency FROM word_frequency WHERE name LIKE CONCAT(:q, '%') ORDER BY frequency DESC LIMIT 7", nativeQuery = true)
List<Suggestion> getSuggestionsFromDBNotFromCache(@Param("q") String query);
}
