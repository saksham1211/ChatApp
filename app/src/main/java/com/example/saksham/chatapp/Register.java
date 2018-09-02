package com.example.saksham.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    Button button;
    private FirebaseAuth mAuth;
    EditText name;
    EditText email;
    EditText password;
    private ProgressDialog mRegProgress;
    private DatabaseReference mDatabase;
    private android.support.v7.widget.Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = findViewById(R.id.createaccount);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();


        mRegProgress = new ProgressDialog(Register.this);


        //mtoolbar = (Toolbar) findViewById(R.id.app_bar);
        //setSupportActionBar(mtoolbar);
        //getSupportActionBar().setTitle("Create Account");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            String  dispname = name.getText().toString();
            String  Email= email.getText().toString();
            String  pass = password.getText().toString();

                if(!TextUtils.isEmpty(dispname)||TextUtils.isEmpty(Email)||TextUtils.isEmpty(pass))

                mRegProgress.setTitle("Registering User");
                mRegProgress.setMessage("Please wait while we create your account");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                createAccount(dispname, Email, pass);


            }
        });
    }
    private void createAccount(final String dispname, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            String device_token = FirebaseInstanceId.getInstance().getToken();

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", dispname);
                            userMap.put("status", "Hi there I'm using ChatApp.");
                            userMap.put("image", "default");
                            userMap.put("thumb_image", "default");
                            userMap.put("device_token", device_token);

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        mRegProgress.dismiss();

                                        Intent mainIntent = new Intent(Register.this, Main2Activity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();

                                    }

                                }
                            });


                        }
                        else {
                            mRegProgress.hide();
                            Toast.makeText(Register.this, "ACCOUNT NOT CREATED, TRY AGAIN.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}