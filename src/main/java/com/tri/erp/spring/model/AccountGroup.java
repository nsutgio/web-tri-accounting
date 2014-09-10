package com.tri.erp.spring.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by TSI Admin on 9/9/2014.
 */

@Entity
@Table(name="account_group")
public class AccountGroup {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "acct_group_id")
    private String accountGroupCode;

    @Column(name = "acct_group_desc")
    private String description;

    public AccountGroup(String accountGroupCode, String description) {
        this.accountGroupCode = accountGroupCode;
        this.description = description;
    }

    public AccountGroup() {}

    public String getAccountGroupCode() {
        return accountGroupCode;
    }

    /**
     * @param accountGroupCode
     */
    public void setAccountGroupCode(String accountGroupCode) {
        this.accountGroupCode = accountGroupCode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccountGroup) {
            return description.equalsIgnoreCase(((AccountGroup) obj).description);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public String toString() {
        return this.description;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}