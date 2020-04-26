package models;

public class Reports {

    private String date;
    private int newUsers;
    private int meetings;
    private int participants;
    private int meetingMinutes;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }

    public int getMeetings() {
        return meetings;
    }

    public void setMeetings(int meetings) {
        this.meetings = meetings;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getMeetingMinutes() {
        return meetingMinutes;
    }

    public void setMeetingMinutes(int meetingMinutes) {
        this.meetingMinutes = meetingMinutes;
    }
}
