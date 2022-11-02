package com.example.medrating.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Preporat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int mg;
    private int count;
    @ManyToMany
    @JoinTable (name="effect_preporat",
            joinColumns=@JoinColumn (name="preporat_id"),
            inverseJoinColumns=@JoinColumn(name="effect_id"))
    private List<Effect> effects;

    @ManyToMany
    @JoinTable (name="defect_preporat",
            joinColumns=@JoinColumn (name="preporat_id"),
            inverseJoinColumns=@JoinColumn(name="defect_id"))
    private List<Defect> defects;

    @ManyToMany
    @JoinTable (name="сontraindications_preporat",
            joinColumns=@JoinColumn (name="preporat_id"),
            inverseJoinColumns=@JoinColumn(name="сontraindications_id"))
    private List<Contraindications> contraindications;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Maker maker;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Star star;

    public Preporat() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMg() {
        return mg;
    }

    public void setMg(int mg) {
        this.mg = mg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public List<Defect> getDefects() {
        return defects;
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
    }

    public List<Contraindications> getContraindications() {
        return contraindications;
    }

    public void setContraindications(List<Contraindications> contraindications) {
        this.contraindications = contraindications;
    }

    public Maker getMakers() {
        return maker;
    }

    public void setMakers(Maker makers) {
        this.maker = makers;
    }

    public Star getStars() {
        return star;
    }

    public void setStars(Star stars) {
        this.star = stars;
    }
}
