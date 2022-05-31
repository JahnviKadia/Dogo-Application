package com.example.myapplication;

public class problems {
    String Problems;
    String Symptoms;
    String Treatment;
    public problems() {
        // empty constructor
        // required for Firebase.
    }

    // Constructor for all variables.
    public problems(String courseName, String courseDescription, String courseDuration) {
        this.Problems = courseName;
        this.Symptoms = courseDescription;
        this.Treatment = courseDuration;
    }

    public String getProblems() {
        return Problems;
    }

    public void setProblems(String problems) {
        this.Problems = problems;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.Symptoms = symptoms;
    }

    public String getTreatment() {
        return Treatment;
    }

    public void setTreatment(String treatment) {
        this.Treatment = treatment;
    }
}
