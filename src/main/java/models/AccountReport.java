package models;

public class AccountReport {

    private String id;
    private String email;
    private String userName;
    private int type;
    private String dept;
    private int meetings;
    private int participants;
    private int meetingNumbers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public int getMeetingNumbers() {
        return meetingNumbers;
    }

    public void setMeetingNumbers(int meetingNumbers) {
        this.meetingNumbers = meetingNumbers;
    }
}
