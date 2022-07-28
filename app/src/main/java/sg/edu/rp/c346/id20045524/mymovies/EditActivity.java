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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    TextView tvTitle, tvGenre, tvYear, tvID;
    EditText etTitle, etGenre, etYear, etID;
    Button btnUpdate, btnDelete, btnCancel;
    Spinner spinner;

    Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        tvTitle = findViewById(R.id.tvTitle);
        tvGenre = findViewById(R.id.tvGenre);
        tvYear = findViewById(R.id.tvYear);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinner = findViewById(R.id.spinner);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        tvID = findViewById(R.id.tvID);
        etID = findViewById(R.id.etID);
        etID.setEnabled(false);

        loadSpinnerData();

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        etID.setText(data.getId()+""); //Change int to string when setting text
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(data.getYear()+""); //Change int to string when setting text
        int pos = data.ratingInt(data.getRating());
        spinner.setSelection(pos);

        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearString = etYear.getText().toString();
                String ratingLabel = spinner.getSelectedItem().toString();

                boolean digitsOnly = TextUtils.isDigitsOnly(etYear.getText());

                if (title.trim().length() == 0 || genre.trim().length() == 0
                        || yearString.trim().length() == 0){
                    Toast.makeText(EditActivity.this, "Blank field detected!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (digitsOnly == true){

                        int getYearNum = Integer.parseInt(yearString);
                        if(!ratingLabel.equals("--")){
                            data.setTitle(title);
                            data.setGenre(genre);
                            data.setYear(getYearNum);
                            data.setRating(ratingLabel);
                            long updateded_id = dbh.updateMovie(data);
                            if (updateded_id != -1){
                                Toast.makeText(EditActivity.this, "Update successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                Log.i("Update", "Update successful");
                            } else {
                                Toast.makeText(EditActivity.this, "Update failed",
                                        Toast.LENGTH_SHORT).show();
                                Log.i("Update", "Update failed");
                            }
                        } else {
                            Toast.makeText(EditActivity.this, "Please set a rating!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //The below spinner function cause Update Function to not work.
//                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                                String ratingLabel = adapterView.getItemAtPosition(position).toString();
//                                if(!ratingLabel.equals("--")){
//                                    data.setTitle(title);
//                                    data.setGenre(genre);
//                                    data.setYear(getYearNum);
//                                    data.setRating(ratingLabel);
//                                    long updateded_id = dbh.updateMovie(data);
//                                    if (updateded_id != -1){
//                                        Toast.makeText(EditActivity.this, "Update successful",
//                                                Toast.LENGTH_SHORT).show();
//                                        finish();
//                                        Log.i("Update", "Update successful");
//                                    } else {
//                                        Toast.makeText(EditActivity.this, "Update failed",
//                                                Toast.LENGTH_SHORT).show();
//                                        Log.i("Update", "Update failed");
//                                    }
//                                } else {
//                                    Toast.makeText(EditActivity.this, "Please set a rating!",
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
                        Toast.makeText(EditActivity.this, "Input Numbers only!",
                                Toast.LENGTH_SHORT).show();
                    }
                }


            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteMovie(data.getId());
                finish();
                Log.d("Finish", "Go Back");

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadSpinnerData() {
        ArrayList<String> ratingLabels = new ArrayList<>();
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