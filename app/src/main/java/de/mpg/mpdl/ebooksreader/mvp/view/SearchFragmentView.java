package de.mpg.mpdl.ebooksreader.mvp.view;

import de.mpg.mpdl.ebooksreader.base.BaseView;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;

public interface SearchFragmentView extends BaseView {
    void successfulSelectDocs(QueryResponseDTO queryResponseDTO);
    void failedSelectDocs(Throwable e);
}
