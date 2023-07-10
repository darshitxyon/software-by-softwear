<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="order">
        <h2 class="jh-entity-heading" data-cy="orderDetailsHeading"><span>Order</span> {{ order.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Date</span>
          </dt>
          <dd>
            <span>{{ order.date | formatDate }}</span>
          </dd>
          <dt>
            <span>Updated At</span>
          </dt>
          <dd>
            <span>{{ order.updatedAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Created At</span>
          </dt>
          <dd>
            <span>{{ order.createdAt | formatDate }}</span>
          </dd>
          <dt>
            <span>Buyer</span>
          </dt>
          <dd>
            <div v-if="order.buyer">
              <router-link :to="{ name: 'BuyerView', params: { buyerId: order.buyer.id } }">{{ order.buyer.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Seller</span>
          </dt>
          <dd>
            <div v-if="order.seller">
              <router-link :to="{ name: 'SellerView', params: { sellerId: order.seller.id } }">{{ order.seller.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Transport</span>
          </dt>
          <dd>
            <div v-if="order.transport">
              <router-link :to="{ name: 'TransportView', params: { transportId: order.transport.id } }">{{
                order.transport.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="order.id" :to="{ name: 'OrderEdit', params: { orderId: order.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./order-details.component.ts"></script>
