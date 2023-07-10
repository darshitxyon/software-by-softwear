package com.sbs.web.rest;

import com.sbs.domain.Buyer;
import com.sbs.repository.BuyerRepository;
import com.sbs.service.BuyerService;
import com.sbs.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
 * REST controller for managing {@link com.sbs.domain.Buyer}.
 */
@RestController
@RequestMapping("/api")
public class BuyerResource {

    private final Logger log = LoggerFactory.getLogger(BuyerResource.class);

    private static final String ENTITY_NAME = "buyer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyerService buyerService;

    private final BuyerRepository buyerRepository;

    public BuyerResource(BuyerService buyerService, BuyerRepository buyerRepository) {
        this.buyerService = buyerService;
        this.buyerRepository = buyerRepository;
    }

    /**
     * {@code POST  /buyers} : Create a new buyer.
     *
     * @param buyer the buyer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyer, or with status {@code 400 (Bad Request)} if the buyer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buyers")
    public ResponseEntity<Buyer> createBuyer(@RequestBody Buyer buyer) throws URISyntaxException {
        log.debug("REST request to save Buyer : {}", buyer);
        if (buyer.getId() != null) {
            throw new BadRequestAlertException("A new buyer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Buyer result = buyerService.save(buyer);
        return ResponseEntity
            .created(new URI("/api/buyers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buyers/:id} : Updates an existing buyer.
     *
     * @param id the id of the buyer to save.
     * @param buyer the buyer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyer,
     * or with status {@code 400 (Bad Request)} if the buyer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buyers/{id}")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Buyer buyer)
        throws URISyntaxException {
        log.debug("REST request to update Buyer : {}, {}", id, buyer);
        if (buyer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Buyer result = buyerService.update(buyer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, buyer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buyers/:id} : Partial updates given fields of an existing buyer, field will ignore if it is null
     *
     * @param id the id of the buyer to save.
     * @param buyer the buyer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyer,
     * or with status {@code 400 (Bad Request)} if the buyer is not valid,
     * or with status {@code 404 (Not Found)} if the buyer is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buyers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Buyer> partialUpdateBuyer(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Buyer buyer)
        throws URISyntaxException {
        log.debug("REST request to partial update Buyer partially : {}, {}", id, buyer);
        if (buyer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Buyer> result = buyerService.partialUpdate(buyer);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, buyer.getId().toString())
        );
    }

    /**
     * {@code GET  /buyers} : get all the buyers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyers in body.
     */
    @GetMapping("/buyers")
    public ResponseEntity<List<Buyer>> getAllBuyers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Buyers");
        Page<Buyer> page = buyerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buyers/:id} : get the "id" buyer.
     *
     * @param id the id of the buyer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buyers/{id}")
    public ResponseEntity<Buyer> getBuyer(@PathVariable UUID id) {
        log.debug("REST request to get Buyer : {}", id);
        Optional<Buyer> buyer = buyerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyer);
    }

    /**
     * {@code DELETE  /buyers/:id} : delete the "id" buyer.
     *
     * @param id the id of the buyer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buyers/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable UUID id) {
        log.debug("REST request to delete Buyer : {}", id);
        buyerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
