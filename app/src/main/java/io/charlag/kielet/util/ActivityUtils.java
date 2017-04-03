package io.charlag.kielet.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by charlag on 03/04/2017.
 */

public final class ActivityUtils {
    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId) {
        fragmentManager.beginTransaction()
                .replace(frameId, fragment)
                .commit();
    }
}
