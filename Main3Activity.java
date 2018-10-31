package com.example.king.justjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView tv = findViewById(R.id.f_score);
        tv.setText("" + getIntent().getStringExtra("pavan"));

    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(Main3Activity.this);
        builder.setMessage("Are you sure,you want to exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Main3Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
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
}
