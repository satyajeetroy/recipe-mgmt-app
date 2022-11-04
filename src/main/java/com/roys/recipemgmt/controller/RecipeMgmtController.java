package com.roys.recipemgmt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roys.recipemgmt.dto.ErrorResponse;
import com.roys.recipemgmt.dto.RecipeDTO;
import com.roys.recipemgmt.service.IRecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller class for Recipe Management App.
 * 
 * @author Satyajeet Roy
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeMgmtController {

	@Autowired
	private IRecipeService recipeService;

	@Operation(summary = "Get all recipes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found all Recipe", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping
	public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
		return ResponseEntity.ok(recipeService.findAll());
	}

	@Operation(summary = "Get a recipe by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the Recipe", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping("/{recipeId}")
	public ResponseEntity<RecipeDTO> getRecipe(
			@Parameter(description = "id of recipe to be searched") @PathVariable final Integer recipeId) {
		return ResponseEntity.ok(recipeService.get(recipeId));
	}

	@Operation(summary = "Add a new Recipe")
	@ApiResponse(responseCode = "201", description = "Recipe Added")
	@PostMapping
	public ResponseEntity<Integer> createRecipe(@RequestBody @Valid final RecipeDTO recipeDTO) {
		return new ResponseEntity<>(recipeService.create(recipeDTO), HttpStatus.CREATED);
	}

	@Operation(summary = "Update recipe identified by recipe id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "200", description = "Recipe Updated", content = @Content) })
	@PutMapping("/{recipeId}")
	public ResponseEntity<Void> updateRecipe(@PathVariable final Integer recipeId,
			@RequestBody @Valid final RecipeDTO recipeDTO) {
		recipeService.update(recipeId, recipeDTO);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Delete a Recipe by its unique id")
	@ApiResponse(responseCode = "204", description = "Recipe delete action performed")
	@DeleteMapping("/{recipeId}")
	public ResponseEntity<Void> deleteRecipe(@PathVariable final Integer recipeId) {
		recipeService.delete(recipeId);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Search a recipe using various filter")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found Recipe", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	@GetMapping("/search")
	public ResponseEntity<List<RecipeDTO>> searchRecipesUsingFilters(@RequestParam @Nullable String recipeType,
			@RequestParam @Nullable Integer recipeServings, @RequestParam @Nullable List<String> ingredientsList,
			@RequestParam @Nullable String instructions) {
		return ResponseEntity
				.ok(recipeService.searchRecipeByFilters(recipeType, recipeServings, ingredientsList, instructions));
	}
}