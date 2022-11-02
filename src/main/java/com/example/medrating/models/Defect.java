package com.example.medrating.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Defect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String opisanie;
    @ManyToMany
    @JoinTable(name="defect_preporat",
            joinColumns=@JoinColumn (name="defect_id"),
            inverseJoinColumns=@JoinColumn(name="preporat_id"))
    private List<Preporat> preporats;

    public Defect() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpisanie() {
        return opisanie;
    }

    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }

    public List<Preporat> getPreporats() {
        return preporats;
    }

    public void setPreporats(List<Preporat> preporats) {
        this.preporats = preporats;
    }
}
