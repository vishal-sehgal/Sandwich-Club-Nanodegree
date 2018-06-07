package com.developervishalsehgal.sandwichclub;


import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.developervishalsehgal.sandwichclub.adapter.CustomListAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Making a drawable of 80x80.
        Drawable drawables = getResources().getDrawable(R.drawable.app_logo);
        drawables.setBounds(0, 0, 50, 50);
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//        Drawable finalDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 80, true));

//        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(drawables);// Setting that drawable to toolbar.


        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        CustomListAdapter adapter = new CustomListAdapter(this, sandwiches);


        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position, view);
            }
        });
    }

    private void launchDetailActivity(int position, View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        view.setTransitionName(String.valueOf(position));

        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, view, view.getTransitionName());
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.github:
                try {
                    String url = getResources().getString(R.string.github_repo_url);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {

                    Toast.makeText(this, getResources().getString(R.string.browser_not_found), Toast.LENGTH_LONG).show();
                }

                break;
            default:
                break;
        }

        return true;
    }
}
