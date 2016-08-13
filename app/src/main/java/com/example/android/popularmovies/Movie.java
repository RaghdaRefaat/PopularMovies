package com.example.android.popularmovies;

/**
 * Created by Raghda Refaat on 13-Aug-16.
 */
public class Movie {

    private String PosterPath;
    private String Overview;
    private String ReleaseDate;
    private String OriginalTitle;
    private int Id;
    private double Popularity;
    private int VoteCount;
    private double VoteAvg;

    public Movie(String ppath , String overview , String date, String title, int id , double pop , int voteCount, double voteAvg){
        PosterPath = ppath;
        Overview = overview;
        ReleaseDate = date;
        OriginalTitle = title;
        Id = id;
        Popularity = pop;
        VoteCount = voteCount;
        VoteAvg = voteAvg;
    }

    public Movie() {
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public String getOverview() {
        return Overview;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public int getId() {
        return Id;
    }
    public double getPopularity() {
        return Popularity;
    }

    public int getVoteCount() {
        return VoteCount;
    }

    public double getVoteAvg() {
        return VoteAvg;
    }
}
