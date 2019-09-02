package com.silence.movies.ui.base;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends MvpAppCompatActivity {

    private Unbinder mUnBinder;

    protected abstract int layoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutId());

        mUnBinder = ButterKnife.bind(this);
    }

    public void changeFragment(Fragment fragment, @IdRes int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commitNow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}
