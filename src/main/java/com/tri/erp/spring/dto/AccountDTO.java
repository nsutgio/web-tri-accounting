package com.tri.erp.spring.dto;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.Objects;

/**
 * Created by arjayadong on 9/9/14.
 */
public class AccountDTO {

    private int id;
    private String code;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {

        return this.code + " => " + this.title;
    }
}
