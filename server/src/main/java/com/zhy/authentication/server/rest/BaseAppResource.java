package com.zhy.authentication.server.rest;

import cn.zhxu.bs.BeanSearcher;
import com.goudong.boot.web.core.ClientException;
import com.goudong.core.lang.Result;
import com.goudong.core.util.AssertUtil;
import com.zhy.authentication.server.rest.req.BaseAppCreate;
import com.zhy.authentication.server.rest.req.BaseAppUpdate;
import com.zhy.authentication.server.service.BaseAppService;
import com.zhy.authentication.server.service.dto.BaseAppDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;

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

    @Resource
    private BeanSearcher beanSearcher;
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
    public Result<Boolean> page(HttpServletRequest httpServletRequest) {
        beanSearcher.search(BaseAppPage.class, MapUtils.flat(httpServletRequest.getParameterMap()), new String[]{ "age" });
        return Result.ofSuccess(true);
    }
}
