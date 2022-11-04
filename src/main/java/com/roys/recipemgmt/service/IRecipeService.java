package com.roys.recipemgmt.service;

import java.util.List;

import com.roys.recipemgmt.dto.RecipeDTO;

/**
 * Recipe Service Interface for CRUD operation
 * 
 * @author Satyajeet Roy
 *
 */
public interface IRecipeService {

	public List<RecipeDTO> findAll();

	public RecipeDTO get(final Integer recipeId);

	public Integer create(final RecipeDTO recipeRequest);

	public void update(final Integer recipeId, final RecipeDTO recipeRequest);

	public void delete(final Integer recipeId);

	public List<RecipeDTO> searchRecipeByFilters(String recipeType, Integer recipeServings,
			List<String> ingredientsList, String instructions);

}
