package com.sbs.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;

/**
 * A Seller.
 */
@Entity
@Table(name = "seller")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "busineess_name")
    private String busineessName;

    @Column(name = "invoice_counter")
    private Long invoiceCounter;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Seller id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBusineessName() {
        return this.busineessName;
    }

    public Seller busineessName(String busineessName) {
        this.setBusineessName(busineessName);
        return this;
    }

    public void setBusineessName(String busineessName) {
        this.busineessName = busineessName;
    }

    public Long getInvoiceCounter() {
        return this.invoiceCounter;
    }

    public Seller invoiceCounter(Long invoiceCounter) {
        this.setInvoiceCounter(invoiceCounter);
        return this;
    }

    public void setInvoiceCounter(Long invoiceCounter) {
        this.invoiceCounter = invoiceCounter;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Seller phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Seller email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seller)) {
            return false;
        }
        return id != null && id.equals(((Seller) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seller{" +
            "id=" + getId() +
            ", busineessName='" + getBusineessName() + "'" +
            ", invoiceCounter=" + getInvoiceCounter() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
