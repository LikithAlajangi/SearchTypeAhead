package com.likith_pp.SearchTypeAhead;

import com.likith_pp.SearchTypeAhead.DS.SuggestionDataStructure;
import com.likith_pp.SearchTypeAhead.Repository.QueryFrequencyRepository;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheToDBSyncManager implements Runnable
{

    private final SuggestionDataStructure suggestionDataStructure;

    @Autowired
    public CacheToDBSyncManager(SuggestionDataStructure s) {
        this.suggestionDataStructure = s;
    }


    @Override
    public void run() {

sync();
    }

    public void sync() {
        while (true) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Initalizing sync, DB to Cache");
            suggestionDataStructure.reload();
            System.out.println("Completed the sync, DB to Cache");
        }
    }
}
