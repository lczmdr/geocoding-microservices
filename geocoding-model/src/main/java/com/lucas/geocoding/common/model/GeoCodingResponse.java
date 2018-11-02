package com.lucas.geocoding.common.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Geo coding response.
 */
@XmlRootElement(name = "GeoCodingResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeoCodingResponse {

    /** The formatted address. */
    private String formattedAddress;

    /** The latitude. */
    private String latitude;

    /** The longitude. */
    private String longitude;

    /**
     * Gets formatted address.
     *
     * @return the formatted address
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     * Sets formatted address.
     *
     * @param formattedAddress the formatted address
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
