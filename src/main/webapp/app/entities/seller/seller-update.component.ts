import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ISeller, Seller } from '@/shared/model/seller.model';
import SellerService from './seller.service';

const validations: any = {
  seller: {
    busineessName: {},
    invoiceCounter: {},
    phoneNumber: {},
    email: {},
  },
};

@Component({
  validations,
})
export default class SellerUpdate extends Vue {
  @Inject('sellerService') private sellerService: () => SellerService;
  @Inject('alertService') private alertService: () => AlertService;

  public seller: ISeller = new Seller();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sellerId) {
        vm.retrieveSeller(to.params.sellerId);
      }
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
    if (this.seller.id) {
      this.sellerService()
        .update(this.seller)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Seller is updated with identifier ' + param.id;
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
      this.sellerService()
        .create(this.seller)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Seller is created with identifier ' + param.id;
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

  public retrieveSeller(sellerId): void {
    this.sellerService()
      .find(sellerId)
      .then(res => {
        this.seller = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
