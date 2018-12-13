package com.copordrop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MJMJ2 on 02/06/17.
 */
public class SettingsFragment extends Fragment {

    private com.suke.widget.SwitchButton switchButtonNew;
    private com.suke.widget.SwitchButton switchButtonOther;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_settings, container, false);

        switchButtonNew = (com.suke.widget.SwitchButton) v.findViewById(R.id.switchNews);
        switchButtonOther = (com.suke.widget.SwitchButton) v.findViewById(R.id.switchLNF);

        switchButtonNew.setChecked(true);
        switchButtonOther.setChecked(true);


        return v;
    }
}
