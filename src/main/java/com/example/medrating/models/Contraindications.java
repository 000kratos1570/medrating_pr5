package com.example.medrating.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Contraindications {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String contr;

    @ManyToMany
    @JoinTable(name="сontraindications_preporat",
            joinColumns=@JoinColumn (name="сontraindications_id"),
            inverseJoinColumns=@JoinColumn(name="preporat_id"))
    private List<Preporat> preporats;

    public Contraindications() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContr() {
        return contr;
    }

    public void setContr(String contr) {
        this.contr = contr;
    }

    public List<Preporat> getPreporats() {
        return preporats;
    }

    public void setPreporats(List<Preporat> preporats) {
        this.preporats = preporats;
    }
}
