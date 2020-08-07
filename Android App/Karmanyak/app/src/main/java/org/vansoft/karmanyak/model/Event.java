package org.vansoft.karmanyak.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Entity(tableName = "VisitedEvent")
@Entity(tableName = "Event")
public class Event {
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    @NonNull
    private String id;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("eventDesc")
    @Expose
    private String eventDesc;
    @SerializedName("reward")
    @Expose
    private Integer reward;
    @SerializedName("eventLocation")
    @Expose
    private String eventLocation;
    @SerializedName("eventManager")
    @Expose
    private String eventManager;
    @SerializedName("requiredVolunteer")
    @Expose
    private Integer requiredVolunteer;
    @SerializedName("eventImageSrc")
    @Expose
    private String eventImageSrc;
    @SerializedName("eventDate")
    @Expose
    private String eventDate;
    @SerializedName("eventQrCode")
    @Expose
    private String eventQrCode;

    @SerializedName("eventType")
    @Expose
    private String eventType;
    @SerializedName("eventEndDate")
    @Expose
    private String eventEndDate;

    @SerializedName("__v")
    @Expose
    private Integer v;

    public Event(){

    }

    public String getEventQrCode() {
        return eventQrCode;
    }

    public void setEventQrCode(String eventQrCode) {
        this.eventQrCode = eventQrCode;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventManager() {
        return eventManager;
    }

    public void setEventManager(String eventManager) {
        this.eventManager = eventManager;
    }

    public Integer getRequiredVolunteer() {
        return requiredVolunteer;
    }

    public void setRequiredVolunteer(Integer requiredVolunteer) {
        this.requiredVolunteer = requiredVolunteer;
    }

    public String getEventImageSrc() {
        return eventImageSrc;
    }

    public void setEventImageSrc(String eventImageSrc) {
        this.eventImageSrc = eventImageSrc;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
