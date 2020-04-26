package models;

import java.util.List;

public class UserPage {

    private int pageCount;
    private int pageNumber;
    private int pageSize;
    private int totalRecords;
    private List<UserFile> user;

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

    public List<UserFile> getUser() {
        return user;
    }

    public void setUser(List<UserFile> user) {
        this.user = user;
    }
}
