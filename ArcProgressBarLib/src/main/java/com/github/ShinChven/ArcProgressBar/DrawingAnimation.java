package com.github.ShinChven.ArcProgressBar;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by ShinChven on 16/5/30.
 */
public class DrawingAnimation extends Animation {


    private final ArcProgressBar mArc;
    private final int oldProgress;
    private final int newProgress;
    private final int oldMaxProgress;
    private final int newMaxProgress;
    private final PercentageCallback mCallback;
    private int limit;

    public DrawingAnimation(ArcProgressBar arcProgressBar, int progress, int maxProgress, PercentageCallback
            callback) {
        mArc = arcProgressBar;
        oldProgress = mArc.getmArcProgressBarProgress();
        oldMaxProgress = mArc.getmArcProgressBarMaxProgress();

        limit = mArc.getmArcProgressBarAngle();

        if (maxProgress > limit) {
            float percent = (float) progress / (float) maxProgress;
            newProgress = (int) (limit * percent);
            newMaxProgress = limit;
        } else {
            newProgress = progress;
            newMaxProgress = maxProgress;
        }

        mCallback = callback;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        int progress = (int) (oldProgress + ((newProgress - oldProgress) * interpolatedTime));
        int maxProgress = (int) (oldMaxProgress + ((newMaxProgress - oldMaxProgress) * interpolatedTime));

        mArc.setmArcProgressBarProgress(progress);
        mArc.setmArcProgressBarMaxProgress(maxProgress);
        mArc.requestLayout();

        if (mCallback != null) {
            float percent = (float) progress / (float) maxProgress;
            mCallback.onPercentChanged(percent);
        }

    }

    public interface PercentageCallback {
        void onPercentChanged(float percent);
    }
}
