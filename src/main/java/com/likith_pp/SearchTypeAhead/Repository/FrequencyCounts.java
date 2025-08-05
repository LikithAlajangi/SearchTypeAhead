package com.likith_pp.SearchTypeAhead.Repository;

import jakarta.persistence.*;

@Entity
@Table(name = "Word_Frequency")
public class FrequencyCounts {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String query;

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private int Frequency;
}
