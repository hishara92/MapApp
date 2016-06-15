package com.example.hishara.mapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    final String LOG="LogIn";
    Button btnLog;
    EditText textUsername,txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);




        textUsername=(EditText)findViewById(R.id.editUsername);
        txtPass=(EditText)findViewById(R.id.editPass);
        btnLog=(Button)findViewById(R.id.buttonLog);
        btnLog.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        HashMap postData=new HashMap();

        String username=textUsername.getText().toString();
        String password=txtPass.getText().toString();

        postData.put("txtUsername",username);
        postData.put("txtPassword",password);

        PostResponseAsyncTask task1=new PostResponseAsyncTask(LogIn.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d(LOG,s);
                if(s.contains("success")){
                    Intent intent = new Intent("com.example.hishara.mapapp.NewMenu.NewMenu");
                    startActivity(intent);
                    Toast.makeText(LogIn.this,"Successfully Logged in",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(LogIn.this,"Username or password ERROR..!!",Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(LogIn.this,s,Toast.LENGTH_LONG).show();
                Intent intent = new Intent("com.example.hishara.mapapp.NewMenu.NewMenu");
                startActivity(intent);

            }
        });
        task1.execute("http://guideme.orgfree.com/customer/");
    }
}
