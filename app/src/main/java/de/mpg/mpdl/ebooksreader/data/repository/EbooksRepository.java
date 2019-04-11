package de.mpg.mpdl.ebooksreader.data.repository;

import de.mpg.mpdl.ebooksreader.model.dto.BookCoverResponseDTO;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import rx.Observable;

public interface EbooksRepository {
    Observable<QueryResponseDTO> selectDocs(String credential, String indent, String q, int start, String wt);

    Observable<BookCoverResponseDTO> getCover(String isbn);
}
