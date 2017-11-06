package com.permana.indra.bakingapp.retrofit;

import com.permana.indra.bakingapp.pojo.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by asus on 30/10/2017.
 */

public interface RecipeInterface {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
