package com.android.io.github.vaidehighime.www.signavv001;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.Random;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 1, SELECT_PDF = 2;
    private Uri selectedImageUri, selectedPdfUri;
    private String receiver, subject, image, pdf,id;
    private ImageButton doc, fullDoc;
    private EditText rec;
    private EditText sub;
    private DatabaseReference dbRef;
    private StorageReference stRef;
    private FirebaseUser user;
    private Issue issue;

    private void fillData(){
        id="issueID"+System.currentTimeMillis();
        receiver=rec.getText().toString();
        subject=sub.getText().toString();
        image= selectedImageUri.getLastPathSegment();
        pdf= selectedPdfUri.getLastPathSegment();
    }

    private void setIssue() {
        fillData();
        issue = new Issue();
        issue.setIssue(id,subject, user.getEmail(), receiver, java.util.Calendar.getInstance().getTime().toString(), pdf, image, "JustRequested", "not signed till now");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        init();
    }

    private void init() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        doc = findViewById(R.id.btnSelectImage);
        fullDoc = findViewById(R.id.btnSelectPdf);
        findViewById(R.id.reviewRequest).setOnClickListener(this);
        rec = findViewById(R.id.receiver);
        sub = findViewById(R.id.subject);
        doc.setOnClickListener(this);
        fullDoc.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        stRef = FirebaseStorage.getInstance().getReference();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    doc.setBackgroundColor(getColor(R.color.btn_logut_bg));
                }
                else{
                    Toast.makeText(RequestActivity.this, "No Image selected!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (requestCode == SELECT_PDF) {
                    selectedPdfUri = data.getData();
                    if (null != selectedPdfUri) {
                        fullDoc.setBackgroundColor(getColor(R.color.btn_logut_bg));
                    }
                    else{
                        Toast.makeText(RequestActivity.this, "No Pdf Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSelectImage:
                openImageChooser();
                break;
            case R.id.reviewRequest:
                openShareActivity();
                break;
            case R.id.btnSelectPdf:
                openPdfChooser();
                break;
            default:
                break;
        }

    }

    private void openPdfChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), SELECT_PDF);
    }

    private void openShareActivity() {
        if (rec.getText() != null) {
            if (sub.getText()!= null) {
                if (selectedImageUri!= null) {
                    if (selectedPdfUri != null) {
                        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                        setIssue();
                        upload();
                        Intent intent = packData();
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RequestActivity.this, "Please Select Document for Review", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RequestActivity.this, "Please Select Image To sign", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RequestActivity.this, "Please Enter theSubject", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(RequestActivity.this, "Please Enter the Receivers email ID", Toast.LENGTH_SHORT).show();
        }

    }

    private Intent packData() {
        receiver = rec.getText().toString();
        subject = sub.getText().toString();
        Intent intent = new Intent(RequestActivity.this, ShareActivity.class);
        intent.setData(selectedImageUri);
        intent.setData(selectedPdfUri);
        intent.putExtra("receiver", receiver);
        intent.putExtra("subject", subject);
        return (intent);

    }


    private void addToDatabase() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef.child(id).setValue(issue);
                Toast.makeText(RequestActivity.this, "Request Sent!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RequestActivity.this, "Error Sending Request", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void upload(){
        final StorageReference imagePath = stRef.child(id).child(Objects.requireNonNull(selectedImageUri.getLastPathSegment()));
        final StorageReference pdfPath = stRef.child(id).child(Objects.requireNonNull(selectedPdfUri.getLastPathSegment()));

        pdfPath.putFile(selectedPdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                imagePath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        addToDatabase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RequestActivity.this,"Error Sending Request",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RequestActivity.this,"Error Sending Request",Toast.LENGTH_SHORT).show();
            }
        });

    }
}