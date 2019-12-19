package com.r00t.cifs.models;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String id;
    private String recordDate;
    private String recordTime;
    private String imageDataName;
    private String rawDataName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getImageDataName() {
        return imageDataName;
    }

    public void setImageDataName(String imageDataName) {
        this.imageDataName = imageDataName;
    }

    public String getRawDataName() {
        return rawDataName;
    }

    public void setRawDataName(String rawDataName) {
        this.rawDataName = rawDataName;
    }
}
