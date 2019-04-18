package de.mpg.mpdl.ebooksreader.model.dto;

public class ImageLinksDTO {
    String smallThumbnail;
    String thumbnail;

    public ImageLinksDTO() {
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
