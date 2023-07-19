package com.zhy.authentication.server.repository;

import com.zhy.authentication.server.domain.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long>, JpaSpecificationExecutor<BaseUser> {

    @Query(value = "from BaseUser where appId=?1 and username=?2")
    BaseUser findByLogin(Long appId, String login);
}
