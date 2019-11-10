package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private Sandwich theSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        theSandwich = JsonUtils.parseSandwichJson(json);
        if (theSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        if (!theSandwich.getImage().isEmpty()) {
            Picasso.with(this)
                    .load(theSandwich.getImage())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(ingredientsIv);
        } else {
            ingredientsIv.setImageResource(R.drawable.placeholder_image);
        }

        setTitle(theSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvAlsoKnownAs.setText(buildListItems(theSandwich.getAlsoKnownAs()));


        TextView tvIngredients = findViewById(R.id.ingredients_tv);
        tvIngredients.setText(buildListItems(theSandwich.getIngredients()));

        TextView tvDescription = findViewById(R.id.description_tv);
        tvDescription.setText(theSandwich.getDescription());

        TextView tvOrigins = findViewById(R.id.origin_tv);
        if (!theSandwich.getPlaceOfOrigin().isEmpty()) {
            tvOrigins.setText(theSandwich.getPlaceOfOrigin());
        } else{
            tvOrigins.setText(R.string.unknown_origin);
        }
    }

    private String buildListItems(List<String> listItems) {
        if (listItems.size() > 0) {
           return "\t" + TextUtils.join("\n\t", listItems);
        } else {
            return "\tN/A";
        }
    }
}
