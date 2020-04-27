package models;

import java.util.List;

public class RecordingCollection {

    private String to;
    private String from;
    private int totalRecords;
    private int pageSize;
    private int pageCount;
    private String nextPageToken;
    private String totalSize;
    private String recordingCount;
    private List<Recording> meetings;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getRecordingCount() {
        return recordingCount;
    }

    public void setRecordingCount(String recordingCount) {
        this.recordingCount = recordingCount;
    }

    public List<Recording> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Recording> meetings) {
        this.meetings = meetings;
    }
}
