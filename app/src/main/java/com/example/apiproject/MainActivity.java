package com.example.apiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Pokemon> pokemonList;
    private PokemonService pokemonService;
    private PokeAdapter pokeAdapter;
    private Pokemon poke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i = 1; i <= 807;i++){
            getPokemonByIdNumber(i);
        }

        pokeAdapter = new PokeAdapter(pokemonList);
        listView = findViewById(R.id.listView_main_thing);
        listView.setAdapter(pokeAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PokemonService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokemonService pokemonService = retrofit.create(PokemonService.class);
    }


    private class PokeAdapter extends ArrayAdapter<Pokemon> {
        // make an instance variable to keep track of the hero list
        private List<Pokemon> pokemonList;

        public PokeAdapter(List<Pokemon> pokemonList) {
            // since we're in the herolistactivity class, we already have the context
            // we're hardcoding in a paticular layout, so we don't need to put it in
            // the constructor either
            super(MainActivity.this, -1, pokemonList);
            this.pokemonList = pokemonList;
        }
        // the goal of the adapter is to link your list to the listview
        // and tell the listview where each aspect of the list item goes
        // so we override a method called
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1. inflate a layout
            LayoutInflater inflater = getLayoutInflater();

            // check if convertView is null
            // if so we'll replace it
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_pokemon, parent, false);
            }

            // 2. wire widgets and link the hero to those widgets
            //instead of calling it from the activity class level
            // we're calling it from the inflated layout to find those widgets
            TextView textViewName = convertView.findViewById(R.id.textView_item_name2);
            TextView textViewRank = convertView.findViewById(R.id.textView_item_id2);
            TextView textViewDesc = convertView.findViewById(R.id.textView_item_gen2);

            // to get the hero you need out of the list
            Pokemon pokemon = pokemonList.get(position);
            // and set the values for the widgets
                textViewName.setText(String.valueOf(pokemon.getName()));
                textViewDesc.setText(String.valueOf(pokemon.getHeight()));
                textViewRank.setText(String.valueOf(pokemon.getId()));
            // 3. return inflated view, use the position parameter variable
            return convertView;
        }
    }
    private Pokemon getPokemonByIdNumber(int idNumber) {
        Call<Pokemon> pokemonCall = pokemonService.getPokemonInformation(String.valueOf(idNumber));

        pokemonCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                //Any code that depends on the results of the search
                //has to go here

                Pokemon foundPokemon = response.body();
                //check if the body isn't null
                if (foundPokemon != null) {

                    poke = foundPokemon;
                    pokemonList.add(poke);


                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                //Toast the failure
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return poke;
    }
}
