package de.mpg.mpdl.ebooksreader.injection.component;

import dagger.Component;
import de.mpg.mpdl.ebooksreader.activity.fragment.SearchFragment;
import de.mpg.mpdl.ebooksreader.injection.PerActivity;
import de.mpg.mpdl.ebooksreader.injection.module.ActivityModule;
import de.mpg.mpdl.ebooksreader.injection.module.EbooksModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class,
        EbooksModule.class})
public interface EbooksComponent {
    void inject(SearchFragment searchFragment);
}
