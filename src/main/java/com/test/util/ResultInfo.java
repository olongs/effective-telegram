package com.test.util;

import java.util.ArrayList;
import java.util.List;

public class ResultInfo<T> {
    private List<T> list = new ArrayList<T>();
    private Long total = 0L;
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
	

}
