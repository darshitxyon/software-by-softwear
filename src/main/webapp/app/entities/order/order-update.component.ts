import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_FORMAT, DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import BuyerService from '@/entities/buyer/buyer.service';
import { IBuyer } from '@/shared/model/buyer.model';

import SellerService from '@/entities/seller/seller.service';
import { ISeller } from '@/shared/model/seller.model';

import TransportService from '@/entities/transport/transport.service';
import { ITransport } from '@/shared/model/transport.model';

import ItemService from '@/entities/item/item.service';
import { IItem, Item } from '@/shared/model/item.model';

import { IOrder, Order } from '@/shared/model/order.model';
import OrderService from './order.service';

const validations: any = {
  order: {
    date: {},
    updatedAt: {},
    createdAt: {},
    items: [],
  },
};

@Component({
  validations,
})
export default class OrderUpdate extends Vue {
  @Inject('orderService') private orderService: () => OrderService;
  @Inject('alertService') private alertService: () => AlertService;

  public order: IOrder = new Order();

  @Inject('buyerService') private buyerService: () => BuyerService;

  public buyers: IBuyer[] = [];

  @Inject('sellerService') private sellerService: () => SellerService;

  public sellers: ISeller[] = [];

  @Inject('transportService') private transportService: () => TransportService;

  public transports: ITransport[] = [];

  @Inject('itemService') private itemService: () => ItemService;

  public items: IItem[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public selecteItems: IItem[] = [];
  public count = 1;

  data() {
    return {
      count: 1,
      selectedItems: {},
      // order: {
      //   items: [] // Initialize as an empty array
      // },
      // Other data properties...
    };
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orderId) {
        vm.retrieveOrder(to.params.orderId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.order.id) {
      this.orderService()
        .update(this.order)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Order is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.orderService()
        .create(this.order)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Order is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      console.log('VALID');
      return dayjs(date).format(DATE_FORMAT);
    }
    return null;
  }

  public presentDateTime(): string {
    return dayjs().format(DATE_TIME_LONG_FORMAT);
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      console.log(event.target.value);
      this.order[field] = dayjs(event.target.value, DATE_FORMAT);
      console.log(this.order[field]);
    } else {
      this.order[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.order[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.order[field] = null;
    }
  }

  public retrieveOrder(orderId): void {
    this.orderService()
      .find(orderId)
      .then(res => {
        res.date = new Date(res.date);
        res.updatedAt = new Date(res.updatedAt);
        res.createdAt = new Date(res.createdAt);
        this.order = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.buyerService()
      .retrieve()
      .then(res => {
        this.buyers = res.data;
      });
    this.sellerService()
      .retrieve()
      .then(res => {
        this.sellers = res.data;
      });
    this.transportService()
      .retrieve()
      .then(res => {
        this.transports = res.data;
      });
    this.itemService()
      .retrieve()
      .then(res => {
        this.items = res.data;
      });
  }

  public addOrderItem(): void {
    // this.items.push({name:null});
    this.count++;
  }

  public removeOrderItem(): void {
    // this.items.push({name:null});
    this.count--;
  }

  public handleItemChange() {
    // this.items.push(item);
    // this.order["items"] = this.order.items;
    console.log(this.order);

    for (const key of Object.keys(this.selecteItems)) {
      console.log(key + '->' + JSON.stringify(this.selecteItems[key]));
      // this.order.items.push(this.selecteItems[key]);
    }

    console.log(this.order);

    this.orderService()
      .generatePDF(this.order)
      .then(response => {
        const fileUrl = URL.createObjectURL(new Blob([response]));

        // Open the generated PDF in a new window/tab for preview
        window.open(fileUrl);
      })
      .catch(error => {
        // Handle error
        console.error(error);
      });
  }
}
