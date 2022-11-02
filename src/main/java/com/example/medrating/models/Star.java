package com.example.medrating.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double starName;

    @OneToMany(mappedBy = "star", fetch = FetchType.EAGER)
    private Collection<Preporat> preporats;

    public Star() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getStarName() {
        return starName;
    }

    public void setStarName(double starName) {
        this.starName = starName;
    }

    public Collection<Preporat> getPreporats() {
        return preporats;
    }

    public void setPreporats(Collection<Preporat> preporats) {
        this.preporats = preporats;
    }
}
