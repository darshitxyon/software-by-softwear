package com.sbs.service;

import com.sbs.domain.Transport;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Transport}.
 */
public interface TransportService {
    /**
     * Save a transport.
     *
     * @param transport the entity to save.
     * @return the persisted entity.
     */
    Transport save(Transport transport);

    /**
     * Updates a transport.
     *
     * @param transport the entity to update.
     * @return the persisted entity.
     */
    Transport update(Transport transport);

    /**
     * Partially updates a transport.
     *
     * @param transport the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Transport> partialUpdate(Transport transport);

    /**
     * Get all the transports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Transport> findAll(Pageable pageable);

    /**
     * Get the "id" transport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Transport> findOne(Long id);

    /**
     * Delete the "id" transport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
