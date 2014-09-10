package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Created by TSI Admin on 9/9/2014.
 */

@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "acct_id")
    private int id;

    @Column
    private String code;

    @Column
    private String title;

    @Column(name = "gl_acct")
    private String GLAccount;

    @Column(name = "sl_acct")
    private String SLAccount;

    @Column(name = "auxiliary_acct")
    private String auxiliaryAccount;

    @Column(name = "normal_balance")
    private int normalBalance;

    @Column(name = "parent_acct_id", insertable = false, updatable = false)
    protected int parentAcctId;

    @Column
    private int level;

    @Column(name = "isActive")
    private int active;

    @Column(name = "is_header")
    protected int isHeader;

    @Column(name = "has_sl")
    private int hasSL;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="acct_type_id")
    private AccountType accountType;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="acct_group_id")
    private AccountGroup accountGroup;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="parent_acct_id")
    private Account parentAccount;

    public Account(Account parentAccount, String code, String title, String GLAccount, String SLAccount, String auxiliaryAccount, int normalBalance, int parentAcctId, int level, int active, int isHeader, int hasSL, AccountType accountType, AccountGroup accountGroup) {
        this.parentAccount = parentAccount;
        this.code = code;
        this.title = title;
        this.GLAccount = GLAccount;
        this.SLAccount = SLAccount;
        this.auxiliaryAccount = auxiliaryAccount;
        this.normalBalance = normalBalance;
        this.parentAcctId = parentAcctId;
        this.level = level;
        this.active = active;
        this.isHeader = isHeader;
        this.hasSL = hasSL;
        this.accountType = accountType;
        this.accountGroup = accountGroup;
    }


    public Account(Account parentAccount, String code, String title, String GLAccount, String SLAccount, String auxiliaryAccount, int normalBalance, int parentAcctId, int level, int active, int isHeader, int hasSL) {
        this.parentAccount = parentAccount;
        this.code = code;
        this.title = title;
        this.GLAccount = GLAccount;
        this.SLAccount = SLAccount;
        this.auxiliaryAccount = auxiliaryAccount;
        this.normalBalance = normalBalance;
        this.parentAcctId = parentAcctId;
        this.level = level;
        this.active = active;
        this.isHeader = isHeader;
        this.hasSL = hasSL;
    }

    public Account() {}

    @Override
    public String toString() {
        return this.getTitle();
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

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the accountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the accountGroup
     */
    public AccountGroup getAccountGroup() {
        return accountGroup;
    }

    /**
     * @param accountGroup the accountGroup to set
     */
    public void setAccountGroup(AccountGroup accountGroup) {
        this.accountGroup = accountGroup;
    }

    /**
     * @return the normalBalance
     */
    public int getNormalBalance() {
        return normalBalance;
    }

    /**
     * @param normalBalance the normalBalance to set
     */
    public void setNormalBalance(int normalBalance) {
        this.normalBalance = normalBalance;
    }

    /**
     * @return the parentAcctId
     */
    public int getParentAcctId() {
        return parentAcctId;
    }

    /**
     * @param parentAcctId the parentAcctId to set
     */
    public void setParentAcctId(int parentAcctId) {
        this.parentAcctId = parentAcctId;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the active
     */
    public int isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * @return the parentAccount
     */
    public Account getParentAccount() {
        return parentAccount;
    }

    /**
     * @param parentAccount the parentAccount to set
     */
    public void setParentAccount(Account parentAccount) {
        this.parentAccount = parentAccount;
    }

    /**
     * @return the GLAccount
     */
    public String getGLAccount() {
        return GLAccount;
    }

    /**
     * @param GLAccount the GLAccount to set
     */
    public void setGLAccount(String GLAccount) {
        this.GLAccount = GLAccount;
    }

    /**
     * @return the SLAccount
     */
    public String getSLAccount() {
        return SLAccount;
    }

    /**
     * @param SLAccount the SLAccount to set
     */
    public void setSLAccount(String SLAccount) {
        this.SLAccount = SLAccount;
    }

    /**
     * @return the auxiliaryAccount
     */
    public String getAuxiliaryAccount() {
        return auxiliaryAccount;
    }

    /**
     * @param auxiliaryAccount the auxiliaryAccount to set
     */
    public void setAuxiliaryAccount(String auxiliaryAccount) {
        this.auxiliaryAccount = auxiliaryAccount;
    }

    /**
     * @return the isHeader
     */
    public int isHeader() {
        return isHeader;
    }

    /**
     * @param isHeader the isHeader to set
     */
    public void isHeader(int isHeader) {
        this.isHeader = isHeader;
    }

    /**
     * @return the hasSL
     */
    public int hasSL() {
        return hasSL;
    }

    /**
     * @param hasSL the hasSL to set
     */
    public void hasSL(int hasSL) {
        this.hasSL = hasSL;
    }

}
