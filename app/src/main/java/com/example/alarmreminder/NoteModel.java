package com.example.alarmreminder;

public class NoteModel {
    String note_title;
    String note_description;
    String id;

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_description() {
        return note_description;
    }

    public void setNote_description(String note_description) {
        this.note_description = note_description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NoteModel(String id, String note_title, String note_description) {
        this.note_title = note_title;
        this.note_description = note_description;
        this.id = id;
    }

}
