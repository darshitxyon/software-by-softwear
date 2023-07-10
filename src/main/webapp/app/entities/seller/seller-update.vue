<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="sbsApp.seller.home.createOrEditLabel" data-cy="SellerCreateUpdateHeading">Create or edit a Seller</h2>
        <div>
          <div class="form-group" v-if="seller.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="seller.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="seller-busineessName">Busineess Name</label>
            <input
              type="text"
              class="form-control"
              name="busineessName"
              id="seller-busineessName"
              data-cy="busineessName"
              :class="{ valid: !$v.seller.busineessName.$invalid, invalid: $v.seller.busineessName.$invalid }"
              v-model="$v.seller.busineessName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="seller-invoiceCounter">Invoice Counter</label>
            <input
              type="number"
              class="form-control"
              name="invoiceCounter"
              id="seller-invoiceCounter"
              data-cy="invoiceCounter"
              :class="{ valid: !$v.seller.invoiceCounter.$invalid, invalid: $v.seller.invoiceCounter.$invalid }"
              v-model.number="$v.seller.invoiceCounter.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="seller-phoneNumber">Phone Number</label>
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="seller-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.seller.phoneNumber.$invalid, invalid: $v.seller.phoneNumber.$invalid }"
              v-model="$v.seller.phoneNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="seller-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="seller-email"
              data-cy="email"
              :class="{ valid: !$v.seller.email.$invalid, invalid: $v.seller.email.$invalid }"
              v-model="$v.seller.email.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.seller.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./seller-update.component.ts"></script>
