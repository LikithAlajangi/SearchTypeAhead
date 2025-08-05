package com.likith_pp.SearchTypeAhead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SyncThreadStarter implements CommandLineRunner {

    @Autowired
    private CacheToDBSyncManager cacheToDBSyncManager;

    @Override
    public void run(String... args) throws Exception {
        Thread t = new Thread(cacheToDBSyncManager);
        t.setName("CacheToDBSyncManager");
        t.start();
    }
}
