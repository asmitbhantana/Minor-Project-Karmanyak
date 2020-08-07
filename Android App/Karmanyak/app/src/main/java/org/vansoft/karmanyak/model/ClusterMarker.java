package org.vansoft.karmanyak.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

    private  LatLng latLng;
    private String title;
    private String desc;
    private Event event;
    private int iconPicture;


    public ClusterMarker(LatLng latLng, String title, String desc, Event event, int iconPicture) {
        this.latLng = latLng;
        this.title = title;
        this.desc = desc;
        this.event = event;
        this.iconPicture = iconPicture;
    }

    @Override
    public LatLng getPosition() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getIconPicture() {
        return iconPicture;
    }

    public void setIconPicture(int iconPicture) {
        this.iconPicture = iconPicture;
    }

}
