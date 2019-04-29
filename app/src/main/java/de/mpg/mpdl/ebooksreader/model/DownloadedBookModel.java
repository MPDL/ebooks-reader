package de.mpg.mpdl.ebooksreader.model;

public class DownloadedBookModel {
    private String title;
    private String author;
    private String isbn;
    private boolean isChecked;
    private int downloadId;

    public DownloadedBookModel() {
    }

    public DownloadedBookModel(String title, String author, String isbn, boolean isChecked, int downloadId) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isChecked = isChecked;
        this.downloadId = downloadId;
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

    public int getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }
}
