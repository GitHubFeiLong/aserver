package com.zhy.authentication.server.rest;

import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.util.MapUtils;
import com.goudong.core.lang.PageResult;
import com.goudong.core.lang.Result;
import com.zhy.authentication.server.rest.req.BaseAppCreate;
import com.zhy.authentication.server.rest.req.BaseAppUpdate;
import com.zhy.authentication.server.rest.req.search.BaseAppPage;
import com.zhy.authentication.server.service.BaseAppService;
import com.zhy.authentication.server.service.dto.BaseAppDTO;
import com.zhy.authentication.server.util.PageResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * REST controller for managing {@link com.zhy.authentication.server.domain.BaseApp}.
 */
@RestController
@RequestMapping("/app")
@Api(tags = "应用")
public class BaseAppResource {

    private final Logger log = LoggerFactory.getLogger(BaseAppResource.class);

    @Resource
    private BaseAppService baseAppService;

    @PostMapping("/base-app")
    @ApiOperation("新增")
    public Result<BaseAppDTO> create(@Valid @RequestBody BaseAppCreate req) {
        BaseAppDTO result = baseAppService.save(req);
        return Result.ofSuccess(result);
    }
    @PutMapping("/base-app")
    @ApiOperation("修改")
    public Result<BaseAppDTO> update(@Valid @RequestBody BaseAppUpdate req) {
        BaseAppDTO result = baseAppService.update(req);
        return Result.ofSuccess(result);
    }

    @DeleteMapping("/base-app/{id}")
    @ApiOperation("删除")
    public Result<Boolean> delete(@PathVariable Long id) {
        baseAppService.delete(id);
        return Result.ofSuccess(true);
    }

    @GetMapping("/base-apps")
    @ApiOperation("分页")
    public Result<PageResult<BaseAppPage>> page(@Validated BaseAppPage req) {
        return Result.ofSuccess(baseAppService.page(req));
    }
}
