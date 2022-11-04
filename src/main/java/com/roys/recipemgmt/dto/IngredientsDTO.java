package com.roys.recipemgmt.dto;

import javax.validation.constraints.NotNull;

/**
 * Data transformation object for Ingredients
 * 
 * @author Satyajeet Roy
 *
 */
public class IngredientsDTO {

	@NotNull
	private String ingredientName;

	private int ingredientQty;

	private String ingredientUnit;

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public int getIngredientQty() {
		return ingredientQty;
	}

	public void setIngredientQty(int ingredientQty) {
		this.ingredientQty = ingredientQty;
	}

	public String getIngredientUnit() {
		return ingredientUnit;
	}

	public void setIngredientUnit(String ingredientUnit) {
		this.ingredientUnit = ingredientUnit;
	}

}
