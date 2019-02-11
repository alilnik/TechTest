package com.drivemode.tech_test;

import java.util.List;

public interface PersonView {
    void showProgress();
    void hideProgress();
    void setData(List<Person> persons);
    void warnEmptyLIst();
}
