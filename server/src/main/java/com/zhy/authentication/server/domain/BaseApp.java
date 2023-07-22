package com.zhy.authentication.server.domain;


import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 应用表
 */
@Entity
@Table(name = "base_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
public class BaseApp extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用密钥
     */
    @NotNull
    @Size(max = 64)
    @Column(name = "secret", length = 64, nullable = false)
    private String secret;

    /**
     * 应用名称
     */
    @NotNull
    @Size(max = 16)
    @Column(name = "name", length = 16, nullable = false)
    private String name;

    /**
     * 是否激活
     */
    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}
