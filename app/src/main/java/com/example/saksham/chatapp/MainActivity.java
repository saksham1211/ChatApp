package com.example.saksham.chatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button4;
    ProgressBar progressBar;

    @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

                    button = findViewById(R.id.button2);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                Intent register=new Intent(MainActivity.this,Register.class);
                startActivity(register);


            }
        });


            button4 = findViewById(R.id.button4);

            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent login=new Intent(MainActivity.this,LoginScreen.class);
                    startActivity(login);

                }
            });
}
}