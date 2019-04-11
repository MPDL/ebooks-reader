package de.mpg.mpdl.ebooksreader.data.service;

import de.mpg.mpdl.ebooksreader.base.BaseService;
import de.mpg.mpdl.ebooksreader.model.dto.BookCoverResponseDTO;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import rx.Observable;

public abstract class EbooksService extends BaseService {
    public abstract Observable<QueryResponseDTO> selectDocs(String credential, String indent, String q, int start, String wt);
    public abstract Observable<BookCoverResponseDTO> getCover(String isbn);
}
