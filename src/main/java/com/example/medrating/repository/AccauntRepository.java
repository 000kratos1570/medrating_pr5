package com.example.medrating.repository;

import com.example.medrating.models.Accaunt;
import com.example.medrating.models.User;
import org.springframework.data.repository.CrudRepository;

public interface AccauntRepository extends CrudRepository<Accaunt, Long> {
    Accaunt findByUser(User user);
}
