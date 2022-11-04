package com.roys.recipemgmt.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roys.recipemgmt.entity.RecipeIngredientPK;
import com.roys.recipemgmt.entity.RecipeIngredients;

/**
 * 
 * @author Satyajeet Roy
 *
 */
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredients, RecipeIngredientPK> {
}
