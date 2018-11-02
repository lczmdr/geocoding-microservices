package com.lucas.geocoding.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {

    @XmlElement(name = "formatted_address")
    private String formattedAddress;

    @XmlElement(name = "geometry")
    private Geometry geometry;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

}
