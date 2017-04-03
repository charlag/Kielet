package io.charlag.kielet.history;

import java.util.List;

import io.charlag.kielet.common.BasePresenter;
import io.charlag.kielet.common.TranslationItemViewModel;
import io.reactivex.Observable;

/**
 * Created by charlag on 03/04/2017.
 */

public final class HistoryContract {
    public interface Presenter extends BasePresenter {
        Observable<List<TranslationItemViewModel>> translations();
    }
    public interface View {
        Observable<Integer> favoriteSelected();
    }
}
