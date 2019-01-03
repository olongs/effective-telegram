package com.test.model;

import java.sql.Date;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: Good
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 9:11
 */
public class GoodInfo {

    private Integer id=null;
    private String gname=null;
    private String brand=null;
    private String size=null;
    private Date update=null;
    private String tnames=null;
    private String tids=null;

    @Override
    public String toString() {
        return "GoodInfo{" +
                "id=" + id +
                ", gname='" + gname + '\'' +
                ", brand='" + brand + '\'' +
                ", size='" + size + '\'' +
                ", update=" + update +
                ", tnames='" + tnames + '\'' +
                ",tids="+tids+'\''+
                '}';
    }

    public String getTids() {
        return tids;
    }

    public void setTids(String tids) {
        this.tids = tids;
    }

    public String getTnames() {
        return tnames;
    }

    public void setTnames(String tnames) {
        this.tnames = tnames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }
}
