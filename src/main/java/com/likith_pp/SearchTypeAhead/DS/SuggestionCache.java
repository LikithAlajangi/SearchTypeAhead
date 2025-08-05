package com.likith_pp.SearchTypeAhead.DS;
import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import com.likith_pp.SearchTypeAhead.Suggestion;
import java.util.List;

public interface SuggestionCache {//we can implement topKSuggestion class or All suggestions Cache
    public List<Suggestion> getTopSuggestions(String query);
}
