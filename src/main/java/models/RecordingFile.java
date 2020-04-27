package models;

public class RecordingFile {

    private String id;
    private String meetingId;
    private String recordingStart;
    private String recordingEnd;
    private String fileType;
    private Number fileSize;
    private String downloadUrl;
    private String status;
    private String deletedTime;
    private String recordingType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getRecordingStart() {
        return recordingStart;
    }

    public void setRecordingStart(String recordingStart) {
        this.recordingStart = recordingStart;
    }

    public String getRecordingEnd() {
        return recordingEnd;
    }

    public void setRecordingEnd(String recordingEnd) {
        this.recordingEnd = recordingEnd;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Number getFileSize() {
        return fileSize;
    }

    public void setFileSize(Number fileSize) {
        this.fileSize = fileSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(String deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(String recordingType) {
        this.recordingType = recordingType;
    }
}
