package com.android.io.github.vaidehighime.www.signavv001;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.MessageFormat;

public class VerificationActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private TextView verificationStatus;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onResume() {
        super.onResume();
        SignavApplication.getInstance().setConnectivityListener(this);
        user.reload();
        if(user.isEmailVerified()) {
            Intent intent = new Intent(VerificationActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SignavApplication.getInstance().setConnectivityListener(this);
        user.reload();
        if(user.isEmailVerified()) {
            Intent intent = new Intent(VerificationActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        init();
        run();
    }

    private void run() {
        checkIfEmailVerified();
    }


    private void init() {
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        verificationStatus=findViewById(R.id.verificationStatus);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    private void checkIfEmailVerified()
    {

        if (user.isEmailVerified())
        {
            verificationStatus.setText(MessageFormat.format("{0} is Verified", user.getEmail()));
            Intent intent = new Intent(VerificationActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            verificationStatus.setText(MessageFormat.format("{0} is not verified click to get a verification email", user.getEmail()));
            verificationStatus.setClickable(true);
            verificationStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    user.sendEmailVerification().addOnCompleteListener(VerificationActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                verificationStatus.setClickable(false);
                                Toast.makeText(VerificationActivity.this, "Verification Email Send to!"+user.getEmail(), Toast.LENGTH_LONG).show();
                                while(!user.isEmailVerified()) {
                                    user.reload();
                                }
                                if(user.isEmailVerified()) {
                                    Intent intent = new Intent(VerificationActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(VerificationActivity.this, "Verification Email not Send!Try Again", Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(VerificationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });


                }
            });
        }
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        ShowInternetConnectivity.showSnack(VerificationActivity.this,isConnected);
    }



}
