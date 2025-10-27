package com.mcon152.recipeshare.web;

import com.mcon152.recipeshare.Recipe;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
=======
>>>>>>> 05e03bf5411e00a1685595a731c5c0211be9fda5
import com.mcon152.recipeshare.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
<<<<<<< HEAD
=======
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
>>>>>>> 05e03bf5411e00a1685595a731c5c0211be9fda5

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
<<<<<<< HEAD
=======
<<<<<<< HEAD
    private final List<Recipe> recipes = new ArrayList<>();

    private final AtomicLong counter = new AtomicLong();
    RecipeController() {}

    /**
     * Adds a new recipe to the list.
     *
     * @param recipe the recipe to add
     * @return the added recipe with its assigned ID
     */
    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        recipe.setId(counter.incrementAndGet());
        recipes.add(recipe);
        return recipe;
    }

    /**
     * Retrieves all recipes.
     *
     * @return a list of all recipes
     */
    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id the ID of the recipe to retrieve
     * @return the recipe with the specified ID, or null if not found
     */
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable long id) {
        for (Recipe recipe : recipes) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }
        return null;
    }

    /**
     * Deletes a recipe by its ID.
     *
     * @param id the ID of the recipe to delete
     * @return true if the recipe was deleted, false if not found
     */
    @DeleteMapping("/{id}")
    public boolean deleteRecipe(@PathVariable long id) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == id) {
                recipes.remove(i);
                return true;
            }
        }
        return false;
    }
    /**
     * Updates an existing recipe by its ID.
     *
     * @param id the ID of the recipe to update
     * @param updatedRecipe the updated recipe data
     * @return the updated recipe, or null if not found
     */
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable long id, @RequestBody Recipe updatedRecipe) {
        throw new UnsupportedOperationException("Update recipe not implemented");
    }

    /**
     * Partially updates an existing recipe by its ID.
     *
     * @param id the ID of the recipe to update
     * @param partialRecipe the partial recipe data to update
     * @return the updated recipe, or null if not found
     */
    @PatchMapping("/{id}")
    public Recipe patchRecipe(@PathVariable long id, @RequestBody Recipe partialRecipe) {
        throw new UnsupportedOperationException("Update recipe not implemented");
    }
}
=======
>>>>>>> 05e03bf5411e00a1685595a731c5c0211be9fda5
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Create a new recipe.
     * Returns 201 Created with Location header pointing to the new resource.
     */
    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe saved = recipeService.addRecipe(recipe);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()           // /api/recipes
                    .path("/{id}")                  // /{id}
                    .buildAndExpand(saved.getId())
                    .toUri();

            return ResponseEntity.created(location).body(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieve all recipes. 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    /**
     * Retrieve a recipe by id. 200 OK or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a recipe. 204 No Content if deleted, 404 Not Found otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        try {
            boolean deleted = recipeService.deleteRecipe(id);
            return deleted
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Replace a recipe (full update). 200 OK with updated entity or 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id, @RequestBody Recipe updatedRecipe) {
        return recipeService.updateRecipe(id, updatedRecipe)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Partial update. 200 OK with updated entity or 404 Not Found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Recipe> patchRecipe(@PathVariable long id, @RequestBody Recipe partialRecipe) {
        return recipeService.patchRecipe(id, partialRecipe)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
<<<<<<< HEAD
=======
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
>>>>>>> 05e03bf5411e00a1685595a731c5c0211be9fda5
