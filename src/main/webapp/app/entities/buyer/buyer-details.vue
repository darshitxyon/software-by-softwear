<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="buyer">
        <h2 class="jh-entity-heading" data-cy="buyerDetailsHeading"><span>Buyer</span> {{ buyer.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Busineess Name</span>
          </dt>
          <dd>
            <span>{{ buyer.busineessName }}</span>
          </dd>
          <dt>
            <span>Phone Number</span>
          </dt>
          <dd>
            <span>{{ buyer.phoneNumber }}</span>
          </dd>
          <dt>
            <span>Email</span>
          </dt>
          <dd>
            <span>{{ buyer.email }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="buyer.id" :to="{ name: 'BuyerEdit', params: { buyerId: buyer.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./buyer-details.component.ts"></script>
