package com.zhy.authentication.server.rest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final BaseAppService baseAppService;

    public BaseAppResource(BaseAppService baseAppService) {
        this.baseAppService = baseAppService;
    }

    @PostMapping("/base-apps")
    @ApiOperation("新增")
    public Result<BaseAppDTO> createBaseApp(@Valid @RequestBody BaseAppCreate req) {
        BaseAppDTO result = baseAppService.save(req);
        return Result.ofSuccess(result);
    }

    @PutMapping("/base-apps")
    public ResponseEntity<BaseAppDTO> updateBaseApp(@Valid @RequestBody BaseAppUpdate req) {
        BaseAppDTO result = baseAppService.update(req);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseAppDTO.getId().toString()))
            .body(result);
    }
    //
    // /**
    //  * {@code GET  /base-apps} : get all the baseApps.
    //  *
    //  * @param pageable the pagination information.
    //  * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baseApps in body.
    //  */
    // @GetMapping("/base-apps")
    // public ResponseEntity<List<BaseAppDTO>> getAllBaseApps(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
    //     log.debug("REST request to get a page of BaseApps");
    //     Page<BaseAppDTO> page = baseAppService.findAll(pageable);
    //     HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
    //     return ResponseEntity.ok().headers(headers).body(page.getContent());
    // }
    //
    // /**
    //  * {@code GET  /base-apps/:id} : get the "id" baseApp.
    //  *
    //  * @param id the id of the baseAppDTO to retrieve.
    //  * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseAppDTO, or with status {@code 404 (Not Found)}.
    //  */
    // @GetMapping("/base-apps/{id}")
    // public ResponseEntity<BaseAppDTO> getBaseApp(@PathVariable Long id) {
    //     log.debug("REST request to get BaseApp : {}", id);
    //     Optional<BaseAppDTO> baseAppDTO = baseAppService.findOne(id);
    //     return ResponseUtil.wrapOrNotFound(baseAppDTO);
    // }
    //
    // /**
    //  * {@code DELETE  /base-apps/:id} : delete the "id" baseApp.
    //  *
    //  * @param id the id of the baseAppDTO to delete.
    //  * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    //  */
    // @DeleteMapping("/base-apps/{id}")
    // public ResponseEntity<Void> deleteBaseApp(@PathVariable Long id) {
    //     log.debug("REST request to delete BaseApp : {}", id);
    //     baseAppService.delete(id);
    //     return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    // }
}
