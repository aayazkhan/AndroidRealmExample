package com.transo.realmexample.model;

import android.view.View;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Book extends RealmObject {

    @PrimaryKey
    private String id;
    private String title;
    private String description;
    private Date addedOn;
    private Date modifiledOn;

    public Book() {
    }

    public Book(String id, String title, Date addedOn) {
        this.id = id;
        this.title = title;
        this.addedOn = addedOn;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public Date getModifiledOn() {
        return modifiledOn;
    }

    public void setModifiledOn(Date modifiledOn) {
        this.modifiledOn = modifiledOn;
    }

    public interface OnViewClickListner {
        void onClick(View v, int position, Book book);
    }

    public interface OnEditClickListner {
        void onClick(View v, int position, Book book);
    }

    public interface OnDeleteClickListner {
        void onClick(View v, int position, Book book);
    }
}
