package io.charlag.kielet.history;

import dagger.Component;
import io.charlag.kielet.AppComponent;
import io.charlag.kielet.annotation.FragmentScoped;

/**
 * Created by charlag on 03/04/2017.
 */

@FragmentScoped
@Component(dependencies = AppComponent.class, modules = HistoryPresenterModule.class)
interface HistoryComponent {
    void inject(HistoryFragment historyFragment);
}
