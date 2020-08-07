package org.vansoft.karmanyak.utils;

import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.model.User;

import java.util.ArrayList;
import java.util.List;

public class AllData {

    User user;
    List<Event> eventList = new ArrayList<>();

    public List<Event> getUpcommingEventList() {
        return upcommingEventList;
    }

    public void setUpcommingEventList(List<Event> upcommingEventList) {
        this.upcommingEventList = upcommingEventList;
    }

    public  void  addUpcomminEventList(Event event){
        this.upcommingEventList.add(event);
    }

    public  boolean isUpcommingEvent(Event e){
        for (Event event: this.upcommingEventList){
            if (e.getId().equals(event.getId())){
                return true;
            }
        }
        return false;
    }

    List<Event> upcommingEventList = new ArrayList<>();
    int point = 10;

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    private AllData(){

    }

    private static AllData allData;

    public static AllData getInstance(){
        if(allData==null){
            allData =new AllData();
        }


        return allData;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addEvent(Event event){
        this.eventList.add(event);
    }

    public List<Event> getEventList() {
        return this.eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void removeUpcommingEvent(Event event) {
        this.upcommingEventList.remove(event);
    }
}
