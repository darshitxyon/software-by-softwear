<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="seller">
        <h2 class="jh-entity-heading" data-cy="sellerDetailsHeading"><span>Seller</span> {{ seller.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Busineess Name</span>
          </dt>
          <dd>
            <span>{{ seller.busineessName }}</span>
          </dd>
          <dt>
            <span>Invoice Counter</span>
          </dt>
          <dd>
            <span>{{ seller.invoiceCounter }}</span>
          </dd>
          <dt>
            <span>Phone Number</span>
          </dt>
          <dd>
            <span>{{ seller.phoneNumber }}</span>
          </dd>
          <dt>
            <span>Email</span>
          </dt>
          <dd>
            <span>{{ seller.email }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="seller.id" :to="{ name: 'SellerEdit', params: { sellerId: seller.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./seller-details.component.ts"></script>
