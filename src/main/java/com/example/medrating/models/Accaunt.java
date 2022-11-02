package com.example.medrating.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Accaunt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String status;
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 1, message = "Фамилия должена быть больше 2 символов")
    private String familia;
    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 1, message = "Имя должено быть больше 2 символов")
    private String ima;
    private String otchestvo;

    @OneToOne(optional = true,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Accaunt() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getIma() {
        return ima;
    }

    public void setIma(String ima) {
        this.ima = ima;
    }

    public String getOtchestvo() {
        return otchestvo;
    }

    public void setOtchestvo(String otchestvo) {
        this.otchestvo = otchestvo;
    }

}
