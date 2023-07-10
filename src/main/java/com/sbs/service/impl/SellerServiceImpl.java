package com.sbs.service.impl;

import com.sbs.domain.Seller;
import com.sbs.repository.SellerRepository;
import com.sbs.service.SellerService;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Seller}.
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    private final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    private final SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller save(Seller seller) {
        log.debug("Request to save Seller : {}", seller);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller update(Seller seller) {
        log.debug("Request to update Seller : {}", seller);
        return sellerRepository.save(seller);
    }

    @Override
    public Optional<Seller> partialUpdate(Seller seller) {
        log.debug("Request to partially update Seller : {}", seller);

        return sellerRepository
            .findById(seller.getId())
            .map(existingSeller -> {
                if (seller.getBusineessName() != null) {
                    existingSeller.setBusineessName(seller.getBusineessName());
                }
                if (seller.getInvoiceCounter() != null) {
                    existingSeller.setInvoiceCounter(seller.getInvoiceCounter());
                }
                if (seller.getPhoneNumber() != null) {
                    existingSeller.setPhoneNumber(seller.getPhoneNumber());
                }
                if (seller.getEmail() != null) {
                    existingSeller.setEmail(seller.getEmail());
                }

                return existingSeller;
            })
            .map(sellerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Seller> findAll(Pageable pageable) {
        log.debug("Request to get all Sellers");
        return sellerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seller> findOne(UUID id) {
        log.debug("Request to get Seller : {}", id);
        return sellerRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Seller : {}", id);
        sellerRepository.deleteById(id);
    }
}
