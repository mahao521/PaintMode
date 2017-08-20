package com.mahao.paintmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mahao.paintmode.widget.RewardView;

public class RewardActivity extends AppCompatActivity implements View.OnClickListener {


    private RewardView rewardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);


        findViewById(R.id.btn_start_scan).setOnClickListener(this);
        findViewById(R.id.btn_stop_scan).setOnClickListener(this);

        rewardView = (RewardView) findViewById(R.id.reward_view);
    }



    @Override
    public void onClick(View v) {

       switch (v.getId()){

           case R.id.btn_start_scan:

               rewardView.startScan();
               break;

           case R.id.btn_stop_scan:

               rewardView.stopScan();
               break;
       }
    }
}
