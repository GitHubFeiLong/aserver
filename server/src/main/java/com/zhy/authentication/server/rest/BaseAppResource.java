package com.zhy.authentication.server.rest;

import com.goudong.core.lang.PageResult;
import com.goudong.core.lang.Result;
import com.zhy.authentication.server.rest.req.BaseAppCreate;
import com.zhy.authentication.server.rest.req.BaseAppUpdate;
import com.zhy.authentication.server.rest.req.search.BaseAppDropDown;
import com.zhy.authentication.server.rest.req.search.BaseAppPage;
import com.zhy.authentication.server.rest.req.search.BaseRoleDropDown;
import com.zhy.authentication.server.service.BaseAppService;
import com.zhy.authentication.server.service.dto.BaseAppDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 新增
 * 编辑
 * 删除
 * 分页
 * 下拉
 * </pre>
 */
@RestController
@RequestMapping("/app")
@Api(tags = "应用")
public class BaseAppResource {

    private final Logger log = LoggerFactory.getLogger(BaseAppResource.class);

    @Resource
    private BaseAppService baseAppService;

    /**
     * 新增应用
     * @param req
     * @return
     */
    @PostMapping("/base-app")
    @ApiOperation("新增")
    public Result<BaseAppDTO> create(@Valid @RequestBody BaseAppCreate req) {
        BaseAppDTO result = baseAppService.save(req);
        return Result.ofSuccess(result);
    }

    /**
     * 修改应用
     * @param req
     * @return
     */
    @PutMapping("/base-app")
    @ApiOperation("修改")
    public Result<BaseAppDTO> update(@Valid @RequestBody BaseAppUpdate req) {
        BaseAppDTO result = baseAppService.update(req);
        return Result.ofSuccess(result);
    }

    /**
     * 删除应用
     * @param id
     * @return
     */
    @DeleteMapping("/base-app/{id}")
    @ApiOperation("删除")
    public Result<Boolean> delete(@PathVariable Long id) {
        baseAppService.delete(id);
        return Result.ofSuccess(true);
    }

    /**
     * 应用分页
     * @param req
     * @return
     */
    @GetMapping("/base-apps")
    @ApiOperation("分页")
    public Result<PageResult<BaseAppPage>> page(@Validated BaseAppPage req) {
        return Result.ofSuccess(baseAppService.page(req));
    }

    /**
     * 应用下拉
     * @param req
     * @return
     */
    @GetMapping("/base-app/drop-down")
    @ApiOperation("下拉")
    public Result<List<BaseAppDropDown>> dropDown(@Validated BaseAppDropDown req) {
        return Result.ofSuccess(baseAppService.dropDown(req));
    }

    /**
     * 应用下拉
     * @param req
     * @return
     */
    @GetMapping("/base-app/all-drop-down")
    @ApiOperation("下拉")
    public Result<List<BaseAppDropDown>> allDropDown(@Validated BaseAppDropDown req) {
        return Result.ofSuccess(baseAppService.allDropDown(req));
    }
}
