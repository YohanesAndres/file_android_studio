package com.If5a.booksdictionary.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int id;
    private String ISBN;
    private String Title;
    private String Author;
    private String Year;
    private String Publisher;
    private String S,M,L;


    public Book() {
    }

    public Book(String ISBN, String title, String author, String year, String publisher, String s, String m, String l) {
        this.ISBN = ISBN;
        this.Title = title;
        this.Author = author;
        this.Year = year;
        this.Publisher = publisher;
        this.S = s;
        this.M = m;
        this.L = l;
    }

    public Book(int id, String ISBN, String title, String author, String year, String publisher, String s, String m, String l) {
        this.id = id;
        this.ISBN = ISBN;
        this.Title = title;
        this.Author = author;
        this.Year = year;
        this.Publisher = publisher;
        this.S = s;
        this.M = m;
        this.L = l;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        ISBN = in.readString();
        Title = in.readString();
        Author = in.readString();
        Year = in.readString();
        Publisher = in.readString();
        S = in.readString();
        M = in.readString();
        L = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }

    public String getL() {
        return L;
    }

    public void setL(String l) {
        L = l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(ISBN);
        parcel.writeString(Title);
        parcel.writeString(Author);
        parcel.writeString(Year);
        parcel.writeString(Publisher);
        parcel.writeString(S);
        parcel.writeString(M);
        parcel.writeString(L);
    }
}
