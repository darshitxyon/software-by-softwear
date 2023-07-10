<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="sbsApp.buyer.home.createOrEditLabel" data-cy="BuyerCreateUpdateHeading">Create or edit a Buyer</h2>
        <div>
          <div class="form-group" v-if="buyer.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="buyer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="buyer-busineessName">Busineess Name</label>
            <input
              type="text"
              class="form-control"
              name="busineessName"
              id="buyer-busineessName"
              data-cy="busineessName"
              :class="{ valid: !$v.buyer.busineessName.$invalid, invalid: $v.buyer.busineessName.$invalid }"
              v-model="$v.buyer.busineessName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="buyer-phoneNumber">Phone Number</label>
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="buyer-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.buyer.phoneNumber.$invalid, invalid: $v.buyer.phoneNumber.$invalid }"
              v-model="$v.buyer.phoneNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="buyer-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="buyer-email"
              data-cy="email"
              :class="{ valid: !$v.buyer.email.$invalid, invalid: $v.buyer.email.$invalid }"
              v-model="$v.buyer.email.$model"
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
            :disabled="$v.buyer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./buyer-update.component.ts"></script>
