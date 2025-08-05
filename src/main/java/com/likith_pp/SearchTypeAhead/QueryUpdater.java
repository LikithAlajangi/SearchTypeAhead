package com.likith_pp.SearchTypeAhead;

import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import com.likith_pp.SearchTypeAhead.Repository.QueryFrequencyRepository;
import org.springframework.stereotype.Component;

import javax.management.Query;
@Component
public class QueryUpdater {

    private final QueryFrequencyRepository queryFrequencyRepository;
    private CacheToDBSyncManager cacheToDBSyncManager;

    public QueryUpdater(QueryFrequencyRepository q){
        queryFrequencyRepository = q;
    }

    public synchronized void update(String query){
        FrequencyCounts f = queryFrequencyRepository.findByQuery(query);
        if(f!=null){
            FrequencyCounts nf = new FrequencyCounts();
            nf.setQuery(query);
            nf.setFrequency(f.getFrequency()+1);
            this.queryFrequencyRepository.delete(f);
            this.queryFrequencyRepository.save(nf);
        }
        else{
            FrequencyCounts nf = new FrequencyCounts();
            nf.setQuery(query);
            nf.setFrequency(1);
            this.queryFrequencyRepository.save(nf);
        }
    }
}
