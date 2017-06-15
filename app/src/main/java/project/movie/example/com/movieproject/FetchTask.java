package project.movie.example.com.movieproject;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import project.movie.example.com.movieproject.MovieProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchTask extends AsyncTask<String, Void, ArrayList<MovieDetails>> {
    private final String LOG_TAG = FetchTask.class.getSimpleName();

    private AsyncTaskExecutor executor;

    public FetchTask(AsyncTaskExecutor executor){
        this.executor = executor;
    }

    @Override
    protected ArrayList<MovieDetails> doInBackground(String... params) {


        if (params.length == 0) {
            return null;
        }

        String sort = params[0];
        switch (sort) {
            case "0":
                sort = "popular";
                break;
            case "1":
                sort = "top_rated";
                break;
        }



        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJsonStr = null;


        try {
            final String MOVIE_URL = "http://api.themoviedb.org/3/movie/";
            final String APPID_PARAM = "api_key";

            Uri builtUri =
                    Uri.parse(MOVIE_URL).buildUpon()
                            .appendPath(sort)
                            .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIE_MAP_API_KEY)
                            .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {

                return null;
            }
            movieJsonStr = buffer.toString();
            Log.v(LOG_TAG, "Movie string: " + movieJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error!", e);
                }
            }
        }

        try {
            return MovieProcess.getMovieDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieDetails> result) {
        this.executor.processFin(result);
    }
}
