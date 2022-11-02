package com.example.medrating.repository;

import com.example.medrating.models.Defect;
import org.springframework.data.repository.CrudRepository;

public interface DefectRepository extends CrudRepository<Defect,Long> {
}
