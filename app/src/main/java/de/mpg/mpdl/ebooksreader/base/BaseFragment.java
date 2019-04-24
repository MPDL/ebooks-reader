package de.mpg.mpdl.ebooksreader.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.mpg.mpdl.ebooksreader.injection.component.ApplicationComponent;
import de.mpg.mpdl.ebooksreader.injection.module.ActivityModule;

public abstract class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;
    private View mRootView;
    public static final String KEY_CLASS_NAME = "key_class_name";

    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public Context getContext(){
        return mContext;
    }

    public void startActivity(Class<? extends Activity> activityClass) {
        startActivity(getLocalIntent(activityClass, null));
    }

    public void startActivity(Class<? extends Activity> activityClass, Bundle bd) {
        startActivity(getLocalIntent(activityClass, bd));
    }

    public void showMessage(Object msg) {
        Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT).show();
    }

    public Intent getLocalIntent(Class<? extends Context> localIntent, Bundle bd) {
        Intent intent = new Intent(getActivity(), localIntent);
        if (null != bd) {
            intent.putExtras(bd);
        }
        return intent;
    }

    protected Intent getBackOnNewIntent(){
        Intent intent = getActivity().getIntent();
        try {
            intent.setClass(getActivity(), Class.forName(intent.getStringExtra(KEY_CLASS_NAME)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    protected Intent getStartOnNewIntent(Class activityClass){
        Intent intent = new Intent();
        intent.setClass(getActivity(), activityClass);
        intent.putExtra(KEY_CLASS_NAME, getActivity().getClass().getName());
        return intent;
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((BaseApplication)getActivity().getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(getActivity());
    }

    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setMessage("");
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        initContentView(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initContentView(Bundle savedInstanceState);

}
