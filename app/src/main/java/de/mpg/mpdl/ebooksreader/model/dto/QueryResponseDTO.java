package de.mpg.mpdl.ebooksreader.model.dto;

import com.google.gson.annotations.SerializedName;

public class QueryResponseDTO {
    @SerializedName("response")
    ResponseContentDTO responseContentDTO;

    public QueryResponseDTO() {
    }

    public QueryResponseDTO(ResponseContentDTO responseContentDTO) {
        this.responseContentDTO = responseContentDTO;
    }

    public ResponseContentDTO getResponseContentDTO() {
        return responseContentDTO;
    }

    public void setResponseContentDTO(ResponseContentDTO responseContentDTO) {
        this.responseContentDTO = responseContentDTO;
    }
}
