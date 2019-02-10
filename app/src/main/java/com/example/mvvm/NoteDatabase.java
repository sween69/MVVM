package com.example.mvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Notes: MVVM's Room Database
 * This MVVM project created by Sylvia on 10/2/2019 12:21 PM
 */

//Database have to be abstract class, abstract class can't be instantiated new object
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    //for singleton
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    //synchronized mean only one thread at the time can access the method,
    //so won't accidentally create two instance db and when two different thread try to access this method at the same time
    public static synchronized NoteDatabase getInstance(Context context) {

        //establish the database only if we don't already have the instance
        if (instance == null) {
            //abstract class can't be instantiated new object
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration() //for when increase the version of db, we have to make rules on how to migrate the db schema, it will delete it & recreate the db
                    .addCallback(roomCallback)//when instance was first created, it will execute the onCreate method on roomCallback, and populate our database
                    .build();
        }
        //if !null
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        //Called when the database is created for the first time. This is called after all the tables are created.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        //Since table first time create no member, so we get it from db
        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}
