package com.likith_pp.SearchTypeAhead.Controller;

import com.likith_pp.SearchTypeAhead.QueryUpdater;
import com.likith_pp.SearchTypeAhead.Suggestion;
import com.likith_pp.SearchTypeAhead.SuggestionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/SearchAhead")
public class Controller {

    @Autowired
   private final SuggestionManager suggestionManager;
    private final QueryUpdater queryUpdater;


    public Controller(SuggestionManager suggestionManager,QueryUpdater q) {
        this.suggestionManager = suggestionManager;
        this.queryUpdater = q;
    }

    @RequestMapping(method = RequestMethod.GET,path="/suggestions")
    public List<Suggestion> getSuggestions(@RequestParam String word){
        if(word==null || word.isEmpty()){//Data Validation
            throw new RuntimeException("Invalid Query");
        }
        long st = System.nanoTime();
        List<Suggestion> ans= suggestionManager.getSuggestions(word);//166,
        //List<Suggestion> ans= suggestionManager.getsuggusingJPQL(word);//214,
        long ed = System.nanoTime();

        System.out.println("Time elapsed: "+ (ed-st));

        return ans;
    }

    @RequestMapping(method = RequestMethod.POST,path = "/update")
    public ResponseEntity<String> UpdateQuery(@RequestParam String query){
        if(query==null || query.isEmpty()){
          return new ResponseEntity<>("Error Data", HttpStatusCode.valueOf(400));
        }
        queryUpdater.update(query);
        return ResponseEntity.ok("Query update succesfully");
    }
}
