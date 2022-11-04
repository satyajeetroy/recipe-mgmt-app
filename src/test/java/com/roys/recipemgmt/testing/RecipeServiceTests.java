package com.roys.recipemgmt.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.roys.recipemgmt.RecipeMgmtApplication;
import com.roys.recipemgmt.dto.IngredientsDTO;
import com.roys.recipemgmt.dto.RecipeDTO;
import com.roys.recipemgmt.entity.Recipe;
import com.roys.recipemgmt.entity.RecipeIngredients;
import com.roys.recipemgmt.jpa.RecipeRepository;
import com.roys.recipemgmt.service.RecipeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RecipeMgmtApplication.class)
public class RecipeServiceTests {

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private RecipeService recipeService;

	private RecipeDTO recipeDTO;

	private Recipe recipe;

	@BeforeEach
	public void setup() {
		createRecipeDTO();
		createRecipeObject();
	}

	private void createRecipeObject() {
		recipe = new Recipe();
		recipe.setRecipeId(1);
		recipe.setRecipeName("Test");
		recipe.setRecipeType("Veg");
		recipe.setRecipeServings(4);
		recipe.setRecipeInstructions("Instructions");
		recipe.setRecipeIngredients(new HashSet<RecipeIngredients>());
	}

	private void createRecipeDTO() {
		recipeDTO = new RecipeDTO();
		List<IngredientsDTO> list = new ArrayList<>();
		IngredientsDTO dto = new IngredientsDTO();
		recipeDTO.setRecipeId(1);
		recipeDTO.setRecipeName("Test");
		recipeDTO.setRecipeType("Veg");
		recipeDTO.setRecipeServings(4);
		recipeDTO.setRecipeInstructions("Instructions");
		dto.setIngredientName("ing1");
		dto.setIngredientQty(1);
		dto.setIngredientUnit("unit");
		list.add(dto);
		recipeDTO.setRecipeIngredients(list);
	}

	@Test
	public void testGetRecipeByID() {
		when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipe));
		RecipeDTO actual = recipeService.get(new Random().ints(1, 10).findFirst().getAsInt());
		assertTrue(actual.getRecipeId().equals(recipeDTO.getRecipeId()));
		verify(recipeRepository, times(1)).findById(anyInt());
	}

	@Test
	public void testGetAllRecipe() {
		when(recipeRepository.findAll(Sort.by("recipeId"))).thenReturn(List.of(recipe));
		assertTrue(recipeService.findAll().size() == 1);
		verify(recipeRepository, times(1)).findAll(Sort.by("recipeId"));
	}

	@Test
	public void testCreateRecipe() {
		when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
		assertTrue(recipeService.create(recipeDTO).equals(recipe.getRecipeId()));
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}

	@Test
	public void testUpdateRecipe() {
		when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(recipe));
		when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
		recipeService.update(anyInt(), recipeDTO);
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		verify(recipeRepository, times(1)).findById(anyInt());
	}

	@Test
	public void testDeleteRecipe() {
		doNothing().when(recipeRepository).deleteById(anyInt());
		recipeService.delete(anyInt());
		verify(recipeRepository, times(1)).deleteById(anyInt());
	}

}
