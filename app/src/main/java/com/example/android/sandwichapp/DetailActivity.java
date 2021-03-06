package com.example.android.sandwichapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sandwichapp.model.Sandwich;
import com.example.android.sandwichapp.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_tv) TextView alsoKnownTv;
    @BindView(R.id.origin_tv) TextView originTv;
    @BindView(R.id.ingredients_tv) TextView ingredientsTV;
    @BindView(R.id.description_tv) TextView descriptionTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.v("details", "current san "+ sandwich);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        StringBuilder alsoKnownData = new StringBuilder();
        for (String currentAlsoKnown : sandwich.getAlsoKnownAs()) {
            if (alsoKnownData != null) {
                alsoKnownData.append("\n");
            }
            alsoKnownData.append(currentAlsoKnown);
        }
        alsoKnownTv.setText(alsoKnownData);
        originTv.setText(sandwich.getPlaceOfOrigin());

        StringBuilder ingredientsData = new StringBuilder();
        for(String currentIngridient : sandwich.getIngredients()){
            if(ingredientsData!=null){
                ingredientsData.append("\n");
            }
            ingredientsData.append(currentIngridient);
        }
        ingredientsTV.setText(ingredientsData);
        descriptionTV.setText(sandwich.getDescription());

    }
}
