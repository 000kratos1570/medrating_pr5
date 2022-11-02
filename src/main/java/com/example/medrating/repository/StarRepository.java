package com.example.medrating.repository;

import com.example.medrating.models.Star;
import org.springframework.data.repository.CrudRepository;

public interface StarRepository extends CrudRepository<Star, Long> {
}
