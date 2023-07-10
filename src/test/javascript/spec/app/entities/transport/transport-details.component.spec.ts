/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TransportDetailComponent from '@/entities/transport/transport-details.vue';
import TransportClass from '@/entities/transport/transport-details.component';
import TransportService from '@/entities/transport/transport.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Transport Management Detail Component', () => {
    let wrapper: Wrapper<TransportClass>;
    let comp: TransportClass;
    let transportServiceStub: SinonStubbedInstance<TransportService>;

    beforeEach(() => {
      transportServiceStub = sinon.createStubInstance<TransportService>(TransportService);

      wrapper = shallowMount<TransportClass>(TransportDetailComponent, {
        store,
        localVue,
        router,
        provide: { transportService: () => transportServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTransport = { id: 123 };
        transportServiceStub.find.resolves(foundTransport);

        // WHEN
        comp.retrieveTransport(123);
        await comp.$nextTick();

        // THEN
        expect(comp.transport).toBe(foundTransport);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTransport = { id: 123 };
        transportServiceStub.find.resolves(foundTransport);

        // WHEN
        comp.beforeRouteEnter({ params: { transportId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.transport).toBe(foundTransport);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
