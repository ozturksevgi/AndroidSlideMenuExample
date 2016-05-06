package com.sevgi.mymenu.interfaces;

import android.view.View;

import com.sevgi.mymenu.model.MenuItem;

/**
 * Created by sevgiozturk on 06/05/16.
 */

public interface MenuViewAnimatorListener {

    public void addViewToContainer(View view);

    public void disableHomeButton();

    public void enableHomeButton();

    public void onChangeFragment(MenuItem menuItem, int position);

}