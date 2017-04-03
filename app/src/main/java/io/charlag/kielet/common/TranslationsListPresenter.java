package io.charlag.kielet.common;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by charlag on 03/04/2017.
 */

public interface TranslationsListPresenter extends BasePresenter {
    public Observable<List<TranslationItemViewModel>> translations();
}
