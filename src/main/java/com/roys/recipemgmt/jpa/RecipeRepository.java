package com.roys.recipemgmt.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.roys.recipemgmt.entity.Recipe;

/**
 * 
 * @author Satyajeet Roy
 *
 */
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {

}
