package com.permana.indra.bakingapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.permana.indra.bakingapp.pojo.Recipe;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus on 30/10/2017.
 */

public class RetrofitBuilder {
    static RecipeInterface recipeI;

    public static RecipeInterface Retrieve() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        recipeI = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(builder.build())
                .build()
                .create(RecipeInterface.class);

        return recipeI;
    }
}
