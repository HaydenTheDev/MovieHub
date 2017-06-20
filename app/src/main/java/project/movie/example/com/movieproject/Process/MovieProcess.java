package project.movie.example.com.movieproject.Process;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import project.movie.example.com.movieproject.MovieDetails;

public class MovieProcess {

    public static ArrayList<MovieDetails> getMovieDataFromJson(String movieJsonStr)
            throws JSONException {

        ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();

        if (TextUtils.isEmpty(movieJsonStr)) {
            return null;
        }

        final String MY_RESULTS = "results";
        final String MY_TITLE = "title";
        final String MY_VOTE = "vote";
        final String MY_POSTER_PATH = "poster_path";
        final String MY_DESCRIPTION = "description";
        final String MY_RELEASE_DATE = "release_date";
        final String MY_BACKDROP = "backdrop_path";

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(MY_RESULTS);

        String baseURL = "http://image.tmdb.org/t/p/w500/";

        for (int i = 0; i < movieArray.length(); i++) {
            String posterURL;
            String moviePoster;
            String overView;
            String releaseDate;
            String backdropURL;
            String movieBackdrop;

            JSONObject currentMovie = movieArray.getJSONObject(i);

            String title = currentMovie.optString(MY_TITLE);
            String vote_average = currentMovie.optString(MY_VOTE);
            posterURL = currentMovie.optString(MY_POSTER_PATH);
            moviePoster = baseURL + posterURL;
            overView = currentMovie.optString(MY_DESCRIPTION);
            releaseDate = currentMovie.optString(MY_RELEASE_DATE);
            backdropURL = currentMovie.optString(MY_BACKDROP);
            movieBackdrop = baseURL + backdropURL;

            movieDetailsArrayList.add(new MovieDetails(title, vote_average, moviePoster, overView,
                    releaseDate, movieBackdrop));
        }

        return movieDetailsArrayList;
    }
}
