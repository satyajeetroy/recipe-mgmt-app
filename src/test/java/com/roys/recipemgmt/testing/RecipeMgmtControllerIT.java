package com.roys.recipemgmt.testing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roys.recipemgmt.RecipeMgmtApplication;
import com.roys.recipemgmt.dto.IngredientsDTO;
import com.roys.recipemgmt.dto.RecipeDTO;
import com.roys.recipemgmt.entity.Recipe;
import com.roys.recipemgmt.entity.RecipeIngredients;
import com.roys.recipemgmt.jpa.RecipeRepository;

@AutoConfigureMockMvc
@EnableAutoConfiguration
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RecipeMgmtApplication.class)
public class RecipeMgmtControllerIT {

	@Autowired
	private MockMvc mock;

	@Autowired
	private RecipeRepository recipeRepository;

	@Test
	public void recipeShouldBeCreated() throws Exception {
		String createReq = populateRecipeRequest();
		mock.perform(MockMvcRequestBuilders.post("/recipes").content(createReq)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());

		Optional<Recipe> rec = recipeRepository.findById(1);
		assert (rec.isPresent());
	}

	@Test
	public void allRecipeShouldBeRetrieved() throws Exception {
		createDBEntries();
		mock.perform(MockMvcRequestBuilders.get("/recipes").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200));

		List<Recipe> result = recipeRepository.findAll();
		assert (result.size() == 2);
	}

	@Test
	public void recipeShouldBeRetrievedByID() throws Exception {
		createDBEntries();
		mock.perform(MockMvcRequestBuilders.get("/recipes/{id}", 101).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void recipeShouldBeDeleted() throws Exception {
		createDBEntries();
		mock.perform(MockMvcRequestBuilders.delete("/recipes/{id}", 101).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNoContent());
		Optional<Recipe> rec = recipeRepository.findById(101);
		assert (rec.isEmpty());
	}

	@Test
	public void recipeShouldBeUpdated() throws Exception {
		createDBEntries();
		String updateReq = populateRecipePutRequest();
		mock.perform(MockMvcRequestBuilders.put("/recipes/{id}", 101).content(updateReq)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	// populate dummy request for put
	private String populateRecipePutRequest() throws JsonProcessingException {
		RecipeDTO req = new RecipeDTO();
		ObjectMapper obj = new ObjectMapper();
		req.setRecipeId(101);
		req.setRecipeName("Test");
		req.setRecipeType("Non-Veg");
		req.setRecipeServings(4);
		req.setRecipeInstructions("step1 step2 step3 step4");
		String createReq = obj.writeValueAsString(req);
		return createReq;
	}

	// populate dummy request for post
	private String populateRecipeRequest() throws JsonProcessingException {
		RecipeDTO req = new RecipeDTO();
		ObjectMapper obj = new ObjectMapper();
		List<IngredientsDTO> list = new ArrayList<>();
		IngredientsDTO dto = new IngredientsDTO();
		req.setRecipeId(1);
		req.setRecipeName("Test");
		req.setRecipeType("Veg");
		req.setRecipeServings(4);
		req.setRecipeInstructions("Instructions");
		dto.setIngredientName("ing1");
		dto.setIngredientQty(1);
		dto.setIngredientUnit("unit");
		list.add(dto);
		req.setRecipeIngredients(list);
		String createReq = obj.writeValueAsString(req);
		return createReq;
	}

	// populate dummy entries in db for retrieval
	private void createDBEntries() {
		createTestRecipe(101, "My Recipe 1", "Veg", 1, "step1");
		createTestRecipe(102, "My Recipe 2", "Non-Veg", 2, "step1 step2");
	}

	private void createTestRecipe(int id, String recName, String recType, int recServ, String recInst) {
		Recipe rec = new Recipe();
		rec.setRecipeId(id);
		rec.setRecipeName(recName);
		rec.setRecipeType(recType);
		rec.setRecipeServings(recServ);
		rec.setRecipeInstructions(recInst);
		rec.setRecipeIngredients(new HashSet<RecipeIngredients>());
		recipeRepository.save(rec);
	}

}
