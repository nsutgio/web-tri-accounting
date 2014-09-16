package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface AccountRepo extends JpaRepository<Account, Integer> {
    public List<Account> findByTitle(String title);
    public List<Account> findByParentAccountIdOrderByCodeAsc(Integer accountId);
    public List<Account> findByIdNotIn(Integer... accountId);
}
