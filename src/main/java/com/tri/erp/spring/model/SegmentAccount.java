package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Created by TSI Admin on 9/15/2014.
 */

@Entity
@Table(name = "segment_accounts")
@AssociationOverrides({
        @AssociationOverride(name = "pk.account",
                joinColumns = @JoinColumn(name = "acct_id")),
        @AssociationOverride(name = "pk.businessSegment",
                joinColumns = @JoinColumn(name = "bus_seg_id")) })
public class SegmentAccount  implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "seg_acct_id")
    private int id;

    private SegmentAccountId pk = new SegmentAccountId();

    @Column(name = "acct_code")
    private String accountCode;

    public SegmentAccount() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @EmbeddedId
    public SegmentAccountId getPk() {
        return pk;
    }

    @Transient
    public Account getAccount() {
        return getPk().getAccount();
    }

    public void setAccount(Account account) {
        this.getPk().setAccount(account);
    }

    @Transient
    public BusinessSegment getBusinessSegment() {
        return getPk().getBusinessSegment();
    }

    public void setBusinessSegment(BusinessSegment segment) {
        this.getPk().setBusinessSegment(segment);
    }

    public void setPk(SegmentAccountId pk) {
        this.pk = pk;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SegmentAccount that = (SegmentAccount) o;

        if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
