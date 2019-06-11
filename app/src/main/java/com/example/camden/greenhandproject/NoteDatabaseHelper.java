package com.example.camden.greenhandproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

public class NoteDatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "NoteDatabaseHelper";
    public static final String DB_NAME = "Note";
    private static final String CREATE_NOTE_DB = "create table Note (" +
            "id integer primary key autoincrement, " +
            "uuid text, " +
            "type text, " +
            "title text, " +
            "content text, " +
            "picture_link text )";

    public NoteDatabaseHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE_DB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNote(NoteBean bean){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getUuid());
        values.put("type",bean.getType());
        values.put("title",bean.getTitle());
        values.put("content",bean.getContent());
        values.put("picture_link",bean.getPictureLink());
        db.insert(DB_NAME,null,values);

    }

    public void removeNote(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});
    }

    public void editNote(String uuid,NoteBean bean){
        removeNote(uuid);
        bean.setUuid(uuid);
        addNote(bean);
    }

    public LinkedList<NoteBean> readNotes(){

        LinkedList<NoteBean> notes = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("Note",null,null,null,null,null,null);
        if (cursor.moveToFirst()){

            do {

                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String picture_link = cursor.getString(cursor.getColumnIndex("picture_link"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));


                NoteBean note = new NoteBean();
                note.setTitle(title);
                note.setContent(content);
                note.setPictureLink(picture_link);
                note.setType(type);
                note.setUuid(uuid);

                notes.add(note);


            }while (cursor.moveToNext());


        }

        return notes;

    }
}
