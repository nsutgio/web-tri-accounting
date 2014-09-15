package com.tri.erp.spring.model;

import javax.persistence.*;

/**
 * Created by TSI Admin on 9/15/2014.
 */
@Entity
@Table(name="business_activity")
public class BusinessActivity implements java.io.Serializable {
    @Id
    @GeneratedValue
    @Column(name = "bus_act_id")
    private int id;

    @Column(name = "bus_act_desc")
    private
    String description;

    @Column(name = "bus_act_code", unique = true)
    private
    String code;

    public BusinessActivity(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public BusinessActivity() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setOde(String ode) {
        this.code = ode;
    }
}
