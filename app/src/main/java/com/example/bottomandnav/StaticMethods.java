package com.example.bottomandnav;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class StaticMethods {
    public static void loadFragmentsWithBackStack(FragmentActivity activity, Fragment selectedFragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.addFrame, selectedFragment)
                .addToBackStack(null)
                .commit();
    }
    public static void loadFragments(FragmentActivity activity, Fragment selectedFragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.addFrame, selectedFragment)
                .commit();
    }
}
