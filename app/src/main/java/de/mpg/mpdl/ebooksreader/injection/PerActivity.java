package de.mpg.mpdl.ebooksreader.injection;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Declare Scope @PerActivity
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
