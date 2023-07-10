/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SellerDetailComponent from '@/entities/seller/seller-details.vue';
import SellerClass from '@/entities/seller/seller-details.component';
import SellerService from '@/entities/seller/seller.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Seller Management Detail Component', () => {
    let wrapper: Wrapper<SellerClass>;
    let comp: SellerClass;
    let sellerServiceStub: SinonStubbedInstance<SellerService>;

    beforeEach(() => {
      sellerServiceStub = sinon.createStubInstance<SellerService>(SellerService);

      wrapper = shallowMount<SellerClass>(SellerDetailComponent, {
        store,
        localVue,
        router,
        provide: { sellerService: () => sellerServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSeller = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        sellerServiceStub.find.resolves(foundSeller);

        // WHEN
        comp.retrieveSeller('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.seller).toBe(foundSeller);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSeller = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        sellerServiceStub.find.resolves(foundSeller);

        // WHEN
        comp.beforeRouteEnter({ params: { sellerId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.seller).toBe(foundSeller);
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
