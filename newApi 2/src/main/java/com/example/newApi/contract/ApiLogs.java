package com.example.newApi.contract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class ApiLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String endPoint;
    private String request;
    private String response;
    private Long timeTaken;
    private String userId;
    private LocalDate date;

    public ApiLogs(String endPoint, String request, String response, Long timeTaken, String userId, LocalDate date) {
        this.endPoint = endPoint;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
        this.userId = userId;
        this.date = date;
    }
    public ApiLogs() {
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getUserId() {
        return userId;
    }
    public String setUserId(String userId) {
        return this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
