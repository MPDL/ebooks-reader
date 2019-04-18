package de.mpg.mpdl.ebooksreader.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocDTO {

    String id;
    String title;
    @SerializedName("title_sub")
    String subTitle;
    @SerializedName("author")
    List<String> authorList;
    @SerializedName("urlPdf_str")
    String urlPdfStr;
    @SerializedName("url")
    List<String> urls;
    String description;
    @SerializedName("isbn")
    List<String> isbn;
    List<String> publishDate;
    List<String> publisher;

    String coverUrl;

    public DocDTO() {
    }

    public DocDTO(String id, String title, String subTitle, List<String> authorList, String urlPdfStr, List<String> urls, String description, List<String> isbn, List<String> publishDate, List<String> publisher, String coverUrl) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authorList = authorList;
        this.urlPdfStr = urlPdfStr;
        this.urls = urls;
        this.description = description;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.coverUrl = coverUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }

    public String getUrlPdfStr() {
        return urlPdfStr;
    }

    public void setUrlPdfStr(String urlPdfStr) {
        this.urlPdfStr = urlPdfStr;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIsbn() {
        return isbn;
    }

    public void setIsbn(List<String> isbn) {
        this.isbn = isbn;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(List<String> publishDate) {
        this.publishDate = publishDate;
    }

    public List<String> getPublisher() {
        return publisher;
    }

    public void setPublisher(List<String> publisher) {
        this.publisher = publisher;
    }
}
