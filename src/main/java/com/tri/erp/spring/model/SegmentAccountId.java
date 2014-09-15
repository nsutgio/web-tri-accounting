package com.tri.erp.spring.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Created by TSI Admin on 9/15/2014.
 */
@Embeddable
public class SegmentAccountId implements java.io.Serializable {

    private Account account;
    private BusinessSegment businessSegment;

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    public BusinessSegment getBusinessSegment() {
        return businessSegment;
    }

    public void setBusinessSegment(BusinessSegment businessSegment) {
        this.businessSegment = businessSegment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SegmentAccountId that = (SegmentAccountId) o;

        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (businessSegment != null ? !businessSegment.equals(that.businessSegment) : that.businessSegment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = account.hashCode();
        result = 31 * result + businessSegment.hashCode();
        return result;
    }
}
