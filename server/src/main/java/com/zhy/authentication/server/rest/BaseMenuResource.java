// package com.zhy.authentication.server.rest;
//
// import com.zhy.authentication.server.service.BaseMenuService;
// import com.zhy.authentication.server.service.dto.BaseMenuDTO;
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
//  * REST controller for managing {@link com.zhy.authentication.server.domain.BaseMenu}.
//  */
// @RestController
// @RequestMapping("/api")
// public class BaseMenuResource {
//
//     private final Logger log = LoggerFactory.getLogger(BaseMenuResource.class);
//
//     private static final String ENTITY_NAME = "baseMenu";
//
//     @Value("${jhipster.clientApp.name}")
//     private String applicationName;
//
//     private final BaseMenuService baseMenuService;
//
//     public BaseMenuResource(BaseMenuService baseMenuService) {
//         this.baseMenuService = baseMenuService;
//     }
//
//     /**
//      * {@code POST  /base-menus} : Create a new baseMenu.
//      *
//      * @param baseMenuDTO the baseMenuDTO to create.
//      * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baseMenuDTO, or with status {@code 400 (Bad Request)} if the baseMenu has already an ID.
//      * @throws URISyntaxException if the Location URI syntax is incorrect.
//      */
//     @PostMapping("/base-menus")
//     public ResponseEntity<BaseMenuDTO> createBaseMenu(@Valid @RequestBody BaseMenuDTO baseMenuDTO) throws URISyntaxException {
//         log.debug("REST request to save BaseMenu : {}", baseMenuDTO);
//         if (baseMenuDTO.getId() != null) {
//             throw new BadRequestAlertException("A new baseMenu cannot already have an ID", ENTITY_NAME, "idexists");
//         }
//         BaseMenuDTO result = baseMenuService.save(baseMenuDTO);
//         return ResponseEntity.created(new URI("/api/base-menus/" + result.getId()))
//             .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//             .body(result);
//     }
//
//     /**
//      * {@code PUT  /base-menus} : Updates an existing baseMenu.
//      *
//      * @param baseMenuDTO the baseMenuDTO to update.
//      * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseMenuDTO,
//      * or with status {@code 400 (Bad Request)} if the baseMenuDTO is not valid,
//      * or with status {@code 500 (Internal Server Error)} if the baseMenuDTO couldn't be updated.
//      * @throws URISyntaxException if the Location URI syntax is incorrect.
//      */
//     @PutMapping("/base-menus")
//     public ResponseEntity<BaseMenuDTO> updateBaseMenu(@Valid @RequestBody BaseMenuDTO baseMenuDTO) throws URISyntaxException {
//         log.debug("REST request to update BaseMenu : {}", baseMenuDTO);
//         if (baseMenuDTO.getId() == null) {
//             throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//         }
//         BaseMenuDTO result = baseMenuService.save(baseMenuDTO);
//         return ResponseEntity.ok()
//             .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseMenuDTO.getId().toString()))
//             .body(result);
//     }
//
//     /**
//      * {@code GET  /base-menus} : get all the baseMenus.
//      *
//      * @param pageable the pagination information.
//      * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baseMenus in body.
//      */
//     @GetMapping("/base-menus")
//     public ResponseEntity<List<BaseMenuDTO>> getAllBaseMenus(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
//         log.debug("REST request to get a page of BaseMenus");
//         Page<BaseMenuDTO> page = baseMenuService.findAll(pageable);
//         HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
//         return ResponseEntity.ok().headers(headers).body(page.getContent());
//     }
//
//     /**
//      * {@code GET  /base-menus/:id} : get the "id" baseMenu.
//      *
//      * @param id the id of the baseMenuDTO to retrieve.
//      * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseMenuDTO, or with status {@code 404 (Not Found)}.
//      */
//     @GetMapping("/base-menus/{id}")
//     public ResponseEntity<BaseMenuDTO> getBaseMenu(@PathVariable Long id) {
//         log.debug("REST request to get BaseMenu : {}", id);
//         Optional<BaseMenuDTO> baseMenuDTO = baseMenuService.findOne(id);
//         return ResponseUtil.wrapOrNotFound(baseMenuDTO);
//     }
//
//     /**
//      * {@code DELETE  /base-menus/:id} : delete the "id" baseMenu.
//      *
//      * @param id the id of the baseMenuDTO to delete.
//      * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//      */
//     @DeleteMapping("/base-menus/{id}")
//     public ResponseEntity<Void> deleteBaseMenu(@PathVariable Long id) {
//         log.debug("REST request to delete BaseMenu : {}", id);
//         baseMenuService.delete(id);
//         return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
//     }
// }
