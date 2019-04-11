package de.mpg.mpdl.ebooksreader.data.net.api;

import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface EbooksApi {
    @GET("/solr/ebx/select")
    Observable<QueryResponseDTO> selectDocs(
            @Header("Authorization") String credential,
            @Query("indent") String indent,
            @Query("q")String q,
            @Query("start")int start,
            @Query("wt")String json
    );
}
