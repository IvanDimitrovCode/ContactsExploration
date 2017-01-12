package com.example.ivandimitrov.contactsexploration;

import java.util.ArrayList;

/**
 * Created by Ivan Dimitrov on 1/12/2017.
 */

public class ContactNode {

    private String mName;
    private ArrayList<String> mMobilePhoneNumbers = new ArrayList<>();
    private ArrayList<String> mHomePhoneNumbers   = new ArrayList<>();
    private ArrayList<String> mWorkPhoneNumbers   = new ArrayList<>();
    private ArrayList<String> mEmailPhoneNumbers  = new ArrayList<>();

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<String> getMobileList() {
        return mMobilePhoneNumbers;
    }

    public void addMobileNumber(ArrayList<String> newNumber) {
        mMobilePhoneNumbers.addAll(newNumber);
    }

    public ArrayList<String> getEmailList() {
        return mEmailPhoneNumbers;
    }

    public void addEmailNumber(String newMobile) {
        mEmailPhoneNumbers.add(newMobile);
    }
}
