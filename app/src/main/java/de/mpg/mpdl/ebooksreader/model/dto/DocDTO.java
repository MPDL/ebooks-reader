package de.mpg.mpdl.ebooksreader.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocDTO {

    String id;
    String title;
    @SerializedName("author")
    List<String> authorList;
    @SerializedName("urlPdf_str")
    String urlPdfStr;
    @SerializedName("url")
    List<String> urls;
    String description;

    public DocDTO() {
    }

    public DocDTO(String id, String title, List<String> authorList, String urlPdfStr, List<String> urls, String description) {
        this.id = id;
        this.title = title;
        this.authorList = authorList;
        this.urlPdfStr = urlPdfStr;
        this.urls = urls;
        this.description = description;
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
}