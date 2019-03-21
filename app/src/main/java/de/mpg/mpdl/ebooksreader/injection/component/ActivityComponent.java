package de.mpg.mpdl.ebooksreader.injection.component;

import android.app.Activity;

import dagger.Component;
import de.mpg.mpdl.ebooksreader.injection.PerActivity;
import de.mpg.mpdl.ebooksreader.injection.module.ActivityModule;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:

 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}
