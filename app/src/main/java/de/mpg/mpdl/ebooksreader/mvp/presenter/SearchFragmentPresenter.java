package de.mpg.mpdl.ebooksreader.mvp.presenter;

import javax.inject.Inject;

import de.mpg.mpdl.ebooksreader.base.BaseAbstractPresenter;
import de.mpg.mpdl.ebooksreader.base.BaseActivity;
import de.mpg.mpdl.ebooksreader.base.BaseSubscriber;
import de.mpg.mpdl.ebooksreader.data.service.EbooksService;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import de.mpg.mpdl.ebooksreader.mvp.view.SearchFragmentView;

public class SearchFragmentPresenter extends BaseAbstractPresenter<SearchFragmentView> {

    @Inject
    EbooksService ebooksService;

    @Inject
    public SearchFragmentPresenter() {
    }

    public void selectDocs(String credential, String indent, String q, int start, String wt, BaseActivity activity) {
        if(!checkNetWork()){
            return;
        }

        ebooksService.execute(new BaseSubscriber<QueryResponseDTO>(mView){
            @Override
            public void onNext(QueryResponseDTO queryResponseDTO) {
                super.onNext(queryResponseDTO);
                if (mView != null) {
                    mView.successfulSelectDocs(queryResponseDTO);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.failedSelectDocs(e);
                }
            }
        }, ebooksService.selectDocs(credential, indent, q, start, wt), activity);

    }
}
