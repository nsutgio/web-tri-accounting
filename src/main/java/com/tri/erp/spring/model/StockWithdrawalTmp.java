package com.tri.erp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by Ryan D. Repe on 10/5/2014.
 */
@Entity
@Table(name = "stock_withdrawal_tmp")
public class StockWithdrawalTmp {

    @Id
    @Column(name = "stock_withdrawal_id")
    private int id;

    @Column(name = "trans_id")
    private int transId;

    @Column(name = "trans_date")
    private java.sql.Date transDate;

    @Column(name = "dept_id")
    private int deptId;

    @Column
    private String purpose;

    @Column
    private String description;

    @Column(name = "inv_loc_id")
    private int locationId;

    @Column(name = "inv_cat_id")
    private int categoryId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "approved_by")
    private int approvedBy;

    @Column(name = "received_by")
    private int receivedBy;

    @Column
    private int requisitioned;

    @Column(name = "prepared_by")
    private int preparedBy;

    @Column(name = "noted_by")
    private int notedBy;

    @Column(name = "issued_by")
    private int issuedBy;

    @Column(name = "doc_status_id")
    private int docStatusId;

    @Column
    private String remarks;

    @Column(name = "trans_date_time")
    private java.sql.Date transDateTime;

    @Column(name = "request_type")
    private int requestType;

    @Column(name = "sw_number")
    private String referenceNo;

    public StockWithdrawalTmp(int id, int transId, Date transDate, int deptId, String purpose, String description, int locationId, int categoryId, int userId, int approvedBy, int receivedBy, int requisitioned, int preparedBy, int notedBy, int issuedBy, int docStatusId, String remarks, Date transDateTime, int requestType, String referenceNo) {
        this.id = id;
        this.transId = transId;
        this.transDate = transDate;
        this.deptId = deptId;
        this.purpose = purpose;
        this.description = description;
        this.locationId = locationId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.approvedBy = approvedBy;
        this.receivedBy = receivedBy;
        this.requisitioned = requisitioned;
        this.preparedBy = preparedBy;
        this.notedBy = notedBy;
        this.issuedBy = issuedBy;
        this.docStatusId = docStatusId;
        this.remarks = remarks;
        this.transDateTime = transDateTime;
        this.requestType = requestType;
        this.referenceNo = referenceNo;
    }

    public StockWithdrawalTmp() {
    }
}

