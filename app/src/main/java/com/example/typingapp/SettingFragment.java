package com.example.typingapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;


public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_flagment);
    }
}
