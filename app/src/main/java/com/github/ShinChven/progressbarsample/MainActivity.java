package com.github.ShinChven.progressbarsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import com.github.ShinChven.ArcProgressBar.DrawingAnimation;
import com.github.ShinChven.progressbarsample.databinding.ActivityMainBinding;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements DrawingAnimation.PercentageCallback {

    boolean flag = false;
    ActivityMainBinding viewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewDataBinding.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDataBinding.arcProgressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        setValueInAnimation();
                    }
                });
            }
        });
    }

    private void setValueInAnimation() {
        if (flag) {
            Animation animation = new DrawingAnimation(viewDataBinding.arcProgressBar, 10, 200,
                    MainActivity.this);
            animation.setDuration(500);
            viewDataBinding.arcProgressBar.startAnimation(animation);
            flag = false;
        } else {
            Animation animation = new DrawingAnimation(viewDataBinding.arcProgressBar, 900, 1000, MainActivity.this);
            animation.setDuration(500);
            viewDataBinding.arcProgressBar.startAnimation(animation);
            flag = true;
        }
    }

    @Override
    public void onPercentChanged(float percent) {
        float value = percent * 100;
        DecimalFormat fmt = new DecimalFormat("00.00");
        viewDataBinding.percentText.setText(fmt.format(value) + "%");
    }
}
