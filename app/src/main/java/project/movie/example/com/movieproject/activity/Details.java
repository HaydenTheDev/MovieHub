package project.movie.example.com.movieproject.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import project.movie.example.com.movieproject.MovieDetails;
import project.movie.example.com.movieproject.Settings;
import project.movie.example.com.movieproject.R;

import com.squareup.picasso.Picasso;


public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();


        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class DetailFragment extends Fragment {

        private static final String LOG_TAG = DetailFragment.class.getSimpleName();

        private static final String FORECAST_SHARE_HASHTAG = " #MovieProject";
        private String mMovieStr;


        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.detail_frag, container, false);

            Intent intent = getActivity().getIntent();
            MovieDetails movieDetails = intent.getExtras().getParcelable(MovieDetails.EXTRA_MOVIE_DETAILS);

            TextView titleDetails = (TextView) rootView.findViewById(R.id.title_details);
            titleDetails.setText(movieDetails.getTitle());

            TextView releaseDateDetails = (TextView) rootView.findViewById(R.id.release_date_details);
            releaseDateDetails.setText(movieDetails.getRelease_date().substring(0,4));

            TextView overviewDetails = (TextView) rootView.findViewById(R.id.description_details);
            overviewDetails.setText(movieDetails.getOverView());

            ImageView backdropDetails = (ImageView) rootView.findViewById(R.id.backdrop_details);
            backdropDetails.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(movieDetails.getBackdrop_path()).into(backdropDetails);

            TextView voteMovie = (TextView) rootView.findViewById(R.id.vote_details);
            voteMovie.setText(movieDetails.getVote());

            ImageView posterDetails = (ImageView) rootView.findViewById(R.id.poster_details);
            posterDetails.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(movieDetails.getPosterUrl()).into(posterDetails);

            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mMovieStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.title_details))
                        .setText(mMovieStr);
            }

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detail_frag_menu, menu);

            MenuItem menuItem = menu.findItem(R.id.action_share);

            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);


            if (mShareActionProvider != null ) {
                mShareActionProvider.setShareIntent(createShareMovieIntent());
            } else {
                Log.d(LOG_TAG, "null?");
            }
        }

        private Intent createShareMovieIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    mMovieStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }
    }
}
