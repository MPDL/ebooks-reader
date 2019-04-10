package de.mpg.mpdl.ebooksreader.data.repository.impl;

import javax.inject.Inject;

import de.mpg.mpdl.ebooksreader.data.net.RetrofitFactory;
import de.mpg.mpdl.ebooksreader.data.net.api.EbooksApi;
import de.mpg.mpdl.ebooksreader.data.repository.EbooksRepository;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import rx.Observable;

public class EbooksRepositoryImpl implements EbooksRepository {

    @Inject
    public EbooksRepositoryImpl() {
    }

    @Override
    public Observable<QueryResponseDTO> selectDocs(String credential, String indent, String q, String wt) {
        return RetrofitFactory.getInstance().create(EbooksApi.class).selectDocs(credential, indent, q, wt);
    }
}
