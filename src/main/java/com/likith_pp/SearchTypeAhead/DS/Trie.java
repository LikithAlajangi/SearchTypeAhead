package com.likith_pp.SearchTypeAhead.DS;

import com.likith_pp.SearchTypeAhead.ConstantsClass;
import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import com.likith_pp.SearchTypeAhead.Repository.QueryFrequencyRepository;
import com.likith_pp.SearchTypeAhead.Suggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Trie implements SuggestionDataStructure {// in-memory data structure

    Logger log = LoggerFactory.getLogger(com.likith_pp.SearchTypeAhead.DS.Trie.class);
    private ConstantsClass constants;
    private volatile TrieNode root;
    private final QueryFrequencyRepository queryFrequencyRepository;//repo used to talk to db
    private int k = ConstantsClass.MAX_SUGGESTION_LIMIT;

    @Autowired//Spring Initializes components using construcor
    public Trie(QueryFrequencyRepository queryFrequencyRepository,Optional<Integer> maxSugg) {
        this.queryFrequencyRepository = queryFrequencyRepository;
        root = new TrieNode();
        init(maxSugg.isEmpty()?k:maxSugg.get());
    }


    @Override
    public void reload() {//sync the contents of db with the contents of cache(RAM)

        TrieNode temp = new TrieNode();

        constructTrie(temp);
       root = temp;
    }

    @Override
    public List<Suggestion> getTopSuggestions(String query) {
     //   log.info("Entered the Trie topSugg method with str: "+query);
      List<Suggestion> ans = new ArrayList<>();
      TrieNode node = root;
      for(int i=0;i<query.length();i++){
          TrieNode t = node.getChildren().get(query.charAt(i)-'a');
          if(t==null){
              log.info("null");
              return ans;
          }
          node = t;
      }
     // log.info("returning node suggestions");
      return node.getTopSuggestions();

    }




    @Override
    public void init(int maxSuggestions) {
        log.info("Trie init..........");
       int k = Math.min(maxSuggestions, ConstantsClass.MAX_QUERY_SIZE);

//       insert("apple",5,0,root);
//       insert("apex",10,0,root);
//      insert("applex",3,0,root);
//       insert("applexou",2,0,root);
//       insert("apartment",120,0,root);

      constructTrie(root);

//       List<Suggestion> ans =  root.getChildren().get(0).getTopSuggestions();
//       for(var a: ans){
//           System.out.println(a.getName());
//       }


    }

    private void constructTrie(TrieNode root) {
        List<FrequencyCounts> freqCounts = queryFrequencyRepository.findAll();//read records from DB
        //log.info("size of FreqCounts:: {}", freqCounts.size());
        //insert all the data from DB into Trie
        for(FrequencyCounts f: freqCounts) {
            insert(f.getQuery(),f.getFrequency(),0,root);
        }

        System.out.println();
    }


    public List<Suggestion> insert(String query,int popularity,int idx,TrieNode curr){

        if(idx==query.length() || idx== ConstantsClass.MAX_QUERY_SIZE){
            Set<Suggestion> suggestions = new HashSet<>(curr.getTopSuggestions());
            suggestions.add(new Suggestion(query,popularity));
            return updateSuggestionsAndGet(suggestions,curr);
        }

        if(curr.getChildren().get(query.charAt(idx)-'a')==null){
            curr.getChildren().set(query.charAt(idx)-'a',new TrieNode());
        }
        TrieNode next = curr.getChildren().get(query.charAt(idx)-'a');
        Set<Suggestion> allSuggestions = new HashSet<>();
        //check if idx==query.size()-1
        if(idx==query.length()-1){
            next.setEOS(true);
        }
        insert(query,popularity,idx+1,next);
        allSuggestions.addAll(curr.getTopSuggestions());
        for(int i=0;i<26;i++){//building curr node getTopSuggestions for children topSuggestions
            TrieNode node = curr.getChildren().get(i);
            if(node!=null){
                allSuggestions.addAll(node.getTopSuggestions());
            }
        }
        return updateSuggestionsAndGet(allSuggestions,curr);
    }

    private List<Suggestion> updateSuggestionsAndGet(Set<Suggestion> allSuggestions, TrieNode curr) {
        //taking all curr's children suggestions and gonna sort them on frequency and get top k suggestions
        List<Suggestion> allSuggestionlist = new ArrayList<>(allSuggestions);
        Collections.sort(allSuggestionlist, new Comparator<Suggestion>() {
            @Override
            public int compare(Suggestion o1, Suggestion o2) {
                return o2.getPopularity()- o1.getPopularity();
            }
        });
        List<Suggestion> finalTopSuggestions = new ArrayList<>();
        for(int i=0;i<Math.min(k,allSuggestionlist.size());i++){
            finalTopSuggestions.add(allSuggestionlist.get(i));
        }
//        log.info("data final: "+finalTopSuggestions.size());
        curr.setTopSuggestions(finalTopSuggestions);
        return curr.getTopSuggestions();//finalTopSuggestions;
    }
}
