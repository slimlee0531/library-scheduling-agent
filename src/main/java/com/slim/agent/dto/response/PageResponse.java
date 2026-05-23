package com.slim.agent.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class PageResponse<T> {

    private List<T> list;
    private long total;
    private int pageNum;
    private int pageSize;

    public PageResponse(List<T> list, long total, int pageNum, int pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
