package com.zhy.authentication.server.domain;


import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 基础用户
 */
@Data
@Entity
@Table(name = "base_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseUser extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用id
     */
    @NotNull
    @Column(name = "app_id", nullable = false)
    private Long appId;

    /**
     * 用户名
     */
    @NotNull
    @Size(max = 16)
    @Column(name = "username", length = 16, nullable = false)
    private String username;

    /**
     * 密码
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "password", length = 128, nullable = false)
    private String password;

    /**
     * 激活状态：true 激活；false 未激活
     */
    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    /**
     * 锁定状态：true 锁定；false 未锁定
     */
    @NotNull
    @Column(name = "locked", nullable = false)
    private Boolean locked;

    /**
     * 有效截止时间
     */
    @Column(name = "valid_time", nullable = false)
    private Date validTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 最后修改人
     */
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<BaseUserRole> roles = new ArrayList<>();
}
