package com.example.mvvm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Notes: MVVM's SQLite Entity
 * This MVVM project created by Sylvia on 10/2/2019 12:03 PM
 */

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;

    //id is auto-generated so cannot passing it
    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //the only value that don't have in constructor
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
