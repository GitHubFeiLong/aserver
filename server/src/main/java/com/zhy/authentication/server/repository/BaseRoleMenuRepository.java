package com.zhy.authentication.server.repository;

import com.zhy.authentication.server.domain.BaseMenu;
import com.zhy.authentication.server.domain.BaseRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseRoleMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseRoleMenuRepository extends JpaRepository<BaseRoleMenu, Long>, JpaSpecificationExecutor<BaseRoleMenu> {

}
