package com.example.medrating.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 1, message = "Название должен быть больше 1 символа")
    private String name;

    @OneToMany(mappedBy = "maker", fetch = FetchType.EAGER)
    private Collection<Preporat> preporats;

    public Maker() {
    }

    public Maker(String name, Collection<Preporat> preporats) {
        this.name = name;
        this.preporats = preporats;
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

    public Collection<Preporat> getPreporats() {
        return preporats;
    }

    public void setPreporats(Collection<Preporat> preporats) {
        this.preporats = preporats;
    }
}
