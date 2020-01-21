package com.example.apiproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonService {
    String BASE_URL = "https://pokeapi.co/api/v2/";

    @GET("{number}/")
    Call<Pokemon> getPokemonInformation(@Path("number") String number);
}
