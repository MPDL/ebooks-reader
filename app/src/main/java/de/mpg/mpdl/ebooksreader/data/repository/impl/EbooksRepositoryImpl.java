package de.mpg.mpdl.ebooksreader.data.repository.impl;

import javax.inject.Inject;

import de.mpg.mpdl.ebooksreader.data.net.GRetrofitFactory;
import de.mpg.mpdl.ebooksreader.data.net.RetrofitFactory;
import de.mpg.mpdl.ebooksreader.data.net.api.EbooksApi;
import de.mpg.mpdl.ebooksreader.data.net.api.GCoverApi;
import de.mpg.mpdl.ebooksreader.data.repository.EbooksRepository;
import de.mpg.mpdl.ebooksreader.model.dto.BookCoverResponseDTO;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import rx.Observable;

public class EbooksRepositoryImpl implements EbooksRepository {

    @Inject
    public EbooksRepositoryImpl() {
    }

    @Override
    public Observable<QueryResponseDTO> selectDocs(String credential, String indent, String q, int start, String wt) {
        return RetrofitFactory.getInstance().create(EbooksApi.class).selectDocs(credential, indent, q, start, wt);
    }

    @Override
    public Observable<BookCoverResponseDTO> getCover(String isbn) {
        return GRetrofitFactory.getInstance().create(GCoverApi.class).getCover(isbn);
    }


}
