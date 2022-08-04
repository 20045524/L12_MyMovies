package sg.edu.rp.c346.id20045524.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog.Builder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EnhancedDisplayActivity extends AppCompatActivity {
    Spinner spnRating;
    Button btnShowAll, btnAddMovie;
    ArrayList<Movie> movieList;
    ListView lv;
    CustomAdapter caMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enhanced_display);

        spnRating = findViewById(R.id.spnRating);
        btnShowAll = findViewById(R.id.btnShowAll);
        lv = findViewById(R.id.lv);
        btnAddMovie = findViewById(R.id.btnAddMovie);

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
                    Toast.makeText(EnhancedDisplayActivity.this, ratingLabel,
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
                Intent i = new Intent(EnhancedDisplayActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewAddDialog = inflater.inflate(R.layout.insert,null);

                // Obtain the UI component in the insert.xml layout
                // It needs to be defined as "final", so that it can used in the onClick() method later
                final EditText etTitle = viewAddDialog.findViewById(R.id.etTitle);
                final EditText etGenre = viewAddDialog.findViewById(R.id.etGenre);
                final EditText etYear = viewAddDialog.findViewById(R.id.etYear);
                final Spinner spinner = viewAddDialog.findViewById(R.id.spinner);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EnhancedDisplayActivity.this);
                myBuilder.setView(viewAddDialog); // Set the view of the dialog
                myBuilder.setTitle("Add Movie");

                myBuilder.setNeutralButton("Cancel", null);

                myBuilder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Insertion
                        DBHelper dbh = new DBHelper(EnhancedDisplayActivity.this);
                        String title = etTitle.getText().toString();
                        String genre = etGenre.getText().toString();
                        String yearString = etYear.getText().toString();
                        String ratingLabel = String.valueOf(spinner.getSelectedItem());

                        boolean digitsOnly = TextUtils.isDigitsOnly(etYear.getText());
                        if (title.trim().length() == 0 || genre.trim().length() == 0
                                || yearString.trim().length() == 0){
                            Toast.makeText(EnhancedDisplayActivity.this, "Blank field detected!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (digitsOnly == true){
                                int getYearNum = Integer.parseInt(yearString);
                                long inserted_id = dbh.insertSong(title, genre, getYearNum, ratingLabel);
                                if (inserted_id != -1){
                                    Toast.makeText(EnhancedDisplayActivity.this, "Insert successful",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EnhancedDisplayActivity.this, "Input Numbers only!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        caMovie.notifyDataSetChanged();

                    }
                });

                AlertDialog insertDialog = myBuilder.create();
                insertDialog.show();

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