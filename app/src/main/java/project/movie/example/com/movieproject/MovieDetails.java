package project.movie.example.com.movieproject;

import android.os.Parcel;
import android.os.Parcelable;


public class MovieDetails  implements Parcelable {

    public static final String EXTRA_MOVIE_DETAILS = "moviedetails";


    public String title;


    public String vote;


    public String posterUrl;


    public String description;


    public String release_date;


    public String backdrop_path;


    public MovieDetails(String originalTitle, String vote_average, String posterPath, String desc,
                        String date, String backdrop) {
        this.title = originalTitle;
        this.vote = vote_average;
        this.posterUrl = posterPath;
        this.description = desc;
        this.release_date = date;
        this.backdrop_path = backdrop;
    }

    protected MovieDetails(Parcel in) {
        title = in.readString();
        vote = in.readString();
        posterUrl = in.readString();
        description = in.readString();
        release_date = in.readString();
        backdrop_path = in.readString();
    }


    public String getTitle() {
        return title;
    }


    public String getVote() {
        return vote;
    }


    public String getPosterUrl(){
        return posterUrl;
    }


    public String getOverView() {
        return description;
    }


    public String getRelease_date() {
        return release_date;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(vote);
        parcel.writeString(posterUrl);
        parcel.writeString(description);
        parcel.writeString(release_date);
        parcel.writeString(backdrop_path);

    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}
