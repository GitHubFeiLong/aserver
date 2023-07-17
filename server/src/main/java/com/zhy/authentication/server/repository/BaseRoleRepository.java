package com.zhy.authentication.server.repository;

import com.zhy.authentication.server.domain.BaseRole;
import com.zhy.authentication.server.domain.BaseRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseRoleRepository extends JpaRepository<BaseRole, Long>, JpaSpecificationExecutor<BaseRole> {

}
