package com.likith_pp.SearchTypeAhead.DS;

import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import com.likith_pp.SearchTypeAhead.Suggestion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrieNode {
    public boolean isEOS() {
        return EOS;
    }

    public void setEOS(boolean EOS) {
        this.EOS = EOS;
    }

    private boolean EOS;//ENd of string

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    private int popularity;

    public List<TrieNode> getChildren() {
        return children;
    }

    private final List<TrieNode> children;

    public List<Suggestion> getTopSuggestions() {
        return topSuggestions;
    }

    public void setTopSuggestions(List<Suggestion> topSuggestions) {
        this.topSuggestions = topSuggestions;
    }

    private List<Suggestion> topSuggestions; //=
    public TrieNode(){
        EOS= false;
        popularity=0;
        children = new ArrayList<>(26);
        for(int i=0;i<26;i++){
            this.children.add(null);
        }
        topSuggestions= new ArrayList<Suggestion>();
    }


}
