package com.yangy.redis.service.impl;

import com.yangy.redis.dao.DividendDAO;
import com.yangy.redis.entity.Dividend;
import com.yangy.redis.service.DividendService;
import com.yangy.redis.utils.Consmer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangy
 * @email java_yangy@126.com
 * @create 2018/6/26
 * @since 1.0.0
 */
@Service
public class DividendServiceImpl implements DividendService {

    @Resource
    private DividendDAO dividendDAO;


    @Override
    public Dividend save(Dividend dividend) {
        Dividend save = dividendDAO.save(dividend);
        return save;
    }

    @Override
    public Dividend update(Dividend dividend) {
        Dividend save = dividendDAO.save(dividend);
        Consmer consmer = new Consmer();
        consmer.consume();
        return save;
    }

    @Override
    public int del(Long recordId) {
        dividendDAO.deleteById(recordId);
        return 1;
    }

    @Override
    public Dividend selectById(Long recordId) {
        return dividendDAO.findById(recordId).get();
    }
}