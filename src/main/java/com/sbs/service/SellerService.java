package com.sbs.service;

import com.sbs.domain.Seller;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Seller}.
 */
public interface SellerService {
    /**
     * Save a seller.
     *
     * @param seller the entity to save.
     * @return the persisted entity.
     */
    Seller save(Seller seller);

    /**
     * Updates a seller.
     *
     * @param seller the entity to update.
     * @return the persisted entity.
     */
    Seller update(Seller seller);

    /**
     * Partially updates a seller.
     *
     * @param seller the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Seller> partialUpdate(Seller seller);

    /**
     * Get all the sellers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Seller> findAll(Pageable pageable);

    /**
     * Get the "id" seller.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Seller> findOne(UUID id);

    /**
     * Delete the "id" seller.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
