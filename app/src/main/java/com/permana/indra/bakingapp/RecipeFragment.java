package com.permana.indra.bakingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.permana.indra.bakingapp.adapters.RecipeAdapter;
import com.permana.indra.bakingapp.pojo.Recipe;
import com.permana.indra.bakingapp.retrofit.RecipeInterface;
import com.permana.indra.bakingapp.retrofit.RetrofitBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.permana.indra.bakingapp.RecipeActivity.ALL_RECIPES;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recipe_RV);
        final RecipeAdapter recipesAdapter = new RecipeAdapter((RecipeActivity) getActivity());
        recyclerView.setAdapter(recipesAdapter);


        if (view.getTag() != null && view.getTag().equals("phone-land")) {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        RecipeInterface iRecipe = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipe>> recipe = iRecipe.getRecipe();


        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer responseCode = response.code();
                Log.d("Response code", responseCode.toString());

                ArrayList<Recipe> recipes = response.body();

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                recipesAdapter.setRecipeData(recipes, getContext());

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d("Error getting data", t.getMessage());
            }
        });

        return view;
    }
}
