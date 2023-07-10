<template>
  <div>
    <h2 id="page-heading" data-cy="SellerHeading">
      <span id="seller-heading">Sellers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'SellerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-seller"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Seller </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && sellers && sellers.length === 0">
      <span>No sellers found</span>
    </div>
    <div class="table-responsive" v-if="sellers && sellers.length > 0">
      <table class="table table-striped" aria-describedby="sellers">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('busineessName')">
              <span>Busineess Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'busineessName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('invoiceCounter')">
              <span>Invoice Counter</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'invoiceCounter'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phoneNumber')">
              <span>Phone Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span>Email</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="seller in sellers" :key="seller.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SellerView', params: { sellerId: seller.id } }">{{ seller.id }}</router-link>
            </td>
            <td>{{ seller.busineessName }}</td>
            <td>{{ seller.invoiceCounter }}</td>
            <td>{{ seller.phoneNumber }}</td>
            <td>{{ seller.email }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SellerView', params: { sellerId: seller.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SellerEdit', params: { sellerId: seller.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(seller)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="sbsApp.seller.delete.question" data-cy="sellerDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-seller-heading">Are you sure you want to delete this Seller?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-seller"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeSeller()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="sellers && sellers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./seller.component.ts"></script>
