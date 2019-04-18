package de.mpg.mpdl.ebooksreader.model.dto;

import com.google.gson.annotations.SerializedName;

public class VolumeInfoDTO {
    @SerializedName("imageLinks")
    ImageLinksDTO imageLinksDTO;

    public VolumeInfoDTO() {
    }

    public ImageLinksDTO getImageLinksDTO() {
        return imageLinksDTO;
    }

    public void setImageLinksDTO(ImageLinksDTO imageLinksDTO) {
        this.imageLinksDTO = imageLinksDTO;
    }
}
