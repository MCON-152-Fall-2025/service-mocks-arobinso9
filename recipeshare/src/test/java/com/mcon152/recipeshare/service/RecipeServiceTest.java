package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.Recipe;
import com.mcon152.recipeshare.repository.RecipeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Assignment: Implement all TODOs using Mockito features covered in class:
 *  - @Mock, @InjectMocks, @Captor, @ExtendWith(MockitoExtension.class)
 *  - Stubbing: thenReturn / thenAnswer / thenThrow
 *  - Verifications: verify(...), times/never/atLeast..., verifyNoMoreInteractions
 *  - InOrder (where meaningful)
 *  - Void stubbing: doNothing / doThrow (use deleteById for this)
 *  - Matchers: any(), eq(), argThat()
 *  - ArgumentCaptor
 *  - (Optional) Spy demo if you introduce a small helper in tests
 *
 * NOTE: This is a pure unit test. Do NOT start a Spring context.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RecipeService (Mockito) — Assignment Skeleton")
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService; // CUT implements RecipeService

    @Captor
    private ArgumentCaptor<Recipe> recipeCaptor;

    // --- Helpers for sample data ---

    private Recipe newRecipeNoId() {
        return new Recipe(
                null,
                "Chocolate Cake",
                "Moist chocolate cake",
                "flour, eggs, cocoa",
                "mix, bake",
                8
        );
    }

    private Recipe savedRecipe(long id) {
        return new Recipe(
                id,
                "Chocolate Cake",
                "Moist chocolate cake",
                "flour, eggs, cocoa",
                "mix, bake",
                8
        );
    }

    // ------------------ addRecipe ------------------

    @Nested
    @DisplayName("addRecipe(Recipe)")
    class AddRecipe {

        @Test
        @DisplayName("returns saved entity (thenReturn) and calls repository.save once")
        void returnsSaved_andSavesOnce() {
            // TODO:
            // 1) when(recipeRepository.save(...)).thenReturn(savedRecipe(1L))
            // 2) call recipeService.addRecipe(newRecipeNoId())
            // 3) assert non-null id and fields
            // 4) verify(recipeRepository).save(any(Recipe.class)); verifyNoMoreInteractions(recipeRepository)

            //See code below as an example answer

            Recipe input = newRecipeNoId();
            Recipe saved = savedRecipe(1L);

            when(recipeRepository.save(any(Recipe.class))).thenReturn(saved);

            Recipe out = recipeService.addRecipe(input);
            assertEquals(1L, out.getId());
            assertEquals(saved, out);

            verify(recipeRepository).save(any(Recipe.class));
            verifyNoMoreInteractions(recipeRepository);
        }

        @Test
        @DisplayName("assigns ID dynamically (thenAnswer) and captures argument")
        void assignsId_thenAnswer_andCaptures() {
            // TODO:
            // 1) Use thenAnswer to return a new Recipe with id=1L, copying fields from arg
            // 2) capture the arg with ArgumentCaptor and assert title, id==null pre-save

            //See code below as an example answer

            when(recipeRepository.save(any(Recipe.class))).thenAnswer(inv -> {
                Recipe r = inv.getArgument(0);
                return new Recipe(1L, r.getTitle(), r.getDescription(),
                        r.getIngredients(), r.getInstructions(), r.getServings());
            });

            Recipe out = recipeService.addRecipe(newRecipeNoId());
            assertEquals(1L, out.getId());

            verify(recipeRepository).save(recipeCaptor.capture());
            Recipe sent = recipeCaptor.getValue();
            assertNull(sent.getId()); // before persistence
            assertEquals("Chocolate Cake", sent.getTitle());
        }

        @Test
        @DisplayName("propagates repository failure (thenThrow)")
        void propagatesRepositoryFailure() {
            // Arrange
            when(recipeRepository.save(any(Recipe.class)))
                    .thenThrow(new IllegalStateException("DB down"));

            // Act & Assert
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> recipeService.addRecipe(newRecipeNoId())
            );
            assertEquals("DB down", exception.getMessage());

            // Verify
            verify(recipeRepository).save(any(Recipe.class));
            verifyNoMoreInteractions(recipeRepository);
        }
    }

    // ------------------ getAllRecipes ------------------

    @Nested
    @DisplayName("getAllRecipes()")
    class GetAllRecipes {

        @Test
        @DisplayName("returns list from repository")
        void returnsList() {
            // Arrange
            List<Recipe> recipes = List.of(
                    savedRecipe(1L),
                    savedRecipe(2L),
                    savedRecipe(3L)
            );
            when(recipeRepository.findAll()).thenReturn(recipes);

            // Act
            List<Recipe> result = recipeService.getAllRecipes();

            // Assert
            assertEquals(3, result.size());
            assertIterableEquals(recipes, result);

            // Verify
            verify(recipeRepository).findAll();
            verifyNoMoreInteractions(recipeRepository);
        }
    }

    // ------------------ getRecipeById ------------------

    @Nested
    @DisplayName("getRecipeById(long)")
    class GetById {

        @Test
        @DisplayName("returns Optional.present when found")
        void present() {
            // Arrange
            Recipe recipe = savedRecipe(1L);
            when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

            // Act
            Optional<Recipe> result = recipeService.getRecipeById(1L);

            // Assert
            assertTrue(result.isPresent());
            assertEquals(recipe, result.get());

            // Verify
            verify(recipeRepository).findById(1L);
            verifyNoMoreInteractions(recipeRepository);
        }

        @Test
        @DisplayName("returns Optional.empty when missing")
        void empty() {
            // Arrange
            when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

            // Act
            Optional<Recipe> result = recipeService.getRecipeById(999L);

            // Assert
            assertTrue(result.isEmpty());

            // Verify
            verify(recipeRepository).findById(999L);
            verifyNoMoreInteractions(recipeRepository);
        }
    }

    // ------------------ deleteRecipe ------------------

    @Nested
    @DisplayName("deleteRecipe(long)")
    class DeleteRecipe {

        @Test
        @DisplayName("returns true when entity existed")
        void returnsTrue_whenExists() {
            // Arrange
            long id = 1L;
            when(recipeRepository.existsById(id)).thenReturn(true);
            doNothing().when(recipeRepository).deleteById(id);

            // Act
            boolean result = recipeService.deleteRecipe(id);

            // Assert
            assertTrue(result);

            // Verify order
            InOrder inOrder = inOrder(recipeRepository);
            inOrder.verify(recipeRepository).existsById(id);
            inOrder.verify(recipeRepository).deleteById(id);
            verifyNoMoreInteractions(recipeRepository);
        }

        @Test
        @DisplayName("returns false when missing (never deletes)")
        void returnsFalse_whenMissing() {
            // Arrange
            long id = 999L;
            when(recipeRepository.existsById(id)).thenReturn(false);

            // Act
            boolean result = recipeService.deleteRecipe(id);

            // Assert
            assertFalse(result);

            // Verify
            verify(recipeRepository).existsById(id);
            verify(recipeRepository, never()).deleteById(anyLong());
            verifyNoMoreInteractions(recipeRepository);
        }

        @Test
        @DisplayName("propagates delete error (doThrow)")
        void propagatesDeleteError() {
            // Arrange
            long id = 1L;
            when(recipeRepository.existsById(id)).thenReturn(true);
            doThrow(new IllegalStateException("Delete error")).when(recipeRepository).deleteById(id);

            // Act & Assert
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> recipeService.deleteRecipe(id)
            );
            assertEquals("Delete error", exception.getMessage());

            // Verify
            verify(recipeRepository).existsById(id);
            verify(recipeRepository).deleteById(id);
            verifyNoMoreInteractions(recipeRepository);
        }
    }

    // ------------------ updateRecipe ------------------

    @Nested
    @DisplayName("updateRecipe(long, Recipe)")
    class UpdateRecipe {

        @Test
        @DisplayName("returns updated entity when exists")
        void returnsUpdated_whenExists() {
            // Arrange
            long id = 1L;
            Recipe existingRecipe = savedRecipe(id);
            Recipe updateInput = new Recipe(
                    id,
                    "Updated Cake",
                    "Better cake",
                    "flour, eggs, sugar",
                    "mix, bake, frost",
                    10
            );
            Recipe savedUpdated = new Recipe(
                    id,
                    "Updated Cake",
                    "Better cake",
                    "flour, eggs, sugar",
                    "mix, bake, frost",
                    10
            );

            when(recipeRepository.findById(id)).thenReturn(Optional.of(existingRecipe));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(savedUpdated);

            // Act
            Optional<Recipe> result = recipeService.updateRecipe(id, updateInput);

            // Assert
            assertTrue(result.isPresent());
            assertEquals("Updated Cake", result.get().getTitle());
            assertEquals("Better cake", result.get().getDescription());

            // Verify with capture
            verify(recipeRepository).findById(id);
            verify(recipeRepository).save(recipeCaptor.capture());

            Recipe captured = recipeCaptor.getValue();
            assertEquals(id, captured.getId());
            assertEquals("Updated Cake", captured.getTitle());
            assertEquals("Better cake", captured.getDescription());
            assertEquals(8, captured.getServings());

            verifyNoMoreInteractions(recipeRepository);
        }

        @Test
        @DisplayName("returns empty when entity missing")
        void returnsEmpty_whenMissing() {
            // Arrange
            long id = 999L;
            Recipe updateInput = new Recipe(
                    id,
                    "Updated Cake",
                    "Better cake",
                    "flour, eggs, sugar",
                    "mix, bake, frost",
                    10
            );

            when(recipeRepository.findById(id)).thenReturn(Optional.empty());

            // Act
            Optional<Recipe> result = recipeService.updateRecipe(id, updateInput);

            // Assert
            assertTrue(result.isEmpty());

            // Verify
            verify(recipeRepository).findById(id);
            verify(recipeRepository, never()).save(any(Recipe.class));
            verifyNoMoreInteractions(recipeRepository);
        }
    }

    // ------------------ patchRecipe ------------------

    @Nested
    @DisplayName("patchRecipe(long, Recipe)")
    class PatchRecipe {

        @Test
        @DisplayName("applies only non-null fields (argThat)")
        void appliesNonNullFields_only() {
            // Arrange
            long id = 1L;
            Recipe existingRecipe = savedRecipe(id);

            // Partial recipe with only title updated
            Recipe partialUpdate = new Recipe();
            partialUpdate.setTitle("Patched Cake Title");

            // Use thenAnswer to echo the saved entity with updated title
            when(recipeRepository.findById(id)).thenReturn(Optional.of(existingRecipe));
            when(recipeRepository.save(any(Recipe.class))).thenAnswer(inv -> inv.getArgument(0));

            // Act
            Optional<Recipe> result = recipeService.patchRecipe(id, partialUpdate);

            // Assert
            assertTrue(result.isPresent());
            assertEquals("Patched Cake Title", result.get().getTitle());

            // Verify using argThat to ensure only the title was updated
            verify(recipeRepository).findById(id);
            verify(recipeRepository).save(argThat(recipe ->
                    recipe.getId().equals(id) &&
                            recipe.getTitle().equals("Patched Cake Title") &&
                            recipe.getDescription().equals(existingRecipe.getDescription()) &&
                            recipe.getIngredients().equals(existingRecipe.getIngredients()) &&
                            recipe.getInstructions().equals(existingRecipe.getInstructions()) &&
                            recipe.getServings() == existingRecipe.getServings()
            ));

            verifyNoMoreInteractions(recipeRepository);
        }

        @Test
        @DisplayName("returns empty when entity missing")
        void returnsEmpty_whenMissing() {
            // Arrange
            long id = 999L;
            Recipe partialUpdate = new Recipe();
            partialUpdate.setTitle("Patched Cake Title");

            when(recipeRepository.findById(id)).thenReturn(Optional.empty());

            // Act
            Optional<Recipe> result = recipeService.patchRecipe(id, partialUpdate);

            // Assert
            assertTrue(result.isEmpty());

            // Verify
            verify(recipeRepository).findById(id);
            verify(recipeRepository, never()).save(any(Recipe.class));
            verifyNoMoreInteractions(recipeRepository);
        }
    }

    // ------------------ extra practice ------------------

    @Nested
    @DisplayName("Advanced stubbing & verification")
    class Advanced {
        @Test
        @DisplayName("consecutive stubs on existsById (true, false)")
        void consecutiveStubs_existsById() {
            // Arrange
            long id = 1L;
            when(recipeRepository.existsById(id)).thenReturn(true, false);

            // Act - Use deleteRecipe which will call existsById internally
            boolean firstDeleteAttempt = recipeService.deleteRecipe(id);
            boolean secondDeleteAttempt = recipeService.deleteRecipe(id);

            // Assert
            assertTrue(firstDeleteAttempt);  // First call should return true
            assertFalse(secondDeleteAttempt); // Second call should return false

            // Verify
            verify(recipeRepository, times(2)).existsById(id);
            verify(recipeRepository, times(1)).deleteById(id); // Called only on first attempt
            verifyNoMoreInteractions(recipeRepository);
        }

    }

}