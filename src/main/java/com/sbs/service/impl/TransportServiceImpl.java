package com.sbs.service.impl;

import com.sbs.domain.Transport;
import com.sbs.repository.TransportRepository;
import com.sbs.service.TransportService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transport}.
 */
@Service
@Transactional
public class TransportServiceImpl implements TransportService {

    private final Logger log = LoggerFactory.getLogger(TransportServiceImpl.class);

    private final TransportRepository transportRepository;

    public TransportServiceImpl(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @Override
    public Transport save(Transport transport) {
        log.debug("Request to save Transport : {}", transport);
        return transportRepository.save(transport);
    }

    @Override
    public Transport update(Transport transport) {
        log.debug("Request to update Transport : {}", transport);
        return transportRepository.save(transport);
    }

    @Override
    public Optional<Transport> partialUpdate(Transport transport) {
        log.debug("Request to partially update Transport : {}", transport);

        return transportRepository
            .findById(transport.getId())
            .map(existingTransport -> {
                if (transport.getName() != null) {
                    existingTransport.setName(transport.getName());
                }

                return existingTransport;
            })
            .map(transportRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transport> findAll(Pageable pageable) {
        log.debug("Request to get all Transports");
        return transportRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transport> findOne(Long id) {
        log.debug("Request to get Transport : {}", id);
        return transportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transport : {}", id);
        transportRepository.deleteById(id);
    }
}
