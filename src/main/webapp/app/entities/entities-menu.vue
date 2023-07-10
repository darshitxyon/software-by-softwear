<template>
  <div>
    <b-dropdown-item to="/seller">
      <font-awesome-icon icon="asterisk" />
      <span>Seller</span>
    </b-dropdown-item>
    <b-dropdown-item to="/buyer">
      <font-awesome-icon icon="asterisk" />
      <span>Buyer</span>
    </b-dropdown-item>
    <b-dropdown-item to="/item">
      <font-awesome-icon icon="asterisk" />
      <span>Item</span>
    </b-dropdown-item>
    <b-dropdown-item to="/order">
      <font-awesome-icon icon="asterisk" />
      <span>Order</span>
    </b-dropdown-item>
    <b-dropdown-item to="/transport">
      <font-awesome-icon icon="asterisk" />
      <span>Transport</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
