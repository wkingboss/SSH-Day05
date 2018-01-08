package com.lanou.domain;

import java.io.Serializable;

/**
 * Created by dllo on 18/1/8.
 */
public class Classes implements Serializable {
    private int cid;
    private String cname,cinfor;

    public Classes() {
    }

    public Classes(String cname, String cinfor) {
        this.cname = cname;
        this.cinfor = cinfor;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCinfor() {
        return cinfor;
    }

    public void setCinfor(String cinfor) {
        this.cinfor = cinfor;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", cinfor='" + cinfor + '\'' +
                '}';
    }
}

