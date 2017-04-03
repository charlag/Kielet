package io.charlag.kielet;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import io.charlag.kielet.history.HistoryFragment;
import io.charlag.kielet.translator.TranslatorFragment;
import io.charlag.kielet.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private static final String CHOSEN_ID = "CHOSEN_ID";

    private int chosenId;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        setFragment(item.getItemId());
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            setFragment(R.id.navigation_home);
        } else {
            chosenId = savedInstanceState.getInt(CHOSEN_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHOSEN_ID, chosenId);
    }

    void setFragment(int id) {
        if (id == chosenId) {
            return;
        }
        chosenId = id;
        Fragment fragment;
        // TODO: replace with actual fragments
        switch (chosenId) {
            case R.id.navigation_home:
                fragment = TranslatorFragment.newInstance();
                break;
            case R.id.navigation_history:
                fragment = HistoryFragment.newInstance();
                break;
            case R.id.navigation_favorites:
                fragment = TranslatorFragment.newInstance();
                break;
            default:
                fragment = TranslatorFragment.newInstance();
        }
        ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.content);
    }
}
