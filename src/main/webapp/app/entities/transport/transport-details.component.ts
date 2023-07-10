import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITransport } from '@/shared/model/transport.model';
import TransportService from './transport.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TransportDetails extends Vue {
  @Inject('transportService') private transportService: () => TransportService;
  @Inject('alertService') private alertService: () => AlertService;

  public transport: ITransport = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transportId) {
        vm.retrieveTransport(to.params.transportId);
      }
    });
  }

  public retrieveTransport(transportId) {
    this.transportService()
      .find(transportId)
      .then(res => {
        this.transport = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
