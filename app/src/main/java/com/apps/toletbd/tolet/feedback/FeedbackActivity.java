package com.apps.toletbd.tolet.feedback;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apps.toletbd.tolet.R;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[] btn = new Button[4];
    private Button btn_unfocused;
    private int[] btn_id = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackground(getResources().getDrawable(R.drawable.button_border));
            btn[i].setOnClickListener(this);
        }

        btn_unfocused = btn[0];
    }

    @Override
    public void onClick(View v) {
        //setForcus(btn_unfocused, (Button) findViewById(v.getId()));
        //Or use switch
        switch (v.getId()){
            case R.id.btn0 :
                setFocus(btn_unfocused, btn[0]);
                break;

            case R.id.btn1 :
                setFocus(btn_unfocused, btn[1]);
                break;

            case R.id.btn2 :
                setFocus(btn_unfocused, btn[2]);
                break;

            case R.id.btn3 :
                setFocus(btn_unfocused, btn[3]);
                break;
        }
    }

    private void setFocus(Button btn_unfocused, Button btn_focus){
        btn_unfocused.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocused.setBackground(getResources().getDrawable(R.drawable.button_border));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.parseColor("#a4c639"));
        this.btn_unfocused = btn_focus;
    }
}
