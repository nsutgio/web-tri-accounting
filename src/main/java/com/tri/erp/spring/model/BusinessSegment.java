package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by TSI Admin on 9/15/2014.
 */
@Entity
@Table(name="business_segment")
public class BusinessSegment implements java.io.Serializable {
    @Id
    @GeneratedValue
    @Column(name = "bus_seg_id")
    private int id;

    @Column(name = "bus_seg_desc")
    private
    String description;

    @Column(name = "bus_seg_code")
    private
    String code;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bus_act_id")
    private BusinessActivity businessActivity;

    public BusinessSegment(String description, String code, BusinessActivity businessActivity) {
        this.description = description;
        this.code = code;
        this.businessActivity = businessActivity;
    }

    public BusinessSegment() {
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

    public void setCode(String ode) {
        this.code = ode;
    }

    public BusinessActivity getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(BusinessActivity businessActivity) {
        this.businessActivity = businessActivity;
    }

}
