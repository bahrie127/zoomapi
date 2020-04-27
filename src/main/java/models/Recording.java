package models;


import java.util.List;

public class Recording {

    private String uuid;
    private String id;
    private String accountId;
    private String hostId;
    private String topic;
    private String startTime;
    private String endTime;
    private int duration;
    private String totalSize;
    private String recordingCount;
    private List<RecordingFile> recordingFiles;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public List<RecordingFile> getRecordingFiles() {
        return recordingFiles;
    }

    public void setRecordingFiles(List<RecordingFile> recordingFiles) {
        this.recordingFiles = recordingFiles;
    }
}
