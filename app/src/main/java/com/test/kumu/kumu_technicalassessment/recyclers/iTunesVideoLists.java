package com.test.kumu.kumu_technicalassessment.recyclers;

public class iTunesVideoLists {

    String trackNameStr;

    String artworkStr;
    String priceStr;
    String genreStr;
    String longDescriptionStr;

    public iTunesVideoLists(){}

    public iTunesVideoLists(String trackNameStr, String artworkStr, String priceStr, String genreStr, String longDescriptionStr) {
        this.trackNameStr = trackNameStr;
        this.artworkStr = artworkStr;
        this.priceStr = priceStr;
        this.genreStr = genreStr;
        this.longDescriptionStr = longDescriptionStr;
    }

    public String getTrackNameStr() {
        return trackNameStr;
    }

    public void setTrackNameStr(String trackNameStr) {
        this.trackNameStr = trackNameStr;
    }

    public String getArtworkStr() {
        return artworkStr;
    }

    public void setArtworkStr(String artworkStr) {
        this.artworkStr = artworkStr;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getGenreStr() {
        return genreStr;
    }

    public void setGenreStr(String genreStr) {
        this.genreStr = genreStr;
    }

    public String getLongDescriptionStr() {
        return longDescriptionStr;
    }

    public void setLongDescriptionStr(String longDescriptionStr) {
        this.longDescriptionStr = longDescriptionStr;
    }



}
