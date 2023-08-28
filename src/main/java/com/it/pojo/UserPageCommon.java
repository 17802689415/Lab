package com.it.pojo;

public class UserPageCommon {
    private int currentPage;
    private int pageSize;
    private String empId;

    @Override
    public String toString() {
        return "UserPageCommon{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", empId='" + empId + '\'' +
                '}';
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
