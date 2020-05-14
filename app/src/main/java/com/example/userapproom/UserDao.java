package com.example.userapproom;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);

    @Query("DELETE from user_table")
    void deleteALl();

    @Query("SELECT * FROM user_table ORDER BY name ASC")
    LiveData<List<User>> getAllUsers();
}
