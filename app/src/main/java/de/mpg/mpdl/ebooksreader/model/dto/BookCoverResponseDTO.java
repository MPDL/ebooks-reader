package de.mpg.mpdl.ebooksreader.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookCoverResponseDTO
{
    @SerializedName("items")
    List<BookCoverItemDTO> bookCoverItemDTOS;

    public BookCoverResponseDTO() {
    }

    public List<BookCoverItemDTO> getBookCoverItemDTOS() {
        return bookCoverItemDTOS;
    }

    public void setBookCoverItemDTOS(List<BookCoverItemDTO> bookCoverItemDTOS) {
        this.bookCoverItemDTOS = bookCoverItemDTOS;
    }
}
