package org.example.studyroom.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 分页返回结果
 */
public class PageResult<T> {

    private List<T> records;
    private long total;
    private long current;
    private long size;

    public PageResult() {}

    public PageResult(IPage<T> page) {
        this.records = page.getRecords();
        this.total = page.getTotal();
        this.current = page.getCurrent();
        this.size = page.getSize();
    }

    public List<T> getRecords() { return records; }
    public void setRecords(List<T> records) { this.records = records; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public long getCurrent() { return current; }
    public void setCurrent(long current) { this.current = current; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
}
