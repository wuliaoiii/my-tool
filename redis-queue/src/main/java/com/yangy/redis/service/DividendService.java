package com.yangy.redis.service;

import com.yangy.redis.entity.Dividend;

public interface DividendService {

    Dividend save(Dividend dividend);

    Dividend update(Dividend dividend);

    int del(Long recordId);

    Dividend selectById(Long recordId);
}
