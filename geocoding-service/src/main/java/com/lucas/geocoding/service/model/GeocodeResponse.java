package com.lucas.geocoding.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GeocodeResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeocodeResponse {

    @XmlElement(name = "result")
    private Result result;

    @XmlElement(name = "status")
    private String status;

    public Result getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
