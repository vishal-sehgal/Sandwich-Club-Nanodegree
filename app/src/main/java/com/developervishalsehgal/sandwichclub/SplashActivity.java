package com.developervishalsehgal.sandwichclub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUp();
    }

    public void setUp() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofInt(0, progressBar.getMax());
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progressBar.setProgress((Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_down);

            }
        });
        animator.start();
    }
}

