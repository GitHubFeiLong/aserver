package com.zhy.authentication.server.repository;

import com.zhy.authentication.server.domain.BaseRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseRoleRepository extends JpaRepository<BaseRole, Long> {

}
