package com.likith_pp.SearchTypeAhead.DS;

import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import com.likith_pp.SearchTypeAhead.Suggestion;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SuggestionDataStructure {//could be Trie or hashtable tomorrow

     void reload();//near real-time
    public List<Suggestion> getTopSuggestions(String query);


   void init(int maxSuggestions);
}
