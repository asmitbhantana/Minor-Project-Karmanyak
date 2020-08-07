package org.vansoft.karmanyak.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.vansoft.karmanyak.model.Event;

import java.util.List;

import jnr.ffi.annotations.In;

@Dao
public interface EventDao {
    @Query("select * from Event")
    List<Event> getAllEvent();

    @Insert
    void addEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Update
    void upDateEvent(Event event);

    @Query("select * from Event where eventQrCode=:code")
    List<Event> getEventByCode(String code);
}
