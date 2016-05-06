package com.sevgi.mymenu;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevgi.mymenu.fragment.HomePageFragment;
import com.sevgi.mymenu.fragment.MenuFragmentOne;
import com.sevgi.mymenu.fragment.MenuFragmentTwo;
import com.sevgi.mymenu.interfaces.MenuViewAnimatorListener;
import com.sevgi.mymenu.model.MenuItem;
import com.sevgi.mymenu.util.MenuViewAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sevgiozturk on 06/05/16.
 */

public class MainActivity extends ActionBarActivity implements MenuViewAnimatorListener {

    private MenuViewAnimator menuViewAnimator;
    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private List<MenuItem> manuList = new ArrayList<>();
    private ActionBarDrawerToggle drawerToggle;
    private TextView toolbar_title;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        menuViewAnimator = new MenuViewAnimator<>(MainActivity.this, manuList, drawerLayout, this);

        Fragment fragment = new HomePageFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

    }

    private void setActionBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.item_close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0) {
                    menuViewAnimator.showMenuContent();
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void createMenuList() {
        MenuItem menuItem0 = new MenuItem("", R.drawable.item_close);
        manuList.add(menuItem0);

        MenuItem menuItem1 = new MenuItem("Home Page", R.drawable.item_home);
        manuList.add(menuItem1);

        MenuItem menuItem = new MenuItem("Menu One", R.drawable.item_one);
        manuList.add(menuItem);

        MenuItem menuItem2 = new MenuItem("Menu Two", R.drawable.item_two);
        manuList.add(menuItem2);
    }

    @Override
    public void onChangeFragment(MenuItem menuItem, int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItem.getName()) {
            case "":
                break;

            case "Home Page":
                Fragment fragmentHome = new HomePageFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragmentHome).commit();
                toolbar_title.setText(menuItem.getName());
                break;

            case "Menu One":
                Fragment fragmentOne = new MenuFragmentOne();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragmentOne).commit();
                toolbar_title.setText(menuItem.getName());
                break;

            case "Menu Two":
                Fragment fragmentTwo = new MenuFragmentTwo();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragmentTwo).commit();
                toolbar_title.setText(menuItem.getName());
                break;

            default:
                break;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
