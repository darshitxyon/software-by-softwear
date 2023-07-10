package com.sbs.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sbs.IntegrationTest;
import com.sbs.domain.Buyer;
import com.sbs.repository.BuyerRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BuyerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyerResourceIT {

    private static final String DEFAULT_BUSINEESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINEESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/buyers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyerMockMvc;

    private Buyer buyer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buyer createEntity(EntityManager em) {
        Buyer buyer = new Buyer().busineessName(DEFAULT_BUSINEESS_NAME).phoneNumber(DEFAULT_PHONE_NUMBER).email(DEFAULT_EMAIL);
        return buyer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buyer createUpdatedEntity(EntityManager em) {
        Buyer buyer = new Buyer().busineessName(UPDATED_BUSINEESS_NAME).phoneNumber(UPDATED_PHONE_NUMBER).email(UPDATED_EMAIL);
        return buyer;
    }

    @BeforeEach
    public void initTest() {
        buyer = createEntity(em);
    }

    @Test
    @Transactional
    void createBuyer() throws Exception {
        int databaseSizeBeforeCreate = buyerRepository.findAll().size();
        // Create the Buyer
        restBuyerMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isCreated());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeCreate + 1);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getBusineessName()).isEqualTo(DEFAULT_BUSINEESS_NAME);
        assertThat(testBuyer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testBuyer.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createBuyerWithExistingId() throws Exception {
        // Create the Buyer with an existing ID
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeCreate = buyerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyerMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuyers() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        // Get all the buyerList
        restBuyerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyer.getId().toString())))
            .andExpect(jsonPath("$.[*].busineessName").value(hasItem(DEFAULT_BUSINEESS_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        // Get the buyer
        restBuyerMockMvc
            .perform(get(ENTITY_API_URL_ID, buyer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyer.getId().toString()))
            .andExpect(jsonPath("$.busineessName").value(DEFAULT_BUSINEESS_NAME))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingBuyer() throws Exception {
        // Get the buyer
        restBuyerMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Update the buyer
        Buyer updatedBuyer = buyerRepository.findById(buyer.getId()).get();
        // Disconnect from session so that the updates on updatedBuyer are not directly saved in db
        em.detach(updatedBuyer);
        updatedBuyer.busineessName(UPDATED_BUSINEESS_NAME).phoneNumber(UPDATED_PHONE_NUMBER).email(UPDATED_EMAIL);

        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBuyer.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBuyer))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getBusineessName()).isEqualTo(UPDATED_BUSINEESS_NAME);
        assertThat(testBuyer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testBuyer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyer.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyerWithPatch() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Update the buyer using partial update
        Buyer partialUpdatedBuyer = new Buyer();
        partialUpdatedBuyer.setId(buyer.getId());

        partialUpdatedBuyer.busineessName(UPDATED_BUSINEESS_NAME).phoneNumber(UPDATED_PHONE_NUMBER);

        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyer))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getBusineessName()).isEqualTo(UPDATED_BUSINEESS_NAME);
        assertThat(testBuyer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testBuyer.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateBuyerWithPatch() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Update the buyer using partial update
        Buyer partialUpdatedBuyer = new Buyer();
        partialUpdatedBuyer.setId(buyer.getId());

        partialUpdatedBuyer.busineessName(UPDATED_BUSINEESS_NAME).phoneNumber(UPDATED_PHONE_NUMBER).email(UPDATED_EMAIL);

        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyer))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getBusineessName()).isEqualTo(UPDATED_BUSINEESS_NAME);
        assertThat(testBuyer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testBuyer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeDelete = buyerRepository.findAll().size();

        // Delete the buyer
        restBuyerMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyer.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
