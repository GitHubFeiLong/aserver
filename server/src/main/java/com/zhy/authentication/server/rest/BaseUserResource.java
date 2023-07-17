package com.zhy.authentication.server.rest;

import com.zhy.authentication.server.service.BaseUserService;
import com.zhy.authentication.server.service.dto.BaseUserDTO;
import com.zhy.authentication.server.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.zhy.authentication.server.domain.BaseUser}.
 */
@RestController
@RequestMapping("/api")
public class BaseUserResource {

    private final Logger log = LoggerFactory.getLogger(BaseUserResource.class);

    private static final String ENTITY_NAME = "baseUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseUserService baseUserService;

    public BaseUserResource(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    /**
     * {@code POST  /base-users} : Create a new baseUser.
     *
     * @param baseUserDTO the baseUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baseUserDTO, or with status {@code 400 (Bad Request)} if the baseUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/base-users")
    public ResponseEntity<BaseUserDTO> createBaseUser(@Valid @RequestBody BaseUserDTO baseUserDTO) throws URISyntaxException {
        log.debug("REST request to save BaseUser : {}", baseUserDTO);
        if (baseUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new baseUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseUserDTO result = baseUserService.save(baseUserDTO);
        return ResponseEntity.created(new URI("/api/base-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /base-users} : Updates an existing baseUser.
     *
     * @param baseUserDTO the baseUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseUserDTO,
     * or with status {@code 400 (Bad Request)} if the baseUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/base-users")
    public ResponseEntity<BaseUserDTO> updateBaseUser(@Valid @RequestBody BaseUserDTO baseUserDTO) throws URISyntaxException {
        log.debug("REST request to update BaseUser : {}", baseUserDTO);
        if (baseUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaseUserDTO result = baseUserService.save(baseUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /base-users} : get all the baseUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baseUsers in body.
     */
    @GetMapping("/base-users")
    public ResponseEntity<List<BaseUserDTO>> getAllBaseUsers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of BaseUsers");
        Page<BaseUserDTO> page = baseUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /base-users/:id} : get the "id" baseUser.
     *
     * @param id the id of the baseUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/base-users/{id}")
    public ResponseEntity<BaseUserDTO> getBaseUser(@PathVariable Long id) {
        log.debug("REST request to get BaseUser : {}", id);
        Optional<BaseUserDTO> baseUserDTO = baseUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(baseUserDTO);
    }

    /**
     * {@code DELETE  /base-users/:id} : delete the "id" baseUser.
     *
     * @param id the id of the baseUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/base-users/{id}")
    public ResponseEntity<Void> deleteBaseUser(@PathVariable Long id) {
        log.debug("REST request to delete BaseUser : {}", id);
        baseUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}