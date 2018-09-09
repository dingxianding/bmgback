package com.example.dto;

public class Header {
    private String title;
    private String dataIndex;
    private String align;
    private String fixed;
    private boolean sorter;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public boolean isSorter() {
        return sorter;
    }

    public void setSorter(boolean sorter) {
        this.sorter = sorter;
    }
}
