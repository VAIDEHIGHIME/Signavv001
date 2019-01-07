package com.android.io.github.vaidehighime.www.signavv001;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;


public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {


    //this context we will use to inflate the layout
    private final Context mCtx;

    //we are storing all the issues in a list
    private final List<Issue> issueList;

    //getting the context and issue list with constructor
    public IssueAdapter(Context mCtx, List<Issue> issueList) {
        this.mCtx = mCtx;
        this.issueList = issueList;
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.pending_item_layout, null);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {
        //getting the issue of the specified position
        Issue issue = issueList.get(position);
        //binding the data with the viewholder views
        holder.textViewId.setText(format("%d", issue.getId()));
        holder.textViewSubject.setText(issue.getSubject());
        holder.textViewSender.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
      //  holder.textViewReceiver.setText(issue.getReceiver());
        holder.textViewDate.setText(java.util.Calendar.getInstance().getTime().toString());
      //  holder.textViewStatus.setText(format("%d", issue.getStatus()));
        //holder.imageViewtoSignPage.setImageDrawable(mCtx.getResources().getDrawable(issue.getToSignPage()));
       // holder.imageViewgcode.setImageDrawable(mCtx.getResources().getDrawable(issue.getGcode()));
       // holder.imageViewfullDocument.setImageDrawable(mCtx.getResources().getDrawable(issue.getFullDocument()));
    }


    @Override
    public int getItemCount() {
        return issueList.size();
    }


    class IssueViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewId;
        final TextView textViewSubject;
        final TextView textViewSender;
        //final TextView textViewReceiver;
        final TextView textViewDate;
       // final TextView textViewStatus;
        final ImageView imageViewfullDocument;
        final ImageView imageViewtoSignPage;
       // final ImageView imageViewgcode;

        public IssueViewHolder(View itemView) {
            super(itemView);
            textViewSender = itemView.findViewById(R.id.textViewSender);
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
           // textViewReceiver = itemView.findViewById(R.id.textViewReceiver);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewDate = itemView.findViewById(R.id.textViewDate);
          //  textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageViewfullDocument =itemView.findViewById(R.id.imageViewfullDocument);
            imageViewtoSignPage = itemView.findViewById(R.id.imageViewtoSignDocument);
            //imageViewgcode = itemView.findViewById(R.id.imageViewgcode);
        }
    }
}
