package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.IngredientRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface IngredientRepository extends CrudRepository<IngredientRecord, String> {
}
