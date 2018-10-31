package com.example.king.justjava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.support.v4.os.LocaleListCompat.create;
import static java.lang.System.in;

public class Main2Activity extends AppCompatActivity {

     TextView part1_Q,part2_Q,showLevel,showScore,showLife,showTimer;

     Button btnAn1, btnAn2, btnAn3;

     int score = 0,curuntLevel=1,partA,partB,currectAns,life=3,seconds=15;

     String result,res,m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        part1_Q = findViewById(R.id.Que_part1);
        part2_Q = findViewById(R.id.Que_part2);
        showLevel=findViewById(R.id.text_level);
        showScore=findViewById(R.id.text_score);
        btnAn1 = findViewById(R.id.btn_ans1);
        btnAn2 = findViewById(R.id.btn_ans2);
        btnAn3 = findViewById(R.id.btn_ans3);
        showLife=findViewById(R.id.life);
        showTimer=findViewById(R.id.timer);


        //calls the main question for front.
        randomQue();
        randomAns();
        updateTimer();



        //calls the options on buttons for front.
        btnAn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = btnAn1.getText().toString();
                res=Integer.toString(currectAns);
                randomQue();
                randomAns();
                checkAns();
                scoreLevel();
                restartTimer();
            }
        });
        btnAn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = btnAn2.getText().toString();
                res=Integer.toString(currectAns);
                randomQue();
                randomAns();
                checkAns();
                scoreLevel();
                restartTimer(); }
        });
        btnAn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = btnAn3.getText().toString();
                res=Integer.toString(currectAns);
                randomQue();
                randomAns();
                checkAns();
                scoreLevel();
                restartTimer();
            }
        });
    }

    //generating a randome Quetion.
    public void randomQue() {


        int numRange=curuntLevel*2;
        Random randInt= new Random();
        partA=(int)(Math.random()*numRange);
        part1_Q.setText(String.valueOf(partA+curuntLevel));
        partB=(int)(Math.random()*numRange);
        partB+=curuntLevel;
        part2_Q.setText(String.valueOf(partB+curuntLevel));
    }

    //giving options of answer,user can choose one of them.
    public void randomAns() {

        currectAns=(partA+curuntLevel)*(partB+curuntLevel);
        int wrongAns1=currectAns-1;
                int wrongAns2=currectAns+1;

        int choise = (int)(Math.random() * 3);
        choise++;
        switch(choise){

            case 1:
                btnAn1.setText(String.valueOf(currectAns));
                btnAn2.setText(String.valueOf(wrongAns1));
                btnAn3.setText(String.valueOf(wrongAns2));
                break;

            case 2:
                btnAn1.setText(String.valueOf(wrongAns2));
                btnAn2.setText(String.valueOf(currectAns));
                btnAn3.setText(String.valueOf(wrongAns1));
                break;

            case 3:
                btnAn1.setText(String.valueOf(wrongAns1));
                btnAn2.setText(String.valueOf(wrongAns2));
                btnAn3.setText(String.valueOf(currectAns));
                break;
        }

    }

    //check answer wrong or right.
    public void checkAns() {

        if (result.equals(res)) {
            Toast.makeText(Main2Activity.this,
                    "Well Done", Toast.LENGTH_SHORT).show();
            giveLife();
        } else {
            endGame();
        }
    }

    //update level and also score acording to level.
    public void scoreLevel(){
        if(result.equals(res)){

            int i=1;
            while(i<=curuntLevel){
                score=score+i;
                i++;
            }
            curuntLevel=curuntLevel+1;
            showLevel.setText(String.valueOf("level:-"+curuntLevel));
            showScore.setText(String.valueOf("score:-"+score));
        }


        }

        //ends the game after 3 wrong answer.
    public void endGame(){
        life=life-1;
        int i=1;
        showLife.setText(String.valueOf("Life:"+life));

        if(life<0){
            seconds=0;
            m=showScore.getText().toString();
            Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
            intent.putExtra("pavan",m);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }


    }

    //run a comedown for the question.
    public void updateTimer(){
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                showTimer.setText(String.valueOf("Timer:"+seconds));
                if(seconds>0){
                    seconds--;
                    handler.postDelayed(this,1000);
                    }
                    else {
                    if (life > 0) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                        builder.setMessage("Give a life to continu.");
                        builder.setCancelable(true);
                        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                life = life - 1;
                                showLife.setText(String.valueOf("Live:" + life));
                                dialogInterface.cancel();
                                restartTimer();
                                run();
                            }
                        });

                        builder.setPositiveButton("Giveup", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                m = showScore.getText().toString();
                                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                                intent.putExtra("pavan", m);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        m = showScore.getText().toString();
                        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                        intent.putExtra("pavan", m);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

    }

    //when hit any button restart the timer.
    public void restartTimer(){
        seconds=15;
    }

    //pass evey three level give your full life
    public void giveLife(){
        if(curuntLevel%5==0 && life<3){
            life=life+1;
            showLife.setText(String.valueOf("Life:"+life));
        }
    }
    //when hit back popup a note for exit or continue.
    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(Main2Activity.this);
        builder.setMessage("Are you sure,you want to exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Close!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    @Override
    public void onPause(){
        super.onPause();
        updateTimer();

    }
    @Override
    public void onResume(){
        super.onResume();
    }

   }






