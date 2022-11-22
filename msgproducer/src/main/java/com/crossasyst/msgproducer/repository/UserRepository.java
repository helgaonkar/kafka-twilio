package com.crossasyst.msgproducer.repository;

import com.crossasyst.msgproducer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity ,Long>{

    @Query(value =  "select email from userdetail where to_char(CURRENT_DATE, 'MM-dd')=to_char(day_month, 'MM-dd')",nativeQuery = true)
    List<String> findAllByDayMonth();
}
