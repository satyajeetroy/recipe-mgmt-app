package com.roys.recipemgmt.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data transformation object for Recipe
 * 
 * @author Satyajeet Roy
 *
 */
public class RecipeDTO {

	private Integer recipeId;

	@NotNull
	@Size(max = 255)
	private String recipeName;

	@NotNull
	@Size(max = 255)
	private String recipeType;

	@NotNull
	private int recipeServings;

	@NotNull
	private String recipeInstructions;

	private List<IngredientsDTO> recipeIngredients;

	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(String recipeType) {
		this.recipeType = recipeType;
	}

	public int getRecipeServings() {
		return recipeServings;
	}

	public void setRecipeServings(int recipeServings) {
		this.recipeServings = recipeServings;
	}

	public String getRecipeInstructions() {
		return recipeInstructions;
	}

	public void setRecipeInstructions(String recipeInstructions) {
		this.recipeInstructions = recipeInstructions;
	}

	public List<IngredientsDTO> getRecipeIngredients() {
		if (recipeIngredients == null)
			recipeIngredients = new ArrayList<IngredientsDTO>();
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<IngredientsDTO> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

}
