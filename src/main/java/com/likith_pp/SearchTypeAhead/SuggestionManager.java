package com.likith_pp.SearchTypeAhead;


//import com.likith_pp.SearchTypeAhead.DS.Trie;
import com.likith_pp.SearchTypeAhead.DS.SuggestionCache;
import com.likith_pp.SearchTypeAhead.DS.SuggestionDataStructure;
import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import com.likith_pp.SearchTypeAhead.Repository.QueryFrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuggestionManager {//talks to Trie(CacheDataStrucutre implementation)


        private final SuggestionCache suggestionCache;
        private final QueryFrequencyRepository queryFrequencyRepository;
    @Autowired
    public SuggestionManager(SuggestionCache suggestionCache, QueryFrequencyRepository queryFrequencyRepository) {
        this.suggestionCache = suggestionCache;
        this.queryFrequencyRepository = queryFrequencyRepository;
    }

    public List<Suggestion> getSuggestions(String word){

        if(word.length()>ConstantsClass.MAX_QUERY_SIZE){//Business side Validations
            throw new RuntimeException("Query length limit exceed");
        }
        word = word.toLowerCase();
        return suggestionCache.getTopSuggestions(word);

    }

    public  List<Suggestion> getsuggusingJPQL(String query){
        //System.out.println("Using JPQL fetch");
        return queryFrequencyRepository.getSuggestionsFromDBNotFromCache(query);
    }
}
