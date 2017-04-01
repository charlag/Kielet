package io.charlag.kielet.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by charlag on 01/04/2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScoped {
}
