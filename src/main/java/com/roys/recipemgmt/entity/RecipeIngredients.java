package com.roys.recipemgmt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Recipe Ingredients mapping table
 * 
 * @author Satyajeet Roy
 *
 */
@Entity
@IdClass(RecipeIngredientPK.class)
@Table(name = "recipe_ingredients")
public class RecipeIngredients implements Serializable {

	private static final long serialVersionUID = 6972447198857758937L;

	// @EmbeddedId
	// private RecipeIngredientPK id;

	@Id
	private int recipeId;

	@Id
	private String indgredientName;

	@Column(name = "ingredient_qty")
	private Integer ingredientQty;

	@Column(name = "ingredient_unit", length = 255)
	private String ingredientUnit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = false, insertable = false, updatable = false)
	private Recipe recipe;

	public RecipeIngredients() {
	}

	public Integer getIngredientQty() {
		return ingredientQty;
	}

	public void setIngredientQty(final Integer ingredientQty) {
		this.ingredientQty = ingredientQty;
	}

	public String getIngredientUnit() {
		return ingredientUnit;
	}

	public void setIngredientUnit(final String ingredientUnit) {
		this.ingredientUnit = ingredientUnit;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(final Recipe recipe) {
		this.recipe = recipe;
	}

	public int getRecipeId() {
		return recipeId;
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

}