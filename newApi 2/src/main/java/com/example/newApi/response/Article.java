package com.example.newApi.response;

import java.util.Date;

public class Article {
    private HeadlineSource source;
    private String author;
    private String title;
    private Object description;
    private String url;
    private Object urlToImage;
    private String publishedAt;
    private Object content;

    public Article(HeadlineSource source, String author, String title, Object description, String url, Object urlToImage, String publishedAt, Object content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    @Override
    public String toString(){
        StringBuilder sr = new StringBuilder();
        sr.append(source);
        sr.append(author);
        sr.append(title);
        sr.append(description);
        sr.append(url);
        sr.append(urlToImage);
        sr.append(publishedAt);
        sr.append(content);
        return sr.toString();
    }

    public Article() {
    }

    public HeadlineSource getSource() {
        return source;
    }

    public void setSource(HeadlineSource source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(Object urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
