package com.permana.indra.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.permana.indra.bakingapp.adapters.RecipeDetailAdapter;
import com.permana.indra.bakingapp.pojo.Recipe;
import com.permana.indra.bakingapp.pojo.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.ListItemClickListener, RecipeStepFragment.ListItemClickListener {

    public static String ALL_RECIPES = "All_Recipes";
    public static String SELECTED_RECIPES = "Selected_Recipes";
    public static String SELECTED_STEPS = "Selected_Steps";
    public static String SELECTED_INDEX = "Selected_Index";
    public static String STACK_RECIPE_DETAIL = "STACK_RECIPE_DETAIL";
    public static String STACK_RECIPE_STEP_DETAIL = "STACK_RECIPE_STEP_DETAIL";


    private ArrayList<Recipe> recipe;
    public String recipeName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {

            Bundle selectedRecipeBundle = getIntent().getExtras();

            recipe = new ArrayList<>();
            recipe = selectedRecipeBundle.getParcelableArrayList(SELECTED_RECIPES);
            recipeName = recipe.get(0).getName();

            final RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(selectedRecipeBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

            if (findViewById(R.id.recipe_linear_layout).getTag() != null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {

                final RecipeStepFragment fragment2 = new RecipeStepFragment();
                fragment2.setArguments(selectedRecipeBundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container2, fragment2).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();

            }


        } else {
            recipeName = savedInstanceState.getString("Title");
        }


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipeName);

    }


    @Override
    public void onListItemClick(List<Step> stepsOut, int selectedItemIndex, String recipeName) {


        final RecipeStepFragment fragment = new RecipeStepFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        getSupportActionBar().setTitle(recipeName);

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS, (ArrayList<Step>) stepsOut);
        stepBundle.putInt(SELECTED_INDEX, selectedItemIndex);
        stepBundle.putString("Title", recipeName);
        fragment.setArguments(stepBundle);

        if (findViewById(R.id.recipe_linear_layout).getTag() != null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();

        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title", recipeName);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container2) == null) {
            if (fm.getBackStackEntryCount() > 1) {
                //go back to "Recipe Detail" screen
                fm.popBackStack(STACK_RECIPE_DETAIL, 0);
            } else if (fm.getBackStackEntryCount() > 0) {
                //go back to "Recipe" screen
                finish();
            }
        } else {
            finish();
            super.onBackPressed();
        }
    }
}
