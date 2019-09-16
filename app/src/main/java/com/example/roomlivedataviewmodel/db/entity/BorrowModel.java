package com.example.roomlivedataviewmodel.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.roomlivedataviewmodel.db.converters.DateConverter;

import java.util.Date;

@Entity
public class BorrowModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String itemName;
    private String personName;
    @TypeConverters(DateConverter.class)
    private Date borrowData;

    public BorrowModel(String itemName, String personName) {
        this.itemName = itemName;
        this.personName = personName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPersonName() {
        return personName;
    }

    public Date getBorrowData() {
        return borrowData;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setBorrowData(Date borrowData) {
        this.borrowData = borrowData;
    }
}
