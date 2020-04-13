package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView nameView;
    TextView knownAsView;
    TextView knownAsLabel;
    TextView ingredientsView;
    TextView placeOfOriginView;
    TextView placeOfOriginViewLabel;
    TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        nameView = findViewById(R.id.name);
        knownAsView = findViewById(R.id.known_as_text);
        knownAsLabel = findViewById(R.id.also_known_tv_label);
        placeOfOriginView = findViewById(R.id.place_of_origin);
        placeOfOriginViewLabel = findViewById(R.id.place_of_origin_label);
        ingredientsView = findViewById(R.id.ingredients_tv);
        descriptionView = findViewById(R.id.description_detail);

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
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        nameView.setText(sandwich.getMainName());

        if (sandwich.getAlsoKnownAs() != null && !sandwich.getAlsoKnownAs().isEmpty()) {
            knownAsView.setVisibility(View.VISIBLE);
            knownAsLabel.setVisibility(View.VISIBLE);

            for (String name : sandwich.getAlsoKnownAs()) {
                knownAsView.append(" " + name);
            }
            knownAsView.append("\n");
        }

        if (sandwich.getIngredients() != null && !sandwich.getIngredients().isEmpty()) {
            for (String ingredient : sandwich.getIngredients()) {
                ingredientsView.append(ingredient + "\n");
            }
        } else {
            ingredientsView.setVisibility(View.GONE);
        }

        descriptionView.setText(sandwich.getDescription());

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginView.setText(sandwich.getPlaceOfOrigin());
            placeOfOriginView.append("\n");
            placeOfOriginView.setVisibility(View.VISIBLE);
            placeOfOriginViewLabel.setVisibility(View.VISIBLE);
        }
    }
}
