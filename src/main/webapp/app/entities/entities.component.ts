import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import SellerService from './seller/seller.service';
import BuyerService from './buyer/buyer.service';
import ItemService from './item/item.service';
import OrderService from './order/order.service';
import TransportService from './transport/transport.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('sellerService') private sellerService = () => new SellerService();
  @Provide('buyerService') private buyerService = () => new BuyerService();
  @Provide('itemService') private itemService = () => new ItemService();
  @Provide('orderService') private orderService = () => new OrderService();
  @Provide('transportService') private transportService = () => new TransportService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
