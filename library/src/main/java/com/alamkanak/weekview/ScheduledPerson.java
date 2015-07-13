package com.alamkanak.weekview;

import com.adtech.webservice.daomain.Doctor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduledPerson {
    @SerializedName("tList")
    private List<Doctor> doctors;
    private String messageStatus;

    public ScheduledPerson() {}

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }
}
