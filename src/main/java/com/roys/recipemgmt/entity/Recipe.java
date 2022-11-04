package com.roys.recipemgmt.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Recipe Entity class
 * 
 * @author Satyajeet Roy
 *
 */
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

	private static final long serialVersionUID = 390172844889982595L;

	@Id
	// @SequenceGenerator(name="RECIPE_RECIPEID_GENERATOR",
	// sequenceName="RECIPE_SEQUENCE_ID", allocationSize = 1, initialValue = 100000)
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,
	// generator="RECIPE_RECIPEID_GENERATOR")
	@Column(name = "recipe_id", unique = true, nullable = false)
	private Integer recipeId;

	@Column(nullable = false, length = 255)
	private String recipeName;

	@Column(nullable = false)
	private Integer recipeServings;

	@Column(nullable = false)
	private String recipeType;

	@Column(nullable = false)
	private String recipeInstructions;

	@OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<RecipeIngredients> recipeIngredients;

	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(final Integer recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(final String recipeName) {
		this.recipeName = recipeName;
	}

	public Integer getRecipeServings() {
		return recipeServings;
	}

	public void setRecipeServings(final Integer recipeServings) {
		this.recipeServings = recipeServings;
	}

	public String getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(final String recipeType) {
		this.recipeType = recipeType;
	}

	public String getRecipeInstructions() {
		return recipeInstructions;
	}

	public void setRecipeInstructions(final String recipeInstructions) {
		this.recipeInstructions = recipeInstructions;
	}

	public Set<RecipeIngredients> getRecipeIngredients() {
		return this.recipeIngredients;
	}

	public void setRecipeIngredients(Set<RecipeIngredients> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public RecipeIngredients addRecipeIngredient(RecipeIngredients recipeIngredient) {
		getRecipeIngredients().add(recipeIngredient);
		recipeIngredient.setRecipe(this);

		return recipeIngredient;
	}

	public RecipeIngredients removeRecipeIngredient(RecipeIngredients recipeIngredient) {
		getRecipeIngredients().remove(recipeIngredient);
		recipeIngredient.setRecipe(null);

		return recipeIngredient;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Recipe:" + "\n");
		sb.append("\t recipeId:" + recipeId + "\n");
		sb.append("\t recipeName:" + recipeName + "\n");
		sb.append("\t recipeServings:" + recipeServings + "\n");
		sb.append("\t recipeType:" + recipeType + "\n");
		sb.append("\t recipeInstructions:" + recipeInstructions + "\n");
		for (RecipeIngredients r : this.getRecipeIngredients()) {
			sb.append("\t" + "\t" + "Ingredients:");
			sb.append("\t" + "\t" + "\t" + "indgredientName" + r.getIndgredientName() + "\n");
			sb.append("\t" + "\t" + "\t" + "ingredientQty" + r.getIngredientQty() + "\n");
			sb.append("\t" + "\t" + "\t" + "ingredientUnit" + r.getIngredientUnit() + "\n");
		}
		return sb.toString();
	}

}