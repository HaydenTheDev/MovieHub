package project.movie.example.com.movieproject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import project.movie.example.com.movieproject.activity.Details;

public class MovieFrag extends Fragment implements AsyncTaskExecutor,
        SharedPreferences.OnSharedPreferenceChangeListener{

    final int UP_CODE = 1;

    MovieAdapter movieAdapter;

    private TextView emptyTextView;

    private View loader;

    ArrayList<MovieDetails> movieDetailArrayList = new ArrayList<>();


    public MovieFrag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        updateMovies("popular");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_frag, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(getActivity(), Settings.class), UP_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String sort = sharedPreferences.getString(getString(R.string.pref_moviekey),
                getString(R.string.pref_popular));
        updateMovies(sort);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    private void updateMovies(String order) {
        if(checkConnection(getActivity())) {
            FetchTask getTask = new FetchTask(this);
            getTask.execute(order);
        }else{

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.movie_frag, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.listview_movie);

        emptyTextView = (TextView) rootView.findViewById(R.id.empty_view);
        gridView.setEmptyView(emptyTextView);

        loader = rootView.findViewById(R.id.loading_indicator);
        gridView.setEmptyView(loader);

        movieAdapter = new MovieAdapter(getContext(), movieDetailArrayList);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MovieDetails movieDetails = (MovieDetails) movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), Details.class);
                MovieDetails movie = new MovieDetails(movieDetails.getTitle(), movieDetails.getVote(),
                        movieDetails.getPosterUrl(), movieDetails.getOverView(), movieDetails.getRelease_date(),
                        movieDetails.getBackdrop_path());
                intent.putExtra(MovieDetails.EXTRA_MOVIE_DETAILS, movie);
                startActivity(intent);
            }
        });

        return rootView;

    }

    public boolean checkConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isConnected());
    }

    @Override
    public void processFin(Object output) {
        this.movieDetailArrayList = (ArrayList<MovieDetails>) output;
        movieAdapter.updateData(this.movieDetailArrayList);
    }
}
