package com.sevgi.mymenu.animations;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by sevgiozturk on 06/05/16.
 */

public class MenuAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mX;
    private final float mY;
    private Camera mCamera;
    private String whichWhay;

    public MenuAnimation(String whichWhay, float fromDegrees, float toDegrees,
                         float X, float Y) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mX = X;
        mY = Y;
        this.whichWhay = whichWhay;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mX;
        final float centerY = mY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();

        if ("OPEN".equals(whichWhay)) {
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(centerX, centerY);
            matrix.postTranslate(-centerX, -centerY);
        } else {
            camera.rotateY(degrees);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
