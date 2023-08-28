package com.it.pojo;

import java.util.List;

public class TaskCommon {
    private String type;
    private String inspector;
    private List<CheckedCommon> checkedList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public List<CheckedCommon> getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(List<CheckedCommon> checkedList) {
        this.checkedList = checkedList;
    }

    @Override
    public String toString() {
        return "TaskCommon{" +
                "type='" + type + '\'' +
                ", inspector='" + inspector + '\'' +
                ", checkedList=" + checkedList +
                '}';
    }
}
