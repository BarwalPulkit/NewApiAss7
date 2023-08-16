package com.example.newApi.response;

import java.util.List;

public class SourceResponse {
    private String status;
    private List<Source> sources;

    public SourceResponse() {
    }

    @Override
    public String toString(){
        StringBuilder sr = new StringBuilder(status);
        for(Source source : sources){
            sr.append(source.toString());
        }
        return sr.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
