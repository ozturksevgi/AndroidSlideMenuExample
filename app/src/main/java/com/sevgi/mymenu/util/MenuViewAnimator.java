package com.sevgi.mymenu.util;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevgi.mymenu.R;
import com.sevgi.mymenu.animations.MenuAnimation;
import com.sevgi.mymenu.interfaces.MenuViewAnimatorListener;
import com.sevgi.mymenu.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sevgiozturk on 06/05/16.
 */

public class MenuViewAnimator<T extends MenuItem> {

    private Activity activity;
    private List<T> list;
    private List<View> viewList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private MenuViewAnimatorListener animatorListener;
    private final int ANIMATION_DURATION = 160;

    public MenuViewAnimator(Activity activity, List<T> items, final DrawerLayout drawerLayout, MenuViewAnimatorListener animatorListener) {
        this.activity = activity;
        this.list = items;
        this.drawerLayout = drawerLayout;
        this.animatorListener = animatorListener;
    }

    private void setViewsClickable(boolean clickable) {
        animatorListener.disableHomeButton();
        for (View view : viewList) {
            view.setEnabled(clickable);
        }
    }

    public void showMenuContent() {
        setViewsClickable(false);
        viewList.clear();
        double size = list.size();
        for (int i = 0; i < size; i++) {
            View viewMenu = activity.getLayoutInflater().inflate(R.layout.menu_list_item, null);
            final int finalI = i;
            viewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] location = {0, 0};
                    v.getLocationOnScreen(location);
                    changeItem(list.get(finalI), location[1] + v.getHeight() / 2);
                }
            });
            ((ImageView) viewMenu.findViewById(R.id.menu_item_image)).setImageResource(list.get(i).getImageRes());
            viewMenu.setVisibility(View.GONE);
            viewMenu.setEnabled(false);
            ((TextView) viewMenu.findViewById(R.id.menu_label)).setText(list.get(i).getName());
            viewList.add(viewMenu);
            animatorListener.addViewToContainer(viewMenu);
            final double position = i;
            final double delay = 4 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateView((int) position);
                    }
                    if (position == viewList.size() - 1) {
                        setViewsClickable(true);
                    }
                }
            }, (long) delay);
        }

    }

    private void hideMenuContent() {
        setViewsClickable(false);
        double size = list.size();
        for (int i = list.size(); i >= 0; i--) {
            final double position = i;
            final double delay = 4 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateHideView((int) position);
                    }
                }
            }, (long) delay);
        }

    }

    private void animateView(int position) {
        final View view = viewList.get(position);
        view.setVisibility(View.VISIBLE);
        MenuAnimation rotation =
                new MenuAnimation("OPEN", 70, 0, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }

    private void animateHideView(final int position) {
        final View view = viewList.get(position);
        MenuAnimation rotation =
                new MenuAnimation("CLOSE", 0, 90, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(View.INVISIBLE);
                if (position == viewList.size() - 1) {
                    animatorListener.enableHomeButton();
                    drawerLayout.closeDrawers();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }

    private void changeItem(MenuItem menuItem, int topPosition) {
        hideMenuContent();
        animatorListener.onChangeFragment(menuItem, topPosition);

    }
}
