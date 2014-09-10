package com.tri.erp.spring.dao;

import com.tri.erp.spring.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * Created by arjayadong on 9/10/14.
 */

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public int create(Account account) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(account);
        tx.commit();
        Serializable id = session.getIdentifier(account);
        session.close();
        return (Integer) id;
    }
}
