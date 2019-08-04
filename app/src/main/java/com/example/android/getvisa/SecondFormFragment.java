package com.example.android.getvisa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * This is the Second Screen for Visa Form Filling and it is used by VisaFormActivity.
 * A simple {@link Fragment} subclass.
 */
public class SecondFormFragment extends Fragment {


    public SecondFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_form, container, false);
    }

}
