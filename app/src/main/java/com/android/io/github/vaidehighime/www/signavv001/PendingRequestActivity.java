package com.android.io.github.vaidehighime.www.signavv001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class PendingRequestActivity extends AppCompatActivity {

    //a list to store all the issues
    List<Issue> issueList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the issuelist
        issueList = new ArrayList<>();


        //adding some items to our list
//        issueList.add(
//                new Issue(
//                        1,
//                        "Pav Bhaji Dedo",
//                        "vaidehighime@gmail.com",
//                        "manjushaghime@gmail.com",
//                        "1/2/34",
//                        R.drawable.logo,
//                        R.drawable.logo,
//                        1,
//                        R.drawable.logo
//                      ));
//        issueList.add(
//                new Issue(
//                        1,
//                        "Pav Bhaji Dedo",
//                        "vaidehighime@gmail.com",
//                        "manjushaghime@gmail.com",
//                        "1/2/34",
//                        R.drawable.logo,
//                        R.drawable.logo,
//                        1,
//                        R.drawable.logo
//                ));
//




        //creating recyclerview adapter
        IssueAdapter adapter = new IssueAdapter(this, issueList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}


