package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.commons.beans.CreateAccountResponse;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.StringFormatter;
import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.service.interfaces.AccountService;
import com.tri.erp.spring.service.interfaces.BusinessSegmentService;
import com.tri.erp.spring.validator.AccountValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class BusinessSegmentServiceImpl implements BusinessSegmentService {

    @Resource
    private BusinessSegmentRepo businessSegmentRepo;

    @Override
    public BusinessSegment create(BusinessSegment bs) {
        return null;
    }

    @Override
    public BusinessSegment delete(Integer id) {
        return null;
    }

    @Override
    public List<BusinessSegment> findAll() {
        return businessSegmentRepo.findAll();
    }

    @Override
    public BusinessSegment update(BusinessSegment bs) {
        return null;
    }

    @Override
    public BusinessSegment findById(Integer id) {
        return null;
    }
}
