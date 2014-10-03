package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface AccountRepo extends JpaRepository<Account, Integer> {
    public List<Account> findByTitle(String title);
    public List<Account> findAllByOrderByTitleAsc();
    public List<Account> findByParentAccountIdOrderByCodeAsc(Integer accountId);
    public List<Account> findByIdNotIn(Integer... accountId);
    public List<Account> findByIdNotInOrderByTitleAsc(Integer... accountId);

    @Transactional
    @Query(value = "SELECT accounts.* FROM accounts " +
            "JOIN segment_accounts ON accounts.acct_id = segment_accounts.acct_id " +
            "WHERE segment_accounts.bus_seg_id IN (:segmentIds)", nativeQuery = true)
    public List<Account> findAccountBySegment(@Param("segmentIds") Integer[] segmentIds);
}
