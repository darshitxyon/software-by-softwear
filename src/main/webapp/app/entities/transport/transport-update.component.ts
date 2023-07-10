import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ITransport, Transport } from '@/shared/model/transport.model';
import TransportService from './transport.service';

const validations: any = {
  transport: {
    name: {},
  },
};

@Component({
  validations,
})
export default class TransportUpdate extends Vue {
  @Inject('transportService') private transportService: () => TransportService;
  @Inject('alertService') private alertService: () => AlertService;

  public transport: ITransport = new Transport();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transportId) {
        vm.retrieveTransport(to.params.transportId);
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
    if (this.transport.id) {
      this.transportService()
        .update(this.transport)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Transport is updated with identifier ' + param.id;
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
      this.transportService()
        .create(this.transport)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Transport is created with identifier ' + param.id;
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

  public retrieveTransport(transportId): void {
    this.transportService()
      .find(transportId)
      .then(res => {
        this.transport = res;
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
