package com.zhy.authentication.server.repository;

import com.zhy.authentication.server.domain.BaseUser;
import com.zhy.authentication.server.domain.BaseUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the BaseUserRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseUserRoleRepository extends JpaRepository<BaseUserRole, Long>, JpaSpecificationExecutor<BaseUserRole> {


    List<BaseUserRole> findAllByUserId(Long userId);
}
