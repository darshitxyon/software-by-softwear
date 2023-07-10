import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IBuyer, Buyer } from '@/shared/model/buyer.model';
import BuyerService from './buyer.service';

const validations: any = {
  buyer: {
    busineessName: {},
    phoneNumber: {},
    email: {},
  },
};

@Component({
  validations,
})
export default class BuyerUpdate extends Vue {
  @Inject('buyerService') private buyerService: () => BuyerService;
  @Inject('alertService') private alertService: () => AlertService;

  public buyer: IBuyer = new Buyer();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.buyerId) {
        vm.retrieveBuyer(to.params.buyerId);
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
    if (this.buyer.id) {
      this.buyerService()
        .update(this.buyer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Buyer is updated with identifier ' + param.id;
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
      this.buyerService()
        .create(this.buyer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Buyer is created with identifier ' + param.id;
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

  public retrieveBuyer(buyerId): void {
    this.buyerService()
      .find(buyerId)
      .then(res => {
        this.buyer = res;
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
