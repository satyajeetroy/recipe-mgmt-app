package com.roys.recipemgmt.component;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.roys.recipemgmt.entity.Recipe;
import com.roys.recipemgmt.entity.RecipeIngredients;

/**
 * Specification class for Recipe
 * 
 * @author Satyajeet Roy
 *
 */
@Component
public class RecipeSpecification {

	/**
	 * Filter specification for type of recipe.
	 * 
	 * @param dishType
	 * @return
	 */
	public Specification<Recipe> isVegetarianRecipe(String dishType) {
		return (root, query, cb) -> cb.like(root.get("recipeType"), dishType);
	}

	/**
	 * Filter specification based on number of servings
	 * 
	 * @param servings
	 * @return
	 */
	public Specification<Recipe> noOfServings(Integer servings) {
		return (root, query, cb) -> cb.equal(root.get("recipeServings"), servings);
	}

	/**
	 * Checks given search text within recipe instructions
	 * 
	 * @param searchText
	 * @return
	 */
	public Specification<Recipe> textSearchInstructions(String searchText) {
		return (root, query, cb) -> cb.like(root.get("recipeInstructions"), "%" + searchText + "%");
	}

	/**
	 * Searches for Recipe ingredient using IN query after joining recipe ingredients table.
	 * 
	 * @param ingredientsList
	 * @return
	 */

	public Specification<Recipe> textSearchIngredientList(List<String> ingredientsList) {
		if (CollectionUtils.isEmpty(ingredientsList))
			return null;

		return (root, query, cb) -> {
			Join<Recipe, RecipeIngredients> recipeIngJoin = root.join("recipeIngredients", JoinType.INNER);
			return recipeIngJoin.get("indgredientName").in(ingredientsList);
		};
	}

	/**
	 * Search for recipes based on various filters.
	 * 
	 * @param recipeType
	 * @param recipeServings
	 * @param ingredientsList
	 * @param instructions
	 * @param searchText
	 * @return
	 */
	public Specification<Recipe> conditionalSearchForRecipe(String recipeType, Integer recipeServings,
			List<String> ingredientsList, String instructions) {
		Specification<Recipe> spec = null;

		if (recipeType != null && !recipeType.isEmpty())
			spec = where(isVegetarianRecipe(recipeType));

		if (recipeServings != null && recipeServings.intValue() > 0)
			spec = where(noOfServings(recipeServings));

		if (ingredientsList != null)
			spec = where(textSearchIngredientList(ingredientsList));

		if (instructions != null && !instructions.isEmpty())
			spec = where(textSearchInstructions(instructions));

		return spec;
	}

}
