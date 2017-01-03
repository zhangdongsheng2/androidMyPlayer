package net1;

import java.util.List;

public class Page<E> {
    private List<E> dataList;
    private int pageCount;
    private int pageNo = 1;
    private int pageSize;
    private int recordCount;//总共有多少数据

    public Page() {
        super();
    }

    public Page(int pageNum, int pageSize) {
        super();
        this.pageNo = pageNum;
        this.pageSize = pageSize;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

}
