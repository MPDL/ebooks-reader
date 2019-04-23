package de.mpg.mpdl.ebooksreader.model;

public class DownloadedBookModel {
    private String title;
    private String author;
    private String isbn;
    private boolean isChecked;

    public DownloadedBookModel() {
    }

    public DownloadedBookModel(String title, String author, String isbn, boolean isChecked) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
