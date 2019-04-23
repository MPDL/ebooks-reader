package de.mpg.mpdl.ebooksreader.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import de.mpg.mpdl.ebooksreader.activity.BookDescriptionActivity;
import de.mpg.mpdl.ebooksreader.activity.R;
import de.mpg.mpdl.ebooksreader.base.BaseActivity;
import de.mpg.mpdl.ebooksreader.base.BaseMvpFragment;
import de.mpg.mpdl.ebooksreader.common.adapter.SearchResultAdapter;
import de.mpg.mpdl.ebooksreader.common.adapter.interf.BookClickListener;
import de.mpg.mpdl.ebooksreader.common.adapter.interf.OnLoadMoreListener;
import de.mpg.mpdl.ebooksreader.injection.component.DaggerEbooksComponent;
import de.mpg.mpdl.ebooksreader.injection.module.EbooksModule;
import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;
import de.mpg.mpdl.ebooksreader.model.dto.QueryResponseDTO;
import de.mpg.mpdl.ebooksreader.mvp.presenter.SearchFragmentPresenter;
import de.mpg.mpdl.ebooksreader.mvp.view.SearchFragmentView;
import de.mpg.mpdl.ebooksreader.utils.JacksonUtil;
import de.mpg.mpdl.ebooksreader.utils.PropertiesReader;

public class SearchFragment extends BaseMvpFragment<SearchFragmentPresenter> implements SearchFragmentView, BookClickListener {


    String HASH_CREDENTIAL = "";
    String queryStr = "";
    int index = 0;
    ImageView backImageView;
    ImageView ebooksSearchImageView;
    TextView ebooksDescriptionTextView;
    TextView ebooksLabel;
    SearchView ebooksSearchView;
    TextView searchHintTextView;
    RecyclerView searchResultRecyclerView;
    SearchResultAdapter searchResultAdapter;

    List<DocDTO> searchResultList= new ArrayList<>();

    public SearchFragment() {
    }

    @Override
    protected void injectComponent() {
        DaggerEbooksComponent.builder()
                .applicationComponent(getApplicationComponent())
                .ebooksModule(new EbooksModule())
                .build()
                .inject(this);

        mPresenter.setView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        backImageView = getActivity().findViewById(R.id.detailBackImageView);
        ebooksSearchImageView = getActivity().findViewById(R.id.ebooksSearchImageView);
        searchResultRecyclerView = getActivity().findViewById(R.id.searchResultRecyclerView);
        ebooksLabel = getActivity().findViewById(R.id.ebooksLabel);
        ebooksDescriptionTextView = getActivity().findViewById(R.id.ebooksDescriptionTextView);
        searchHintTextView = getActivity().findViewById(R.id.searchHintTextView);
        ebooksSearchView = getActivity().findViewById(R.id.ebooksSearchView);

        backImageView.setOnClickListener(view1 -> {
            ebooksSearchImageView.setVisibility(View.VISIBLE);
            TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);
            tabLayout.setVisibility(View.VISIBLE);
            ebooksLabel.setVisibility(View.VISIBLE);
            ebooksDescriptionTextView.setVisibility(View.VISIBLE);
            searchHintTextView.setVisibility(View.VISIBLE);

            backImageView.setVisibility(View.GONE);
            searchResultRecyclerView.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)ebooksSearchView.getLayoutParams();
            params.setMargins(params.leftMargin, 435, params.rightMargin, params.bottomMargin);
            ebooksSearchView.setLayoutParams(params);
            ebooksSearchView.setQuery("", false);
            ebooksSearchView.clearFocus();
        });

        searchHintTextView.setOnClickListener(view12 -> {
            ebooksSearchImageView.setVisibility(View.GONE);
            TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);
            tabLayout.setVisibility(View.GONE);
            ebooksLabel.setVisibility(View.GONE);
            ebooksDescriptionTextView.setVisibility(View.GONE);
            searchHintTextView.setVisibility(View.GONE);

            backImageView.setVisibility(View.VISIBLE);
            searchResultRecyclerView.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)ebooksSearchView.getLayoutParams();
            params.setMargins(params.leftMargin, 210, params.rightMargin, params.bottomMargin);
            ebooksSearchView.setLayoutParams(params);
            ebooksSearchView.setIconified(false);
            searchResultList.clear();
        });

        ebooksSearchView.setQuery("", false);
        ebooksSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchResultList.clear();
                index = 0;
                queryStr = query;
                if (!queryStr.isEmpty()) {
                    mPresenter.selectDocs(HASH_CREDENTIAL, "on", "allfields:" + query + " prodcode_str_mv:SBA OR prodcode_str_mv:Springer OR prodcode_str_mv:OAPEN", index, "json", (BaseActivity) getActivity());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.isEmpty()) {
                    searchResultList.clear();
                    queryStr = "";
                }
                searchResultAdapter.notifyDataSetChanged();
                return false;
            }
        });

        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchResultAdapter = new SearchResultAdapter(searchResultRecyclerView, searchResultList);
        searchResultAdapter.setClickListener(this);
        searchResultRecyclerView.setAdapter(searchResultAdapter);
        searchResultAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (index <= 49 && !queryStr.isEmpty()) {
                    mPresenter.selectDocs(HASH_CREDENTIAL, "on", "allfields:" + queryStr + " prodcode_str_mv:SBA OR prodcode_str_mv:Springer OR prodcode_str_mv:OAPEN", index, "json", (BaseActivity) getActivity());
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        PropertiesReader propertiesReader = new PropertiesReader(getActivity());
        HASH_CREDENTIAL = propertiesReader.getEBooksProperties().getProperty("credential");
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == -1) return;
        DocDTO docDTO = searchResultList.get(position);
        String docDTOStr = JacksonUtil.stringifyDocDTO(docDTO);

        Intent bookDescriptionIntent = new Intent(getActivity(), BookDescriptionActivity.class);
        bookDescriptionIntent.putExtra("docDTOStr", docDTOStr);
        startActivity(bookDescriptionIntent);
    }

    @Override
    public void successfulSelectDocs(QueryResponseDTO queryResponseDTO) {
        searchResultAdapter.notifyItemRemoved(searchResultList.size());
        List<DocDTO> moreDocDTOs = queryResponseDTO.getResponseContentDTO().getDocs();
        for (DocDTO docDTO : moreDocDTOs) {
            if (docDTO != null && docDTO.getIsbn() != null) {
                docDTO.setCoverUrl("https://ebooks4-qa.mpdl.mpg.de/ebooks/Cover/Show?size=small&isbn=" + docDTO.getIsbn().get(0));
            }
            if (docDTO !=null && docDTO.getUrlPdfStr() == null && docDTO.getUrls() != null && docDTO.getUrls().size() > 0) {
                docDTO.setUrlPdfStr(docDTO.getUrls().get(0).replace("http://dx.doi.org/", "https://link.springer.com/content/pdf/"));
            }
        }

        searchResultList.addAll(moreDocDTOs);

        index += 10;
        searchResultAdapter.notifyDataSetChanged();
        searchResultAdapter.setLoaded();
    }

    @Override
    public void failedSelectDocs(Throwable e) {
        //TODO: error message
    }
}
