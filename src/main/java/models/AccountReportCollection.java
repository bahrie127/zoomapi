package models;

import java.util.List;

public class AccountReportCollection {

    private String from;
    private String to;
    private int pageCount;
    private int pageNumber;
    private int pageSize;
    private int totalRecords;
    private int totalMeetings;
    private int totalParticipants;
    private int totalMeetingMinutes;
    private List<AccountReport> users;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalMeetings() {
        return totalMeetings;
    }

    public void setTotalMeetings(int totalMeetings) {
        this.totalMeetings = totalMeetings;
    }

    public int getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public int getTotalMeetingMinutes() {
        return totalMeetingMinutes;
    }

    public void setTotalMeetingMinutes(int totalMeetingMinutes) {
        this.totalMeetingMinutes = totalMeetingMinutes;
    }

    public List<AccountReport> getUsers() {
        return users;
    }

    public void setUsers(List<AccountReport> users) {
        this.users = users;
    }
}
