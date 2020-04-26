package models;

import com.sun.tools.javac.util.List;

public class ReportsPage {

    private int year;
    private int month;
    private List<Reports> dates;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Reports> getDates() {
        return dates;
    }

    public void setDates(List<Reports> dates) {
        this.dates = dates;
    }
}
