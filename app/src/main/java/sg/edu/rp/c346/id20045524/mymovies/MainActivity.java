package sg.edu.rp.c346.id20045524.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle, tvGenre, tvYear, tvRating;
    EditText etTitle, etGenre, etYear;
    Button btnInsert, btnShowList;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvGenre = findViewById(R.id.tvGenre);
        tvYear = findViewById(R.id.tvYear);
        tvRating = findViewById(R.id.tvRating);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinner = findViewById(R.id.spinner);

        loadSpinnerData();

        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearString = etYear.getText().toString();
                String ratingLabel = spinner.getSelectedItem().toString();

                boolean digitsOnly = TextUtils.isDigitsOnly(etYear.getText());

                if (title.trim().length() == 0 || genre.trim().length() == 0
                        || yearString.trim().length() == 0){
                    Toast.makeText(MainActivity.this, "Blank field detected!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (digitsOnly == true){

                        int getYearNum = Integer.parseInt(yearString);
                        if(!ratingLabel.equals("--")){
                            long inserted_id = dbh.insertSong(title, genre, getYearNum, ratingLabel);
                            if (inserted_id != -1){
                                Toast.makeText(MainActivity.this, "Insert successful",
                                        Toast.LENGTH_SHORT).show();
                                clear();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Please set a rating!",
                                    Toast.LENGTH_SHORT).show();
                        }

//                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                                String ratingLabel = adapterView.getItemAtPosition(position).toString();
//                                if(!ratingLabel.equals("--")){
//                                    long inserted_id = dbh.insertSong(title, genre, getYearNum, ratingLabel);
//                                    if (inserted_id != -1){
//                                        Toast.makeText(MainActivity.this, "Insert successful",
//                                                Toast.LENGTH_SHORT).show();
//                                        clear();
//                                    }
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Please set a rating!",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> adapterView) {
//
//                            }
//                        });

                    } else {
                        Toast.makeText(MainActivity.this, "Input Numbers only!",
                                Toast.LENGTH_SHORT).show();
                    }
                }


            }

            private void clear() {
                etTitle.setText("");
                etGenre.setText("");
                etYear.setText("");
                spinner.setSelection(0);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity
                Intent i = new Intent(MainActivity.this, ShowListActivity.class);
                startActivity(i);
            }
        });



    }

    private void loadSpinnerData() {
        ArrayList<String> ratingLabels = new ArrayList<>();
        ratingLabels.add("--");
        ratingLabels.add("G");
        ratingLabels.add("PG");
        ratingLabels.add("PG13");
        ratingLabels.add("NC16");
        ratingLabels.add("M18");
        ratingLabels.add("R21");
        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ratingLabels);

        // attaching data adapter to spinner
        spinner.setAdapter(ratingAdapter);
    }



}
