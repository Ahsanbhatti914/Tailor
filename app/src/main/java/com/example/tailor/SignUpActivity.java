package com.example.tailor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    EditText nameEdt,emailEdt,passwordEdt,confirmPasswordEdt;
    Button signUpBtn;
    TextView signInTv;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameEdt = findViewById(R.id.nameEdtSignUp);
        emailEdt = findViewById(R.id.emailEdtSignUp);
        passwordEdt = findViewById(R.id.passwordEdtSignUp);
        confirmPasswordEdt = findViewById(R.id.confirmPasswordEdtSignUp);
        signUpBtn = findViewById(R.id.signUpBtn);
        signInTv = findViewById(R.id.signInTvSignUp);
        progressBar = findViewById(R.id.progressbarSignUp);
        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,email,password,confirmPassword;
                name = nameEdt.getText().toString();
                email = emailEdt.getText().toString();
                password = passwordEdt.getText().toString();
                confirmPassword = confirmPasswordEdt.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(SignUpActivity.this, "Name required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this, "Email required", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String temp = email.substring(0,email.length()-4);
                    if(!(temp.contains("@gmail") || temp.contains("@hotmail"))){
                        Toast.makeText(SignUpActivity.this, "Invaild email" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i = 0; i < temp.length() ; i++){
                        if(temp.charAt(i) == '.'){
                            Toast.makeText(SignUpActivity.this, "Email must not contain (.), except .com" , Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this, "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 8 || password.length() > 15){
                    Toast.makeText(SignUpActivity.this, "Password should be 8-15 digits long", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(SignUpActivity.this, "Confirm password required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confirmPassword)){
                    Toast.makeText(SignUpActivity.this, "Confirm password not matched", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.fetchSignInMethodsForEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        List<String> signInMethods = task.getResult().getSignInMethods();
                                        if (signInMethods != null && !signInMethods.isEmpty()) {
                                            // User exists
                                            Toast.makeText(SignUpActivity.this, "User already exists with email "
                                                    + email, Toast.LENGTH_SHORT).show();
                                        }else {
                                            progressBar.setVisibility(View.VISIBLE);
                                            mAuth.createUserWithEmailAndPassword(email,password)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                                            progressBar.setVisibility(View.GONE);
                                                            if(task.isSuccessful()){
                                                                mAuth.signOut();
                                                                Toast.makeText(SignUpActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                                                startActivity(intent);
                                                            }else {
                                                                Toast.makeText(SignUpActivity.this, "Registration not successfull!", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });
                                        }
                                    }
                                });
            }
        });
        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}