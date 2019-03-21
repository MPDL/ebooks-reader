package de.mpg.mpdl.ebooksreader.injection.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import de.mpg.mpdl.ebooksreader.base.BaseActivity;
import de.mpg.mpdl.ebooksreader.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context context();
}
