package com.mbd.auth.user.adapter.out.repository;

import com.mbd.auth.user.domain.User;
import com.mbd.auth.user.domain.UserDomainRepository;
import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.usecase.dto.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends UserDomainRepository, Repository<User, UserId> {

    @Query("""
            SELECT new com.mbd.auth.user.usecase.dto.UserDto(u.id, u.name, u.email)
            FROM User u WHERE u.id = :id
            """)
    UserDto findById(@Param("id") UserId id);
}
