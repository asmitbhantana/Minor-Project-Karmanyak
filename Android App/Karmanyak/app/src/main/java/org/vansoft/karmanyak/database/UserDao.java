package org.vansoft.karmanyak.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.vansoft.karmanyak.model.User;

import java.util.List;

@Dao
public interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void addUser(User user);

    @Query("select * from User where id=:id")
     List<User> selectFromId(long id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
     void updateUser(User user);

    @Query("select * from User")
    List<User> getAllUSer();

    @Query("delete from User")
    void deletePreviousUser();
}
