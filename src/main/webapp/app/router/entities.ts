import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Seller = () => import('@/entities/seller/seller.vue');
// prettier-ignore
const SellerUpdate = () => import('@/entities/seller/seller-update.vue');
// prettier-ignore
const SellerDetails = () => import('@/entities/seller/seller-details.vue');
// prettier-ignore
const Buyer = () => import('@/entities/buyer/buyer.vue');
// prettier-ignore
const BuyerUpdate = () => import('@/entities/buyer/buyer-update.vue');
// prettier-ignore
const BuyerDetails = () => import('@/entities/buyer/buyer-details.vue');
// prettier-ignore
const Item = () => import('@/entities/item/item.vue');
// prettier-ignore
const ItemUpdate = () => import('@/entities/item/item-update.vue');
// prettier-ignore
const ItemDetails = () => import('@/entities/item/item-details.vue');
// prettier-ignore
const Order = () => import('@/entities/order/order.vue');
// prettier-ignore
const OrderUpdate = () => import('@/entities/order/order-update.vue');
// prettier-ignore
const OrderDetails = () => import('@/entities/order/order-details.vue');
// prettier-ignore
const Transport = () => import('@/entities/transport/transport.vue');
// prettier-ignore
const TransportUpdate = () => import('@/entities/transport/transport-update.vue');
// prettier-ignore
const TransportDetails = () => import('@/entities/transport/transport-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'seller',
      name: 'Seller',
      component: Seller,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'seller/new',
      name: 'SellerCreate',
      component: SellerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'seller/:sellerId/edit',
      name: 'SellerEdit',
      component: SellerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'seller/:sellerId/view',
      name: 'SellerView',
      component: SellerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'buyer',
      name: 'Buyer',
      component: Buyer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'buyer/new',
      name: 'BuyerCreate',
      component: BuyerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'buyer/:buyerId/edit',
      name: 'BuyerEdit',
      component: BuyerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'buyer/:buyerId/view',
      name: 'BuyerView',
      component: BuyerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item',
      name: 'Item',
      component: Item,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item/new',
      name: 'ItemCreate',
      component: ItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item/:itemId/edit',
      name: 'ItemEdit',
      component: ItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'item/:itemId/view',
      name: 'ItemView',
      component: ItemDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order',
      name: 'Order',
      component: Order,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/new',
      name: 'OrderCreate',
      component: OrderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/:orderId/edit',
      name: 'OrderEdit',
      component: OrderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/:orderId/view',
      name: 'OrderView',
      component: OrderDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transport',
      name: 'Transport',
      component: Transport,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transport/new',
      name: 'TransportCreate',
      component: TransportUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transport/:transportId/edit',
      name: 'TransportEdit',
      component: TransportUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transport/:transportId/view',
      name: 'TransportView',
      component: TransportDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
