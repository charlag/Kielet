package io.charlag.kielet.util;

import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static android.widget.AdapterView.INVALID_POSITION;

/**
 * Created by charlag on 02/04/2017.
 *  Observable like AdapterViewItemSelectionObservable from RxBinding but only fires events if
 *  adapterView tag is different than position (thus, to not fire event use setTag(position) before
 *  setSelection()).
 */

public class TouchSelectedEventsObservable extends Observable<Integer> {

    final AdapterView<?> view;

    public TouchSelectedEventsObservable(AdapterView<?> view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onError(new IllegalStateException(
                    "Expected to be called on the main thread but was " +
                            Thread.currentThread().getName()));
            return;
        }
        Listener listener = new Listener(view, observer);
        view.setOnItemSelectedListener(listener);
        observer.onSubscribe(listener);
    }

    private static final class Listener extends MainThreadDisposable
            implements AdapterView.OnItemSelectedListener {
        private final AdapterView<?> view;
        private final Observer<? super Integer> observer;

        Listener(AdapterView<?> view, Observer<? super Integer> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (isDisposed()) {
                return;
            }
            if (parent.getTag() == null || ((int) parent.getTag()) != position) {
                observer.onNext(position);
            }
        }

        @Override public void onNothingSelected(AdapterView<?> adapterView) {
            if (!isDisposed()) {
                observer.onNext(INVALID_POSITION);
            }
        }

        @Override protected void onDispose() {
            view.setOnItemSelectedListener(null);
        }
    }
}
