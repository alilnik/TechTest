package com.drivemode.tech_test;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Person implements Parcelable {

    private String name, age, nationality, occupation;
    private final String NOTGIVEN = "NOT GIVEN";


    public Person(JSONObject jsonPerson) {
        name = jsonPerson.optString("name", NOTGIVEN);
        age = jsonPerson.optString("age", NOTGIVEN);
        occupation = jsonPerson.optString("occupation", NOTGIVEN);
        nationality = jsonPerson.optString("nationality", NOTGIVEN);

    }

    public Person(){
        name = NOTGIVEN;
        age = NOTGIVEN;
        occupation = NOTGIVEN;
        nationality = NOTGIVEN;
    }

    protected Person(Parcel in) {
        name = in.readString();
        age = in.readString();
        nationality = in.readString();
        occupation = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getNationality() {
        return nationality;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getMainInfo(){
        return name + " " + age;
    }

    public String getAdditionalInfo(){
        return occupation + " " + nationality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(nationality);
        dest.writeString(occupation);
        dest.writeString(NOTGIVEN);
    }
}
