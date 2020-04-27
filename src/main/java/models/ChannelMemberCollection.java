package models;

import java.util.List;

public class ChannelMemberCollection {

    private int totalRecords;
    private int pageSize;
    private String nextPageToken;
    private List<ChannelMember> members;

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

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<ChannelMember> getMembers() {
        return members;
    }

    public void setMembers(List<ChannelMember> members) {
        this.members = members;
    }
}
