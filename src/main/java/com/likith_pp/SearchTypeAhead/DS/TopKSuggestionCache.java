package com.likith_pp.SearchTypeAhead.DS;

import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import com.likith_pp.SearchTypeAhead.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopKSuggestionCache implements SuggestionCache{
    private final SuggestionDataStructure suggestionDataStructure;//COde against interface rather than directly TRIE

    @Autowired
    public TopKSuggestionCache(SuggestionDataStructure suggestionDataStructure) {
        this.suggestionDataStructure = suggestionDataStructure;
    }

    @Override
    public List<Suggestion> getTopSuggestions(String query) {
        return suggestionDataStructure.getTopSuggestions(query);
    }
}
