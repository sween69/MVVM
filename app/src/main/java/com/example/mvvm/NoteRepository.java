package com.example.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Notes: MVVM's Repository
 * This MVVM project created by Sylvia on 10/2/2019 12:56 PM
 */

// Repository provide abstract layout between the different data source(internet & locally) & the rest of the app,
// without it will need to read through the different media of the db source, design when to fetch data from where and api call,
// this provide the clean api for rest of them, so ViewModal doesn't have to care where the data came from or how it fetch,
// it just call the method from repository directly
public class NoteRepository {

    private  NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public  NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao(); //usually can't call abstract method, but since we create our NoteDatabase instance with builder, so Room will generate the necessary code (Room to subclasses our abstract class)
        allNotes = noteDao.getAllNotes(); //Room also auto-generate for this method
    }

    //all of this method are the api of repository expose to our app (ViewModel call)
    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    //return allNotes retrieve from our noteDao,
    // Room will automatically execute the database operation that return the select data on the background context
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    // AsyncTask for sql operation
    //static because it doesn't have a reference to the repository, otherwise will cause the memory leak
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao; //for make database operation, eg: query

        //Since the class is static and we cannot access the NoteDao of our repository directly, so we have to pass it over the constructor
        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao; //for make database operation, eg: query

        //Since the class is static and we cannot access the NoteDao of our repository directly, so we have to pass it over the constructor
        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao; //for make database operation, eg: query

        //Since the class is static and we cannot access the NoteDao of our repository directly, so we have to pass it over the constructor
        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao; //for make database operation, eg: query

        //Since the class is static and we cannot access the NoteDao of our repository directly, so we have to pass it over the constructor
        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
