package com.android.io.github.vaidehighime.www.signavv001;

public class Issue {

    private String id ;
    private String subject;
    private String sender;
    private String receiver;
    private String date;
    private String fullDocument;
    private String toSignPage;
    private String status;
    private String gcode;

    public Issue(){}

    public void setIssue(String id, String subject, String sender, String receiver, String date, String fullDocument, String toSignPage, String status, String gcode) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.fullDocument = fullDocument;
        this.toSignPage = toSignPage;
        this.status = status;
        this.gcode = gcode;
    }

    public String getId() {
        return id;
    }


    public String getSubject() {

        return subject;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getFullDocument() {
        return fullDocument;
    }

    public String getToSignPage() {
        return toSignPage;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }


    public String getGcode() {
        return gcode;
    }


}
