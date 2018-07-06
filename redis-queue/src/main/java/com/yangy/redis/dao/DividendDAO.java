package com.yangy.redis.dao;

import com.yangy.redis.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendDAO extends JpaRepository<Dividend,Long> {

}
