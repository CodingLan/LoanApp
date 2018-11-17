package com.zhenxing.loanapp.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

/**
 * Created by xtdhwl on 2018/9/25.
 */
public class TBBaseFragment extends Fragment {

    private boolean DEBUG = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (DEBUG) {
            Logger.i("LIFE:%s onAttach", getClass());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            Logger.i("LIFE:%s onCreate:%s", getClass(), savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        if (DEBUG) {
            Logger.i("LIFE:%s onCreateView", getClass());
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (DEBUG) {
            Logger.i("LIFE:%s onActivityCreated:%s", getClass(), savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DEBUG) {
            Logger.i("LIFE:%s onStart", getClass());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DEBUG) {
            Logger.i("LIFE:%s onResume", getClass());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (DEBUG) {
            Logger.i("LIFE:%s onPause", getClass());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG) {
            Logger.i("LIFE:%s onStop", getClass());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (DEBUG) {
            Logger.i("LIFE:%s onDestroyView", getClass());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            Logger.i("LIFE:%s onDestroy", getClass());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (DEBUG) {
            Logger.i("LIFE:%s onDetach", getClass());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (DEBUG) {
            Logger.i("LIFE:%s onConfigurationChanged:%s", getClass(), newConfig);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (DEBUG) {
            Logger.i("LIFE:%s onSaveInstanceState:%s", getClass(), outState);
        }
    }
}
