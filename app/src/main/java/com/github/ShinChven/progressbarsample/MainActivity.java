package com.github.ShinChven.progressbarsample;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.github.ShinChven.progressbarsample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewDataBinding.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDataBinding.arcProgressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        viewDataBinding.arcProgressBar.setmArcProgressBarProgress(70);
                        viewDataBinding.arcProgressBar.setmArcProgressBarMaxProgress(80);
                        viewDataBinding.arcProgressBar.invalidate();
                    }
                });
            }
        });


    }
}
