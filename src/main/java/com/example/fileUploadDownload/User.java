package com.example.fileUploadDownload;

import com.opencsv.bean.*;

import java.util.ArrayList;
import java.util.List;

public class User {
    @CsvBindByPosition(position = 0)

    private String name;
    @CsvBindByPosition(position = 1)

    private String email;
    @CsvBindByPosition(position = 2)

    private String countryCode;
    @CsvBindAndSplitByPosition(collectionType = ArrayList.class, writeDelimiter = ";", elementType = String.class, position = 3)
    private List<String> no;

    public User() {

    }

    public User(String name, String email, String countryCode, List<String> no) {
        this.name = name;
        this.email = email;
        this.countryCode = countryCode;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<String> getNo() {
        return no;
    }

    public void setNo(List<String> no) {
        this.no = no;
    }
}
