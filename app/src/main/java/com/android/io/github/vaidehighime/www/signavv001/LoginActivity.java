package com.android.io.github.vaidehighime.www.signavv001;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener  {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private Button btnSignup ;
    private Button btnLogin;
    private Button btnPassReset;

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        SignavApplication.getInstance().setConnectivityListener(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        SignavApplication.getInstance().setConnectivityListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        ifAlreadyLoggedIn();
        run();

    }

    private void run() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        btnPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful())
                        {
                            if (password.length() < 6)
                            {
                                inputPassword.setError(getString(R.string.minimum_password));
                            }
                            else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            startActivity(new Intent(LoginActivity.this,VerificationActivity.class));
                            finish();
                        }

                    }


                });

            }
        });
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnPassReset = findViewById(R.id.btn_reset_password);
    }

    private void ifAlreadyLoggedIn() {

        if( user != null) {

            startActivity(new Intent(LoginActivity.this, VerificationActivity.class));
            finish();
        }

    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        ShowInternetConnectivity.showSnack(LoginActivity.this,isConnected);
    }



}

