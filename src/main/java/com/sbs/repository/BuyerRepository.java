package com.sbs.repository;

import com.sbs.domain.Buyer;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Buyer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, UUID> {}
