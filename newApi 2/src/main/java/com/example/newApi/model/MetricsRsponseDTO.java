package com.example.newApi.model;

public class MetricsRsponseDTO {
    private String numOfCalls;
    private String avgTimeTaken;
    private String p99TimeTaken;

    public MetricsRsponseDTO(String numOfCalls, String avgTimeTaken, String p99TimeTaken) {
        this.numOfCalls = numOfCalls;
        this.avgTimeTaken = avgTimeTaken;
        this.p99TimeTaken = p99TimeTaken;
    }
    public MetricsRsponseDTO() {
    }
    public String getNumOfCalls() {
        return numOfCalls;
    }
    public void setNumOfCalls(String numOfCalls) {
        this.numOfCalls = numOfCalls;
    }
    public String getAvgTimeTaken() {
        return avgTimeTaken;
    }
    public void setAvgTimeTaken(String avgTimeTaken) {
        this.avgTimeTaken = avgTimeTaken;
    }
    public String getP99TimeTaken() {
        return p99TimeTaken;
    }
    public void setP99TimeTaken(String p99TimeTaken) {
        this.p99TimeTaken = p99TimeTaken;
    }

}
