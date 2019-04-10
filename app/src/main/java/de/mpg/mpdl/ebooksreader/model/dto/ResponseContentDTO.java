package de.mpg.mpdl.ebooksreader.model.dto;

import java.util.List;

public class ResponseContentDTO {

    int numFound;

    int start;

    List<DocDTO> docs;

    public ResponseContentDTO() {
    }

    public ResponseContentDTO(int numFound, int start, List<DocDTO> docs) {
        this.numFound = numFound;
        this.start = start;
        this.docs = docs;
    }

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<DocDTO> getDocs() {
        return docs;
    }

    public void setDocs(List<DocDTO> docs) {
        this.docs = docs;
    }
}
