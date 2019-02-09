package com.drivemode.tech_test;

import org.json.JSONObject;

public class Person {

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
}
