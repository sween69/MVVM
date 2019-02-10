package com.example.mvvm;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Notes: MVVM's Dao (Data Access Object) - LiveData
 * This MVVM project created by Sylvia on 10/2/2019 12:11 PM
 */

//Doa have to be abstract or interface cuz we don't create the body
//for make database operation, eg: query
@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
