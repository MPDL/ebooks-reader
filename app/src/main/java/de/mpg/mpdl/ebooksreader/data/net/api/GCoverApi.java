package de.mpg.mpdl.ebooksreader.data.net.api;

import de.mpg.mpdl.ebooksreader.model.dto.BookCoverResponseDTO;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GCoverApi {
    @GET("/books/v1/volumes")
    Observable<BookCoverResponseDTO> getCover(
            @Query("q")String isbn
    );
}