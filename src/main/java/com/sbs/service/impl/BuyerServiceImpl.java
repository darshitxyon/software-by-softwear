package com.sbs.service.impl;

import com.sbs.domain.Buyer;
import com.sbs.repository.BuyerRepository;
import com.sbs.service.BuyerService;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Buyer}.
 */
@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

    private final Logger log = LoggerFactory.getLogger(BuyerServiceImpl.class);

    private final BuyerRepository buyerRepository;

    public BuyerServiceImpl(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Override
    public Buyer save(Buyer buyer) {
        log.debug("Request to save Buyer : {}", buyer);
        return buyerRepository.save(buyer);
    }

    @Override
    public Buyer update(Buyer buyer) {
        log.debug("Request to update Buyer : {}", buyer);
        return buyerRepository.save(buyer);
    }

    @Override
    public Optional<Buyer> partialUpdate(Buyer buyer) {
        log.debug("Request to partially update Buyer : {}", buyer);

        return buyerRepository
            .findById(buyer.getId())
            .map(existingBuyer -> {
                if (buyer.getBusineessName() != null) {
                    existingBuyer.setBusineessName(buyer.getBusineessName());
                }
                if (buyer.getPhoneNumber() != null) {
                    existingBuyer.setPhoneNumber(buyer.getPhoneNumber());
                }
                if (buyer.getEmail() != null) {
                    existingBuyer.setEmail(buyer.getEmail());
                }

                return existingBuyer;
            })
            .map(buyerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Buyer> findAll(Pageable pageable) {
        log.debug("Request to get all Buyers");
        return buyerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Buyer> findOne(UUID id) {
        log.debug("Request to get Buyer : {}", id);
        return buyerRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Buyer : {}", id);
        buyerRepository.deleteById(id);
    }
}
