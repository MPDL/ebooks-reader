package de.mpg.mpdl.ebooksreader.data.service.impl;

import javax.inject.Inject;

import de.mpg.mpdl.ebooksreader.data.repository.EbooksRepository;
import de.mpg.mpdl.ebooksreader.data.service.EbooksService;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import rx.Observable;

public class EbooksServiceImpl extends EbooksService {

    @Inject
    EbooksRepository ebooksRepository;

    @Inject
    public EbooksServiceImpl() {
    }

    @Override
    public Observable<QueryResponseDTO> selectDocs(String credential, String indent, String q, int start, String wt) {
        return ebooksRepository.selectDocs(credential, indent, q, start, wt);
    }
}
