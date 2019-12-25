package com.example.control.model;

import java.util.Date;
import androidx.annotation.NonNull;


public class ReportCard {
    private long reportCardDate;
    private long dateOfWork;
    private String hours;
    private String reportCardText;
    private String dateOfWorkString;


    public ReportCard() { }
    public ReportCard(long dateOfWork, String hours, String reportCardText){
        this.reportCardDate = new Date().getTime();
        this.dateOfWork = dateOfWork;
        this.hours = hours;
        this.reportCardText = reportCardText;
    }

    public String getHours() {
        return this.hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getReportCardText() {
        return reportCardText;
    }

    public void setReportCardText(String reportCardText) {
        this.reportCardText = reportCardText;
    }

    public long getReportCardDate() {
        return reportCardDate;
    }

    public void setReportCardDate(long reportCardDate) {
        this.reportCardDate = reportCardDate;
    }
    public long getDateOfWork() {
        return dateOfWork;
    }
    public String getDateOfWorkString() {
        return android.text.format.DateFormat.format("dd.MM.yyyy", new Date(this.dateOfWork)).toString(); //TODO CONVERT LONG DATE TO STRING DATE
    }

    public void setDateOfWork(long dateOfWork) {
        this.dateOfWork = dateOfWork;
    }

    public void setDateOfWorkString(String dateOfWorkString) {
        this.dateOfWorkString = dateOfWorkString;
    }

    @NonNull
    @Override
    public String toString() {
        return "hours = " + getHours() + " description = " + getReportCardText();
    }
}
