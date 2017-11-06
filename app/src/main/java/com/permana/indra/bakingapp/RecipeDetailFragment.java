package com.permana.indra.bakingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.permana.indra.bakingapp.adapters.RecipeDetailAdapter;
import com.permana.indra.bakingapp.pojo.Ingredient;
import com.permana.indra.bakingapp.pojo.Recipe;
import com.permana.indra.bakingapp.widget.UpdateService;

import java.util.ArrayList;
import java.util.List;

import static com.permana.indra.bakingapp.RecipeActivity.SELECTED_RECIPES;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    ArrayList<Recipe> recipe;
    String recipeName;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        recipe = new ArrayList<>();


        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);
        } else {
            recipe = getArguments().getParcelableArrayList(SELECTED_RECIPES);
        }

        List<Ingredient> ingredients = recipe.get(0).getIngredients();
        recipeName = recipe.get(0).getName();

        TextView detailTV = view.findViewById(R.id.recipe_detail_TV);

        ArrayList<String> recipeIngredientsForWidgets = new ArrayList<>();


        ingredients.forEach((a) ->
        {
            detailTV.append("\u2022 " + a.getIngredient() + "\n");
            detailTV.append("\t\t\t Quantity: " + a.getQuantity().toString() + "\n");
            detailTV.append("\t\t\t Measure: " + a.getMeasure() + "\n\n");

            recipeIngredientsForWidgets.add(a.getIngredient() + "\n" +
                    "Quantity: " + a.getQuantity().toString() + "\n" +
                    "Measure: " + a.getMeasure() + "\n");
        });

        RecyclerView recyclerView = view.findViewById(R.id.recipe_detail_RV);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        RecipeDetailAdapter mRecipeDetailAdapter = new RecipeDetailAdapter((RecipeDetailActivity) getActivity());
        recyclerView.setAdapter(mRecipeDetailAdapter);
        mRecipeDetailAdapter.setMasterRecipeData(recipe, getContext());

        //update widget
        UpdateService.startBakingService(getContext(), recipeIngredientsForWidgets);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

}
