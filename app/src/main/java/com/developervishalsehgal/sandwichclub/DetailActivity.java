package com.developervishalsehgal.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developervishalsehgal.sandwichclub.model.Sandwich;
import com.developervishalsehgal.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements Animation.AnimationListener {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView imageView;
    private View bottomScrimView;

    ProgressBar progressBar;

    TextView titleTV, descriptionTV, originTV, ingredientsTV, alsoKnownTV;

    boolean enableBackBtn = false;

    Animation fadeInAnim, slideDownAnim, slideUpAnim, slideUpAnimOne, slideOutDown, slideOutDownOne, slideOutUp, fadeOutAnim;
    LinearLayout descriptionBox, ingredientsBox, alsoKnowAsBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


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
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        initViews();
        populateUI(sandwich);
        imageView.setTransitionName(String.valueOf(position));
        Picasso.get()
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_loading)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void initViews() {

        progressBar = findViewById(R.id.thumb_progressbar);

        titleTV = findViewById(R.id.title_tv);
        descriptionTV = findViewById(R.id.description_tv);
        originTV = findViewById(R.id.origin_tv);
        ingredientsTV = findViewById(R.id.ingredients_tv);
        alsoKnownTV = findViewById(R.id.also_known_tv);

        imageView = findViewById(R.id.image_iv);
        bottomScrimView = findViewById(R.id.bottom_scrim);
        descriptionBox = findViewById(R.id.description_box_ll);
        ingredientsBox = findViewById(R.id.ingredients_box_ll);
        alsoKnowAsBox = findViewById(R.id.also_known_as_box_ll);

        fadeInAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeInAnim.setDuration(1000);
        fadeInAnim.setAnimationListener(this);

        slideDownAnim = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slideDownAnim.setDuration(1000);
        slideDownAnim.setAnimationListener(this);

        slideUpAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUpAnim.setDuration(1000);
        slideUpAnim.setAnimationListener(this);

        slideUpAnimOne = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUpAnimOne.setDuration(500);
        slideUpAnimOne.setAnimationListener(this);

        slideOutDown = AnimationUtils.loadAnimation(this, R.anim.slide_out_down);
        slideOutDown.setAnimationListener(this);

        slideOutDownOne = AnimationUtils.loadAnimation(this, R.anim.slide_out_down);
        slideOutDownOne.setAnimationListener(this);

        slideOutUp = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);
        slideOutUp.setAnimationListener(this);

        fadeOutAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        fadeOutAnim.setDuration(500);
        fadeOutAnim.setAnimationListener(this);


        bottomScrimView.startAnimation(fadeInAnim);

    }

    private void populateUI(Sandwich sandwich) {

        titleTV.setText(sandwich.getMainName());
        descriptionTV.setText(sandwich.getDescription());
        if (sandwich.getPlaceOfOrigin().isEmpty() || sandwich.getPlaceOfOrigin().equals(" ")) {
            originTV.setText(getResources().getString(R.string.not_avail));
        } else {
            originTV.setText(sandwich.getPlaceOfOrigin());
        }
        settingList(sandwich.getIngredients(), ingredientsTV);
        settingList(sandwich.getAlsoKnownAs(), alsoKnownTV);

    }

    /**
     * First have to check if the list is empty then display NOT AVAILABLE.
     *
     * @param list
     * @param textView
     */

    private void settingList(List<String> list, TextView textView) {
        if (list.isEmpty()) {
            textView.setText(getResources().getString(R.string.not_avail));
            return;
        }
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            data.append(list.get(i));
            if (i != list.size() - 1)
                data.append(",");
        }

        textView.setText(data.toString().replace(",", "\n"));

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == fadeInAnim) {
            descriptionBox.setVisibility(View.VISIBLE);
            descriptionBox.setAnimation(slideDownAnim);
        }
        if (animation == slideDownAnim) {
            ingredientsBox.setVisibility(View.VISIBLE);
            ingredientsBox.setAnimation(slideUpAnim);
        }
        if (animation == slideUpAnim) {
            alsoKnowAsBox.setVisibility(View.VISIBLE);
            alsoKnowAsBox.setAnimation(slideUpAnimOne);
            enableBackBtn = true;
        }
        if (animation == slideOutDown) {
            alsoKnowAsBox.setVisibility(View.INVISIBLE);
            ingredientsBox.setAnimation(slideOutDownOne);
        }
        if (animation == slideOutDownOne) {
            ingredientsBox.setVisibility(View.INVISIBLE);
            descriptionBox.setAnimation(slideOutUp);
        }
        if (animation == slideOutUp) {
            descriptionBox.setVisibility(View.INVISIBLE);
            bottomScrimView.startAnimation(fadeOutAnim);
        }
        if (animation == fadeOutAnim) {
            bottomScrimView.setVisibility(View.INVISIBLE);
            super.onBackPressed();
        }


    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onBackPressed() {

        if (enableBackBtn)
            alsoKnowAsBox.startAnimation(slideOutDown);

    }
}
