package com.likith_pp.SearchTypeAhead;

import com.likith_pp.SearchTypeAhead.Repository.FrequencyCounts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.*;

public class Main {
    public static void main(String[] args) {

//USe main to generate Data and insert in DB
        populateDB();
    }

    private static void populateDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPUnit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

       List<FrequencyCounts> data = generateData();
        //List<FrequencyCounts> data = new ArrayList<>();
//        FrequencyCounts ex1 = new FrequencyCounts();
//        ex1.setQuery("apple");
//        ex1.setFrequency(5);
//        FrequencyCounts ex2 = new FrequencyCounts();
//        ex2.setQuery("apex");
//        ex2.setFrequency(10);
//
//        FrequencyCounts ex3 = new FrequencyCounts();
//        ex3.setQuery("applex");
//        ex3.setFrequency(3);
//        data.add(ex1);
//        data.add(ex2);
//        data.add(ex3);
         //  System.out.println("Generated data size: "+data.size());
        for(FrequencyCounts fc: data){
            em.persist(fc);//it goes @Entity FreqencyCounts DB table
        }
        em.getTransaction().commit();
        em.close();
    }

    private static List<FrequencyCounts> generateData() {
        List<FrequencyCounts> dbData = new ArrayList<>();
        Map<String, Integer> freqMap = new HashMap<>();
        String val = "abc";
        Random random = new Random();
        for(int i=0;i<10000;i++){
            int size = random.nextInt(2,7);
            String query = "";
            for(int j=0;j<size;j++){
                query += val.charAt(random.nextInt(0,3));
            }

            if(freqMap.containsKey(query)){
                freqMap.put(query, freqMap.get(query)+1);
            }
            else{
                freqMap.put(query,1);
            }
        }
        //map to Entity
        for(Map.Entry<String, Integer> freqCount : freqMap.entrySet()){
            FrequencyCounts f = new FrequencyCounts();
            f.setFrequency(freqCount.getValue());
            f.setQuery(freqCount.getKey());
            dbData.add(f);
        }

        return dbData;
    }
}
