package com.sbs.service;

import com.sbs.domain.Buyer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Buyer}.
 */
public interface BuyerService {
    /**
     * Save a buyer.
     *
     * @param buyer the entity to save.
     * @return the persisted entity.
     */
    Buyer save(Buyer buyer);

    /**
     * Updates a buyer.
     *
     * @param buyer the entity to update.
     * @return the persisted entity.
     */
    Buyer update(Buyer buyer);

    /**
     * Partially updates a buyer.
     *
     * @param buyer the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Buyer> partialUpdate(Buyer buyer);

    /**
     * Get all the buyers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Buyer> findAll(Pageable pageable);

    /**
     * Get the "id" buyer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Buyer> findOne(UUID id);

    /**
     * Delete the "id" buyer.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
