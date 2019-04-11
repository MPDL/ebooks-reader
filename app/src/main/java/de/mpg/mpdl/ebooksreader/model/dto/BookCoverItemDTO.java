package de.mpg.mpdl.ebooksreader.model.dto;

import com.google.gson.annotations.SerializedName;

public class BookCoverItemDTO {

    @SerializedName("volumeInfo")
    VolumeInfoDTO volumeInfoDTO;

    public BookCoverItemDTO() {
    }

    public VolumeInfoDTO getVolumeInfoDTO() {
        return volumeInfoDTO;
    }

    public void setVolumeInfoDTO(VolumeInfoDTO volumeInfoDTO) {
        this.volumeInfoDTO = volumeInfoDTO;
    }
}
