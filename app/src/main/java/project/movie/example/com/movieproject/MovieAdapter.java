package project.movie.example.com.movieproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import project.movie.example.com.movieproject.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<MovieDetails> {
    private final String LOG_TAG = MovieAdapter.class.getSimpleName();
    List<MovieDetails> movieDetails;



    public MovieAdapter(Context context, List<MovieDetails> movies) {
        super(context, 0, movies);
    }

    public void updateData(ArrayList<MovieDetails> newPosters) {
        this.movieDetails = newPosters;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (movieDetails != null) {
            return movieDetails.size();
        }else
            return 0;
    }

    @Override
    public MovieDetails getItem(int position){
        return movieDetails.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }

        MovieDetails currentMovie = getItem(position);

        TextView titleMovie = (TextView) listItemView.findViewById(R.id.title_textview);
        titleMovie.setText(currentMovie.getTitle());

        ImageView posterMovie = (ImageView) listItemView.findViewById(R.id.movie_poster);
        Picasso.with(getContext()).load(currentMovie.getPosterUrl()).into(posterMovie);

        Picasso.with(getContext())
                .load(currentMovie.getPosterUrl())
                .placeholder(R.drawable.pic_load)
                .error(R.drawable.pic_error)
                .resize(6000, 2000)
                .onlyScaleDown()
                .into(posterMovie);

        return listItemView;
    }
}
