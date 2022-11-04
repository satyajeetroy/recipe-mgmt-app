package com.roys.recipemgmt.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * The primary key class for the recipe_ingredients database table.
 * 
 * @author Satyajeet Roy
 * 
 */
@Embeddable
public class RecipeIngredientPK implements Serializable {

	private static final long serialVersionUID = 6073858741890810379L;

	@Column(name = "recipe_id", nullable = false)
	private int recipeId;

	@Column(name = "indgredient_name", nullable = false)
	private String indgredientName;

	public RecipeIngredientPK() {
	}

	public int getRecipeId() {
		return this.recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getIndgredientName() {
		return indgredientName;
	}

	public void setIndgredientName(String indgredientName) {
		this.indgredientName = indgredientName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(indgredientName, recipeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeIngredientPK other = (RecipeIngredientPK) obj;
		return Objects.equals(indgredientName, other.indgredientName) && recipeId == other.recipeId;
	}

}