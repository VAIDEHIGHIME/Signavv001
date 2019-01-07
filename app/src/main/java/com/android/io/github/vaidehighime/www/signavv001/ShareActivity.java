package com.android.io.github.vaidehighime.www.signavv001;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mashape.unirest.http.exceptions.UnirestException;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener{
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ImageView reviewDoc = findViewById(R.id.selectedDocReview);
        TextView reviewSender = findViewById(R.id.senderReview);
        TextView reviewReceiver = findViewById(R.id.receiverReview);
        LinearLayout content = findViewById(R.id.mainLayoutShare);
        TextView error = findViewById(R.id.Error);
        Button reRequestButton=findViewById(R.id.reRequestBtn);
        reRequestButton.setOnClickListener(this);
        Button signBtn=findViewById(R.id.signBtn);
        signBtn.setOnClickListener(this);
        Button rejectBtn=findViewById(R.id.rejectBtn);
        rejectBtn.setOnClickListener(this);
        String rec,sen;

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                content.setVisibility(View.GONE);
                uri=null;

            }
            else
            {
                error.setVisibility(View.GONE);
                sen=extras.getString("sender");
                rec=extras.getString("receiver");
                uri = getIntent().getData();

            }
        }
        else
        {
            error.setVisibility(View.GONE);
            sen=(String) savedInstanceState.getSerializable("sender");
            rec=(String) savedInstanceState.getSerializable("receiver");
            uri = getIntent().getData();

        }
        //reviewSender.bringToFront();
        //reviewSender.setText(sen);
        reviewSender.setText(getIntent().getStringExtra("sender"));
        //reviewReceiver.bringToFront();
        //reviewReceiver.setText(rec);
        reviewReceiver.setText(getIntent().getStringExtra("receiver"));

        if (null != uri) {
            reviewDoc.setImageURI(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rejectBtn:

                    rejectRequest();

                break;
            case R.id.signBtn:goToDrawSignActivity();break;

            default:break;
        }
    }

    private void goToDrawSignActivity() {
        Intent intent=new Intent(ShareActivity.this,DrawSignActivity.class);
        intent.setData( uri );
        startActivity(intent);
        finish();
    }

    private void rejectRequest() {
        //EmailWrapper.sendComplexMessage(getIntent().getStringExtra("receiver"),"Request Rejected");
        Toast.makeText(getApplicationContext(), "Rejected!", Toast.LENGTH_SHORT).show();

    }
}
