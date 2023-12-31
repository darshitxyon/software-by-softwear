package com.sbs.web.rest;

import com.sbs.domain.Transport;
import com.sbs.repository.TransportRepository;
import com.sbs.service.TransportService;
import com.sbs.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sbs.domain.Transport}.
 */
@RestController
@RequestMapping("/api")
public class TransportResource {

    private final Logger log = LoggerFactory.getLogger(TransportResource.class);

    private static final String ENTITY_NAME = "transport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportService transportService;

    private final TransportRepository transportRepository;

    public TransportResource(TransportService transportService, TransportRepository transportRepository) {
        this.transportService = transportService;
        this.transportRepository = transportRepository;
    }

    /**
     * {@code POST  /transports} : Create a new transport.
     *
     * @param transport the transport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transport, or with status {@code 400 (Bad Request)} if the transport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transports")
    public ResponseEntity<Transport> createTransport(@RequestBody Transport transport) throws URISyntaxException {
        log.debug("REST request to save Transport : {}", transport);
        if (transport.getId() != null) {
            throw new BadRequestAlertException("A new transport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transport result = transportService.save(transport);
        return ResponseEntity
            .created(new URI("/api/transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transports/:id} : Updates an existing transport.
     *
     * @param id the id of the transport to save.
     * @param transport the transport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transport,
     * or with status {@code 400 (Bad Request)} if the transport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transports/{id}")
    public ResponseEntity<Transport> updateTransport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transport transport
    ) throws URISyntaxException {
        log.debug("REST request to update Transport : {}, {}", id, transport);
        if (transport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Transport result = transportService.update(transport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transports/:id} : Partial updates given fields of an existing transport, field will ignore if it is null
     *
     * @param id the id of the transport to save.
     * @param transport the transport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transport,
     * or with status {@code 400 (Bad Request)} if the transport is not valid,
     * or with status {@code 404 (Not Found)} if the transport is not found,
     * or with status {@code 500 (Internal Server Error)} if the transport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Transport> partialUpdateTransport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transport transport
    ) throws URISyntaxException {
        log.debug("REST request to partial update Transport partially : {}, {}", id, transport);
        if (transport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Transport> result = transportService.partialUpdate(transport);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transport.getId().toString())
        );
    }

    /**
     * {@code GET  /transports} : get all the transports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transports in body.
     */
    @GetMapping("/transports")
    public ResponseEntity<List<Transport>> getAllTransports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Transports");
        Page<Transport> page = transportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transports/:id} : get the "id" transport.
     *
     * @param id the id of the transport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transports/{id}")
    public ResponseEntity<Transport> getTransport(@PathVariable Long id) {
        log.debug("REST request to get Transport : {}", id);
        Optional<Transport> transport = transportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transport);
    }

    /**
     * {@code DELETE  /transports/:id} : delete the "id" transport.
     *
     * @param id the id of the transport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transports/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        log.debug("REST request to delete Transport : {}", id);
        transportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
