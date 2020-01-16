package com.android.app.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
    private String countryCode = "+55";
    private String ddd;
    private String number;

    public PhoneNumber() {

    }

    public PhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replace("-", "").replace("()", "").replace(" ", "");
        Log.d("cassio", phoneNumber);
        if (phoneNumber.charAt(0) == '+') {//+5577999116731
            countryCode = phoneNumber.substring(0, 3);
            ddd = phoneNumber.substring(3, 5);
            number = phoneNumber.substring(5);
        } else {//77999116731
            ddd = phoneNumber.substring(1, 3);
            number = phoneNumber.substring(4);
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setDDD(String ddd) {
        if (ddd.charAt(0) == '(') {
            this.ddd = ddd.substring(1, 3);
        } else {
            this.ddd = ddd;
        }
    }

    public String getDDD() {
        return ("(").concat(ddd).concat(")");
    }

    public String getNumber() {
        return number.charAt(0)+" "+number.substring(1, 5)+"-"+number.substring(5);
    }

    public String getPhoneNumber() {
        return getDDD() + " " + getNumber();
    }

    @Override
    public String toString() {// +55 (77) 999116731
        return countryCode + " " + getDDD() + " " + getNumber();
    }
}
