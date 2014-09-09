package com.tri.erp.spring.repo;

import com.tri.erp.spring.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TSI Admin on 9/9/2014.
 */
public interface AccountRepo  extends JpaRepository<Account, Integer> {

}
