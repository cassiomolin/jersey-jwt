package com.cassiomolin.example.common.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data transfer object that holds details about an API error.
 *
 * @author cassiomolin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDetails {

    private Integer status;
    private String title;
    private String message;
    private String path;

    public ApiErrorDetails() {

    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}