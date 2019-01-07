package com.android.io.github.vaidehighime.www.signavv001;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener
{

    private TextView displayLoggedEmailID;
    private CardView requestToSign,drawSign,logout,deleteUser;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onResume(){
        super.onResume();
        SignavApplication.getInstance().setConnectivityListener(this);
        ifAuthStateChanged();

    }


    @Override
    protected void onStart() {
        super.onStart();
        SignavApplication.getInstance().setConnectivityListener(this);
        ifAuthStateChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        ifAuthStateChanged();
    }

    private void ifAuthStateChanged() {
        new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        displayLoggedEmailID=findViewById(R.id.displayLoggedEmailID);
        requestToSign = findViewById(R.id.requestSign);
        drawSign = findViewById(R.id.drawSign);
        logout = findViewById(R.id.logout);
        deleteUser = findViewById(R.id.DeleteAccount);
        displayLoggedEmailID.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        requestToSign.setOnClickListener(this);
        drawSign.setOnClickListener(this);
        logout.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.requestSign:
                startActivityRequestSign();
                break;
            case R.id.drawSign:
                startActivityPendingSign();
                break;
            case R.id.logout:
                LogOut();
                break;
            case R.id.DeleteAccount:
                removeUser();
                break;
            default:
                 break;
        }
    }

    private void LogOut() {
        FirebaseAuth auth =FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startActivityPendingSign() {
        startActivity(new Intent(this, PendingRequestActivity.class));
        finish();
    }

    private void startActivityRequestSign() {
        startActivity(new Intent(this, RequestActivity.class));
        finish();
    }
    private void removeUser(){
        if (user != null) {
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(DashboardActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DashboardActivity.this, SignupActivity.class));
                        finish();
                    } else {
                        Toast.makeText(DashboardActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        ShowInternetConnectivity.showSnack(DashboardActivity.this,isConnected);
    }

}
