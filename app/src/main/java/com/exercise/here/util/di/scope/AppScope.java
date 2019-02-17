package com.exercise.here.util.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Anonym on 07/03/2018.
 */
@Documented
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface AppScope {
}
