package com.roys.recipemgmt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.roys.recipemgmt.component.RecipeSpecification;
import com.roys.recipemgmt.dto.IngredientsDTO;
import com.roys.recipemgmt.dto.RecipeDTO;
import com.roys.recipemgmt.entity.Recipe;
import com.roys.recipemgmt.entity.RecipeIngredients;
import com.roys.recipemgmt.jpa.RecipeRepository;

/**
 * Service class for Recipe
 * 
 * @author Satyajeet Roy
 *
 */
@Service
public class RecipeService implements IRecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private RecipeSpecification recipeSpecification;

	/**
	 * Get all Recipes from DB
	 * 
	 * @return List of {@link RecipeDTO}
	 */
	@Override
	public List<RecipeDTO> findAll() {
		return recipeRepository.findAll(Sort.by("recipeId")).stream().map(recipe -> mapToDTO(recipe, new RecipeDTO()))
				.collect(Collectors.toList());
	}

	/**
	 * Get Recipe using recipe id
	 * 
	 * @param recipeId
	 * @return {@link RecipeDTO}
	 */
	@Override
	public RecipeDTO get(final Integer recipeId) {
		return recipeRepository.findById(recipeId).map(recipe -> mapToDTO(recipe, new RecipeDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	/**
	 * Create a new Recipe into DB
	 * 
	 * @param recipeRequest
	 * @return {@link Integer}
	 */
	@Override
	public Integer create(final RecipeDTO recipeRequest) {
		final Recipe recipe = new Recipe();
		mapToEntity(recipeRequest, recipe);
		return recipeRepository.save(recipe).getRecipeId();
	}

	/**
	 * Update an existing Recipe identified by unique recipe id
	 * 
	 * @param recipeId
	 * @param recipeRequest
	 */
	@Override
	public void update(final Integer recipeId, final RecipeDTO recipeRequest) {
		final Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		mapToEntity(recipeRequest, recipe);
		recipeRepository.save(recipe);
	}

	/**
	 * Deletes a Recipe by its unique recipe id
	 * 
	 * @param recipeId
	 */
	@Override
	public void delete(final Integer recipeId) {
		recipeRepository.deleteById(recipeId);
	}

	/**
	 * search recipes using filters
	 * 
	 * @param textSearch
	 */
	@Override
	public List<RecipeDTO> searchRecipeByFilters(String recipeType, Integer recipeServings,
			List<String> ingredientsList, String instructions) {
		return recipeRepository
				.findAll(recipeSpecification.conditionalSearchForRecipe(recipeType, recipeServings, ingredientsList,
						instructions))
				.stream().map(recipe -> mapToDTO(recipe, new RecipeDTO())).collect(Collectors.toList());
	}

	/**
	 * Maps Recipe DTO object from Recipe Entity
	 * 
	 * @param recipe
	 * @param recipeDTO
	 * @return {@link RecipeDTO}
	 */
	private RecipeDTO mapToDTO(final Recipe recipe, final RecipeDTO recipeDTO) {
		recipeDTO.setRecipeId(recipe.getRecipeId());
		recipeDTO.setRecipeName(recipe.getRecipeName());
		recipeDTO.setRecipeServings(recipe.getRecipeServings());
		recipeDTO.setRecipeType(recipe.getRecipeType());
		recipeDTO.setRecipeInstructions(recipe.getRecipeInstructions());
		recipeDTO.setRecipeIngredients(populateIngredientsDTO(recipe, recipeDTO));
		return recipeDTO;
	}

	/**
	 * Populates Ingredients DTO object from Recipe Entity
	 * 
	 * @param recipe
	 * @param recipeDTO
	 * @return List of {@link IngredientsDTO} object
	 */
	private List<IngredientsDTO> populateIngredientsDTO(Recipe recipe, RecipeDTO recipeDTO) {
		List<IngredientsDTO> ingList = recipeDTO.getRecipeIngredients();
		Set<RecipeIngredients> ingredientsDTOs = recipe.getRecipeIngredients();
		for (RecipeIngredients ingredients : ingredientsDTOs) {
			IngredientsDTO ingDto = new IngredientsDTO();
			ingDto.setIngredientName(ingredients.getIndgredientName());
			ingDto.setIngredientQty(ingredients.getIngredientQty());
			ingDto.setIngredientUnit(ingredients.getIngredientUnit());
			ingList.add(ingDto);
		}
		return ingList;
	}

	/**
	 * Maps Recipe Entity from Recipe DTO request object
	 * 
	 * @param recipeDTO
	 * @param recipe
	 * @return {@link Recipe}
	 */
	private Recipe mapToEntity(final RecipeDTO recipeDTO, final Recipe recipe) {
		if (Objects.isNull(recipe.getRecipeId()))
			recipe.setRecipeId(recipeDTO.getRecipeId());

		recipe.setRecipeName(recipeDTO.getRecipeName());

		recipe.setRecipeServings(recipeDTO.getRecipeServings());

		recipe.setRecipeType(recipeDTO.getRecipeType());

		recipe.setRecipeInstructions(recipeDTO.getRecipeInstructions());

		if (Objects.isNull(recipe.getRecipeIngredients()) || recipe.getRecipeIngredients().size() == 0)
			recipe.setRecipeIngredients(populateRecipeIngredientsEntity(recipeDTO, recipe));
		else {
			recipe.setRecipeIngredients(updateRecipeIngredientsEntity(recipeDTO, recipe));
		}

		return recipe;
	}

	private Set<RecipeIngredients> updateRecipeIngredientsEntity(RecipeDTO recipeDTO, Recipe recipe) {
		Set<RecipeIngredients> ingredientsSet = recipe.getRecipeIngredients();
		List<IngredientsDTO> ingredientsList = recipeDTO.getRecipeIngredients();
		Set<RecipeIngredients> ingDTOSet = new HashSet<>();

		for (RecipeIngredients recipeIng : ingredientsSet) {
			for (IngredientsDTO ingDTO : ingredientsList) {
				if (recipeIng.getIndgredientName().equals(ingDTO.getIngredientName())) {
					recipeIng.setIngredientQty(ingDTO.getIngredientQty());
					recipeIng.setIngredientUnit(ingDTO.getIngredientUnit());
				} else {
					RecipeIngredients recIng = new RecipeIngredients();
					recIng.setRecipe(recipe);
					recIng.setRecipeId(recipeDTO.getRecipeId());
					recIng.setIndgredientName(ingDTO.getIngredientName());
					recIng.setIngredientUnit(ingDTO.getIngredientUnit());
					recIng.setIngredientQty(ingDTO.getIngredientQty());
					ingDTOSet.add(recIng);
				}
			}
		}
		ingredientsSet.addAll(ingDTOSet);
		return ingredientsSet;
	}

	/**
	 * Populates Recipe Ingredients Entity from Recipe DTO request object
	 * 
	 * @param recipeDTO
	 * @param recipe
	 * @return Set of {@link RecipeIngredients}
	 */
	private Set<RecipeIngredients> populateRecipeIngredientsEntity(RecipeDTO recipeDTO, Recipe recipe) {
		Set<RecipeIngredients> ingredientsSet = new HashSet<>();
		List<IngredientsDTO> ingredientsList = recipeDTO.getRecipeIngredients();
		for (IngredientsDTO ingredient : ingredientsList) {
			RecipeIngredients recIng = new RecipeIngredients();
			recIng.setRecipe(recipe);
			recIng.setRecipeId(recipeDTO.getRecipeId());
			recIng.setIndgredientName(ingredient.getIngredientName());
			recIng.setIngredientUnit(ingredient.getIngredientUnit());
			recIng.setIngredientQty(ingredient.getIngredientQty());
			ingredientsSet.add(recIng);
		}
		return ingredientsSet;
	}
}