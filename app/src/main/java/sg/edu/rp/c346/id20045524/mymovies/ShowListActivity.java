package sg.edu.rp.c346.id20045524.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {
    Spinner spnRating;
    Button btnShowAll;
    ArrayList<Movie> movieList;
    ListView lv;
    CustomAdapter caMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        spnRating = findViewById(R.id.spnRating);
        btnShowAll = findViewById(R.id.btnShowAll);
        lv = findViewById(R.id.lv);

        loadSpinnerData();

        DBHelper dbh = new DBHelper(this);
        movieList = dbh.getAllMovies();
        caMovie = new CustomAdapter(this, R.layout.row, movieList);

        lv.setAdapter(caMovie);


        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList.clear();
                String ratingLabel = spnRating.getSelectedItem().toString();
                if (!ratingLabel.equals("All")){
                    movieList.addAll(dbh.getSongsByRating(ratingLabel));
                    Toast.makeText(ShowListActivity.this, ratingLabel,
                            Toast.LENGTH_SHORT).show();
                } else {
                    movieList.addAll(dbh.getAllMovies());
                }

                caMovie.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {

                Movie data = movieList.get(position); //user can select song to edit
                Intent i = new Intent(ShowListActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });



    }

    //app will autoload savedData
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OnResume", "Data changed");
        DBHelper dbh = new DBHelper(this);
        movieList.clear();
        movieList.addAll(dbh.getAllMovies());
        caMovie.notifyDataSetChanged();
        spnRating.setSelection(0);

    }

    private void loadSpinnerData() {
        ArrayList<String> ratingLabels = new ArrayList<>();
        ratingLabels.add("All");
        ratingLabels.add("G");
        ratingLabels.add("PG");
        ratingLabels.add("PG13");
        ratingLabels.add("NC16");
        ratingLabels.add("M18");
        ratingLabels.add("R21");

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ratingLabels);

        // attaching data adapter to spinner
        spnRating.setAdapter(ratingAdapter);
    }


}