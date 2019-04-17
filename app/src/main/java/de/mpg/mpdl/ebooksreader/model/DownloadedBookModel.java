package de.mpg.mpdl.ebooksreader.model;

public class DownloadedBookModel {
    private String title;
    private String author;
    private boolean isChecked;

    public DownloadedBookModel() {
    }

    public DownloadedBookModel(String title, String author, boolean isChecked) {
        this.title = title;
        this.author = author;
        this.isChecked = isChecked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
