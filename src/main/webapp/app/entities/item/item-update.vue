<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="sbsApp.item.home.createOrEditLabel" data-cy="ItemCreateUpdateHeading">Create or edit a Item</h2>
        <div>
          <div class="form-group" v-if="item.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="item.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="item-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="item-name"
              data-cy="name"
              :class="{ valid: !$v.item.name.$invalid, invalid: $v.item.name.$invalid }"
              v-model="$v.item.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="item-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="item-description"
              data-cy="description"
              :class="{ valid: !$v.item.description.$invalid, invalid: $v.item.description.$invalid }"
              v-model="$v.item.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="item-quantityType">Quantity Type</label>
            <select
              class="form-control"
              name="quantityType"
              :class="{ valid: !$v.item.quantityType.$invalid, invalid: $v.item.quantityType.$invalid }"
              v-model="$v.item.quantityType.$model"
              id="item-quantityType"
              data-cy="quantityType"
            >
              <option v-for="quantityType in quantityTypeValues" :key="quantityType" v-bind:value="quantityType">{{ quantityType }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="item-order">Order</label>
            <select class="form-control" id="item-order" data-cy="order" name="order" v-model="item.order">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="item.order && orderOption.id === item.order.id ? item.order : orderOption"
                v-for="orderOption in orders"
                :key="orderOption.id"
              >
                {{ orderOption.id }}
              </option>
            </select>
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
            :disabled="$v.item.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./item-update.component.ts"></script>
