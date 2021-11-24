package com.example.chucknorrisapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.chucknorrisapp.pogo.Joke;
import com.example.chucknorrisapp.pogo.Value;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;


public class JokesFragment extends Fragment implements View.OnClickListener {
    public static final String JOKES_ARRAY_KEY = "1";
    private int numberOfJokes;
    EditText editText;
    ListView listView;
    ArrayList<String> jokesArray;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            jokesArray = savedInstanceState.getStringArrayList(JOKES_ARRAY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_jokes, container, false);
        Button reloadButton = layout.findViewById(R.id.reloadButton);
        listView = layout.findViewById(R.id.listView);
        reloadButton.setOnClickListener(this);
        editText = layout.findViewById(R.id.editTextNumber);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (jokesArray != null) {
            fillListWithJokes();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putStringArrayList(JOKES_ARRAY_KEY, jokesArray);
    }

    @Override
    public void onClick(View v) {
        numberOfJokes = 0;
        String text = editText.getText().toString();
        if (!text.equals("")) {
            numberOfJokes = Integer.parseInt(text);
            //TODO if I enter more than 574 jokes i get indexOfBoundException ArrayList 574, i didn't understand why
            if (numberOfJokes > 574) {
                Toast.makeText(getActivity(), "Max amount of jokes is 574", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(getActivity(), "Please, enter a number of jokes", Toast.LENGTH_SHORT).show();
            return;
        }
        //get Json data and set it in ArrayList
        NetworkService.getInstance().getAPI().getRandomJokesWithCount(numberOfJokes).enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(@NonNull Call<Joke> call, @NonNull Response<Joke> response) {
                jokesArray = new ArrayList<>();
                List<Value> listValue;
                Joke jokeClass = response.body();
                listValue = jokeClass.getValue();
                for (int i = 0; i < numberOfJokes; i++) {
                    jokesArray.add(listValue.get(i).getJoke());
                }
                fillListWithJokes();
            }

            @Override
            public void onFailure(@NonNull Call<Joke> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error occurred while getting request!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    //set data to listview using the adapter
    private void fillListWithJokes() {
        view = getView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1, jokesArray);
        listView.setAdapter(adapter);
    }

}
