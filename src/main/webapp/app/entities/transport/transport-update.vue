<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="sbsApp.transport.home.createOrEditLabel" data-cy="TransportCreateUpdateHeading">Create or edit a Transport</h2>
        <div>
          <div class="form-group" v-if="transport.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="transport.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="transport-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="transport-name"
              data-cy="name"
              :class="{ valid: !$v.transport.name.$invalid, invalid: $v.transport.name.$invalid }"
              v-model="$v.transport.name.$model"
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
            :disabled="$v.transport.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./transport-update.component.ts"></script>
