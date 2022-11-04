package com.roys.recipemgmt.dto;

import java.util.List;

public class SearchRequest {

	private String recipeType;

	private int recipeServings;

	private List<String> ingredients;

	private String textSearch;

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

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public String getTextSearch() {
		return textSearch;
	}

	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}

}
