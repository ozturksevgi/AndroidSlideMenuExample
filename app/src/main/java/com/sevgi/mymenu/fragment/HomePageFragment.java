package com.sevgi.mymenu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.sevgi.mymenu.R;

/**
 * Created by sevgiozturk on 06/05/16.
 */

public class HomePageFragment extends Fragment {
    Context context;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public HomePageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        context = container.getContext();

        final Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        Button homePageButton = (Button) view.findViewById(R.id.homePageButton);
        homePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(animation);
            }
        });

        return view;
    }
}