package com.mlc.file.parser;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date eventTime;
    private String originIp;
    private String httpMethod;
    private String httpResponse;
    private String userAgentRequestHeader;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return eventTime;
    }

    public void setTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getOriginIp() {
        return originIp;
    }

    public void setOriginIp(String originIp) {
        this.originIp = originIp;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(String httpResponse) {
        this.httpResponse = httpResponse;
    }

    public String getUserAgentRequestHeader() {
        return userAgentRequestHeader;
    }

    public void setUserAgentRequestHeader(String userAgentRequestHeader) {
        this.userAgentRequestHeader = userAgentRequestHeader;
    }

}
