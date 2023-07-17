package com.zhy.authentication.server.repository;

import com.zhy.authentication.server.domain.BaseApp;
import com.zhy.authentication.server.domain.BaseMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseMenuRepository extends JpaRepository<BaseMenu, Long>, JpaSpecificationExecutor<BaseMenu> {

}
