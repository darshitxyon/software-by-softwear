<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="sbsApp.order.home.createOrEditLabel" data-cy="OrderCreateUpdateHeading">Create or edit a Order</h2>
        <div>
          <div class="form-group" v-if="order.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="order.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="order-date">Date</label>
            <div class="d-flex">
              <input
                id="order-date"
                data-cy="date"
                type="date"
                class="form-control"
                name="date"
                :class="{ valid: !$v.order.date.$invalid, invalid: $v.order.date.$invalid }"
                :value="convertDateTimeFromServer($v.order.date.$model)"
                @change="updateInstantField('date', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="order-updatedAt">Updated At</label>
            <div class="d-flex">
              <input
                id="order-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.order.updatedAt.$invalid, invalid: $v.order.updatedAt.$invalid }"
                :value="presentDateTime()"
                disabled
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="order-createdAt">Created At</label>
            <div class="d-flex">
              <input
                id="order-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.order.createdAt.$invalid, invalid: $v.order.createdAt.$invalid }"
                :value="presentDateTime()"
                disabled
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="order-buyer">Buyer</label>
            <select class="form-control" id="order-buyer" data-cy="buyer" name="buyer" v-model="order.buyer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="order.buyer && buyerOption.busineessName === order.buyer.busineessName ? order.buyer : buyerOption"
                v-for="buyerOption in buyers"
                :key="buyerOption.busineessName"
              >
                {{ buyerOption.busineessName }}:{{ buyerOption.email }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="order-seller">Seller</label>
            <select class="form-control" id="order-seller" data-cy="seller" name="seller" v-model="order.seller">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="order.seller && sellerOption.busineessName === order.seller.busineessName ? order.seller : sellerOption"
                v-for="sellerOption in sellers"
                :key="sellerOption.busineessName"
              >
                {{ sellerOption.busineessName }}:{{ sellerOption.email }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="order-transport">Transport</label>
            <select class="form-control" id="order-transport" data-cy="transport" name="transport" v-model="order.transport">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="order.transport && transportOption.name === order.transport.name ? order.transport : transportOption"
                v-for="transportOption in transports"
                :key="transportOption.name"
              >
                {{ transportOption.name }}
              </option>
            </select>
          </div>

          <div class="form-group" v-for="index in count" :key="index">
            <label class="form-control-label" for="order-items">Items</label>
            <select class="form-control" id="order-items" data-cy="items" name="items" v-model="selecteItems['item' + index]">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  selecteItems['item' + index] && itemOption.name == selecteItems['item' + index].name
                    ? selecteItems['item' + index]
                    : itemOption
                "
                v-for="itemOption in items"
                :key="itemOption.id"
              >
                {{ itemOption.name }}-{{ itemOption.quantityType }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <!--            <a href="#" id="add_item" @click="addOrderItem"></a>-->
            <button type="button" class="btn btn-secondary" @click="addOrderItem">
              <font-awesome-icon icon="plus"></font-awesome-icon>&nbsp;<span>Add</span>
            </button>
            <button type="button" class="btn btn-secondary" @click="removeOrderItem">
              <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Remove</span>
            </button>
          </div>

          <div class="form-group">
            <button type="button" class="btn btn-secondary" @click="handleItemChange">Preview PDF</button>
          </div>

          <!--          <div class="form-group">-->
          <!--            <label class="form-control-label" for="order-items">Items</label>-->
          <!--            <select class="form-control" id="order-items" data-cy="items" name="items" v-model="order.items">-->
          <!--              <option v-bind:value="null"></option>-->
          <!--              <option-->
          <!--                v-for="itemsOption in items"-->
          <!--                v-bind:value="itemsOption"-->
          <!--                :key="itemsOption.name"-->
          <!--              >-->
          <!--                {{ itemsOption.name }}-->
          <!--              </option>-->
          <!--            </select>-->
          <!--            <button type="button" :disabled="$v.order.$invalid || isSaving" v-on:click="handleItemChange(selectedItem)">Select</button>-->
          <!--          </div>-->
          <!--        </div>-->
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.order.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./order-update.component.ts"></script>
