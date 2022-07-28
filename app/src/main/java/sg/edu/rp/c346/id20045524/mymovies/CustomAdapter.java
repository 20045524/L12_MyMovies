package sg.edu.rp.c346.id20045524.mymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movie> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object - Get
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row - Read
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding - Find and Bind
        TextView tvTitle = rowView.findViewById(R.id.tvTitle);
        TextView tvGenre = rowView.findViewById(R.id.tvGenre);
        TextView tvYear = rowView.findViewById(R.id.tvYear);

        ImageView ivRating = rowView.findViewById(R.id.imageViewRating);

        // Obtain the Contact information based on the position - Find
        Movie currentItem = movieList.get(position);

        // Set values to the TextView to display the corresponding information - Populate
        tvTitle.setText(currentItem.getTitle());
        tvGenre.setText(currentItem.getGenre());
        tvYear.setText(currentItem.getYear()+"");


        String imageUrl = "";
        if (currentItem.getRating().equalsIgnoreCase("G")){
            imageUrl += "https://www.imda.gov.sg/-/media/Imda/Images/Content/Regulation-Licensing-and-Consultations/Content-Standards-and-classification/Classification-Rating/General-Rating.png";

        } else if (currentItem.getRating().equalsIgnoreCase("PG")){
            imageUrl += "https://www.imda.gov.sg/-/media/Imda/Images/Content/Regulation-Licensing-and-Consultations/Content-Standards-and-classification/Classification-Rating/PG-Rating.png";

        } else if (currentItem.getRating().equalsIgnoreCase("PG13")){
            imageUrl += "https://www.imda.gov.sg/-/media/Imda/Images/Content/Regulation-Licensing-and-Consultations/Content-Standards-and-classification/Classification-Rating/PG13-Rating.png";

        } else if (currentItem.getRating().equalsIgnoreCase("NC16")){
            imageUrl += "https://www.imda.gov.sg/-/media/Imda/Images/Content/Regulation-Licensing-and-Consultations/Content-Standards-and-classification/Classification-Rating/NC16-Rating.png";

        } else if (currentItem.getRating().equalsIgnoreCase("M18")){
            imageUrl += "https://www.imda.gov.sg/-/media/Imda/Images/Content/Regulation-Licensing-and-Consultations/Content-Standards-and-classification/Classification-Rating/M18-Rating.png";

        } else if (currentItem.getRating().equalsIgnoreCase("R21")){
            imageUrl += "https://www.imda.gov.sg/-/media/Imda/Images/Content/Regulation-Licensing-and-Consultations/Content-Standards-and-classification/Classification-Rating/R21-Rating.png";

        }
        Picasso.with(parent_context).load(imageUrl).into(ivRating);




        return rowView;
    }



}
