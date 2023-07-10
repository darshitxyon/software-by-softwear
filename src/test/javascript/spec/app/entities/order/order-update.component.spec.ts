/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import OrderUpdateComponent from '@/entities/order/order-update.vue';
import OrderClass from '@/entities/order/order-update.component';
import OrderService from '@/entities/order/order.service';

import BuyerService from '@/entities/buyer/buyer.service';

import SellerService from '@/entities/seller/seller.service';

import TransportService from '@/entities/transport/transport.service';

import ItemService from '@/entities/item/item.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Order Management Update Component', () => {
    let wrapper: Wrapper<OrderClass>;
    let comp: OrderClass;
    let orderServiceStub: SinonStubbedInstance<OrderService>;

    beforeEach(() => {
      orderServiceStub = sinon.createStubInstance<OrderService>(OrderService);

      wrapper = shallowMount<OrderClass>(OrderUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          orderService: () => orderServiceStub,
          alertService: () => new AlertService(),

          buyerService: () =>
            sinon.createStubInstance<BuyerService>(BuyerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          sellerService: () =>
            sinon.createStubInstance<SellerService>(SellerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          transportService: () =>
            sinon.createStubInstance<TransportService>(TransportService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          itemService: () =>
            sinon.createStubInstance<ItemService>(ItemService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.order = entity;
        orderServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orderServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.order = entity;
        orderServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(orderServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrder = { id: 123 };
        orderServiceStub.find.resolves(foundOrder);
        orderServiceStub.retrieve.resolves([foundOrder]);

        // WHEN
        comp.beforeRouteEnter({ params: { orderId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.order).toBe(foundOrder);
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
