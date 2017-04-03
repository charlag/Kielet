package io.charlag.kielet.history;

import io.charlag.kielet.common.TranslationsListPresenter;
import io.charlag.kielet.common.TranslationsView;

/**
 * Created by charlag on 03/04/2017.
 */

final class HistoryContract {
    private HistoryContract() {
    }

    public interface Presenter extends TranslationsListPresenter {
    }

    public interface View extends TranslationsView {
    }
}
