// package com.zhy.authentication.server.rest;
//
// import com.zhy.authentication.server.service.BaseRoleService;
// import com.zhy.authentication.server.service.dto.BaseRoleDTO;
// import com.zhy.authentication.server.web.rest.errors.BadRequestAlertException;
// import io.github.jhipster.web.util.HeaderUtil;
// import io.github.jhipster.web.util.PaginationUtil;
// import io.github.jhipster.web.util.ResponseUtil;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.ResponseEntity;
// import org.springframework.util.MultiValueMap;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.util.UriComponentsBuilder;
//
// import javax.validation.Valid;
// import java.net.URI;
// import java.net.URISyntaxException;
// import java.util.List;
// import java.util.Optional;
//
// /**
//  * REST controller for managing {@link com.zhy.authentication.server.domain.BaseRole}.
//  */
// @RestController
// @RequestMapping("/api")
// public class BaseRoleResource {
//
//     private final Logger log = LoggerFactory.getLogger(BaseRoleResource.class);
//
//     private static final String ENTITY_NAME = "baseRole";
//
//     @Value("${jhipster.clientApp.name}")
//     private String applicationName;
//
//     private final BaseRoleService baseRoleService;
//
//     public BaseRoleResource(BaseRoleService baseRoleService) {
//         this.baseRoleService = baseRoleService;
//     }
//
//     /**
//      * {@code POST  /base-roles} : Create a new baseRole.
//      *
//      * @param baseRoleDTO the baseRoleDTO to create.
//      * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baseRoleDTO, or with status {@code 400 (Bad Request)} if the baseRole has already an ID.
//      * @throws URISyntaxException if the Location URI syntax is incorrect.
//      */
//     @PostMapping("/base-roles")
//     public ResponseEntity<BaseRoleDTO> createBaseRole(@Valid @RequestBody BaseRoleDTO baseRoleDTO) throws URISyntaxException {
//         log.debug("REST request to save BaseRole : {}", baseRoleDTO);
//         if (baseRoleDTO.getId() != null) {
//             throw new BadRequestAlertException("A new baseRole cannot already have an ID", ENTITY_NAME, "idexists");
//         }
//         BaseRoleDTO result = baseRoleService.save(baseRoleDTO);
//         return ResponseEntity.created(new URI("/api/base-roles/" + result.getId()))
//             .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//             .body(result);
//     }
//
//     /**
//      * {@code PUT  /base-roles} : Updates an existing baseRole.
//      *
//      * @param baseRoleDTO the baseRoleDTO to update.
//      * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseRoleDTO,
//      * or with status {@code 400 (Bad Request)} if the baseRoleDTO is not valid,
//      * or with status {@code 500 (Internal Server Error)} if the baseRoleDTO couldn't be updated.
//      * @throws URISyntaxException if the Location URI syntax is incorrect.
//      */
//     @PutMapping("/base-roles")
//     public ResponseEntity<BaseRoleDTO> updateBaseRole(@Valid @RequestBody BaseRoleDTO baseRoleDTO) throws URISyntaxException {
//         log.debug("REST request to update BaseRole : {}", baseRoleDTO);
//         if (baseRoleDTO.getId() == null) {
//             throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//         }
//         BaseRoleDTO result = baseRoleService.save(baseRoleDTO);
//         return ResponseEntity.ok()
//             .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseRoleDTO.getId().toString()))
//             .body(result);
//     }
//
//     /**
//      * {@code GET  /base-roles} : get all the baseRoles.
//      *
//      * @param pageable the pagination information.
//      * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baseRoles in body.
//      */
//     @GetMapping("/base-roles")
//     public ResponseEntity<List<BaseRoleDTO>> getAllBaseRoles(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
//         log.debug("REST request to get a page of BaseRoles");
//         Page<BaseRoleDTO> page = baseRoleService.findAll(pageable);
//         HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
//         return ResponseEntity.ok().headers(headers).body(page.getContent());
//     }
//
//     /**
//      * {@code GET  /base-roles/:id} : get the "id" baseRole.
//      *
//      * @param id the id of the baseRoleDTO to retrieve.
//      * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseRoleDTO, or with status {@code 404 (Not Found)}.
//      */
//     @GetMapping("/base-roles/{id}")
//     public ResponseEntity<BaseRoleDTO> getBaseRole(@PathVariable Long id) {
//         log.debug("REST request to get BaseRole : {}", id);
//         Optional<BaseRoleDTO> baseRoleDTO = baseRoleService.findOne(id);
//         return ResponseUtil.wrapOrNotFound(baseRoleDTO);
//     }
//
//     /**
//      * {@code DELETE  /base-roles/:id} : delete the "id" baseRole.
//      *
//      * @param id the id of the baseRoleDTO to delete.
//      * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//      */
//     @DeleteMapping("/base-roles/{id}")
//     public ResponseEntity<Void> deleteBaseRole(@PathVariable Long id) {
//         log.debug("REST request to delete BaseRole : {}", id);
//         baseRoleService.delete(id);
//         return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
//     }
// }
