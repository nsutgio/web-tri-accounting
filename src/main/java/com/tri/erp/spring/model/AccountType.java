package com.tri.erp.spring.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Created by TSI Admin on 9/9/2014.
 */

@Entity
@Table(name="account_type")
public class AccountType {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String description;

    @Column
    private String code;

    @OneToMany(mappedBy="accountType")
    private Set<AccountGroup> accountGroupSet;

    public AccountType(String description, String code, Set<AccountGroup> accountGroupSet) {
        this.description = description;
        this.code = code;
        this.accountGroupSet = accountGroupSet;
    }

    public AccountType() {}
    /**
     * @return the acctTypeDesc
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param acctTypeDesc the acctTypeDesc to set
     */
    public void setDescription(String acctTypeDesc) {
        this.description = acctTypeDesc;
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

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AccountType) {
            return id == (((AccountType) obj).id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.code);
        return hash;
    }


    public Set<AccountGroup> getAccountGroupSet() {
        return accountGroupSet;
    }

    public void setAccountGroupSet(Set<AccountGroup> accountGroupSet) {
        this.accountGroupSet = accountGroupSet;
    }
}