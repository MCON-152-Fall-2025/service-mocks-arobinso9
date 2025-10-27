package com.mcon152.recipeshare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testCreateRecipe() {
<<<<<<< HEAD
        Recipe recipe = new Recipe(1L, "Cake", "Delicious cake", "Flour, Sugar, Eggs", "Mix and bake");
=======
        Recipe recipe = new Recipe(1L, "Cake", "Delicious cake", "Flour, Sugar, Eggs", "Mix and bake", 8);
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
        assertEquals(1L, recipe.getId());
        assertEquals("Cake", recipe.getTitle());
        assertEquals("Delicious cake", recipe.getDescription());
        assertEquals("Flour, Sugar, Eggs", recipe.getIngredients());
        assertEquals("Mix and bake", recipe.getInstructions());
<<<<<<< HEAD
=======
        assertEquals(8, recipe.getServings());
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
    }

    @Test
    void testReadRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        recipe.setTitle("Pie");
        recipe.setDescription("Apple pie");
        recipe.setIngredients("Apples, Flour, Sugar");
        recipe.setInstructions("Mix and bake");
<<<<<<< HEAD
=======
        recipe.setServings(6);
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
        assertEquals(2L, recipe.getId());
        assertEquals("Pie", recipe.getTitle());
        assertEquals("Apple pie", recipe.getDescription());
        assertEquals("Apples, Flour, Sugar", recipe.getIngredients());
        assertEquals("Mix and bake", recipe.getInstructions());
<<<<<<< HEAD
=======
        assertEquals(6, recipe.getServings());
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
    }

    @Test
    void testUpdateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Bread");
        recipe.setDescription("Simple bread");
        recipe.setIngredients("Flour, Water, Yeast");
        recipe.setInstructions("Mix and bake");
<<<<<<< HEAD
        recipe.setTitle("Whole Wheat Bread");
        recipe.setDescription("Healthy bread");
        assertEquals("Whole Wheat Bread", recipe.getTitle());
        assertEquals("Healthy bread", recipe.getDescription());
=======
        recipe.setServings(2);
        recipe.setTitle("Whole Wheat Bread");
        recipe.setDescription("Healthy bread");
        recipe.setServings(4);
        assertEquals("Whole Wheat Bread", recipe.getTitle());
        assertEquals("Healthy bread", recipe.getDescription());
        assertEquals(4, recipe.getServings());
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
    }

    @Test
    void testDeleteRecipe() {
<<<<<<< HEAD
        Recipe recipe = new Recipe(3L, "Soup", "Hot soup", "Water, Vegetables", "Boil");
=======
        Recipe recipe = new Recipe(3L, "Soup", "Hot soup", "Water, Vegetables", "Boil", 2);
>>>>>>> d72216e26235d7e0812b44fe5c346e1092aa89a6
        recipe = null;
        assertNull(recipe);
    }
}
