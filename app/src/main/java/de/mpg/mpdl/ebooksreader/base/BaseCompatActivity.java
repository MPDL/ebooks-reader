package de.mpg.mpdl.ebooksreader.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseCompatActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getLayoutId());
        initContentView(savedInstanceState);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * this activity layout res
     * @return res layout xml id
     */
    protected abstract int getLayoutId();

    /**
     * handle bundle
     * @param savedInstanceState
     */
    protected abstract void initContentView(Bundle savedInstanceState);


}
