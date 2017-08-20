package com.mahao.paintmode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.btn_one:

                Intent intent = new Intent(this,RewardActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_two:

                Intent intent1 = new Intent(this, RoundActivity.class);
                startActivity(intent1);
                break;

            case R.id.btn_three:

                Intent intent2 = new Intent(this,ReverseActivity.class);
                startActivity(intent2);
                break;

            case R.id.btn_four:

                Intent intent3 = new Intent(this,EraserActivity.class);
                startActivity(intent3);
                break;
        }

    }
}
