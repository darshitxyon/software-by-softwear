import { IOrder } from '@/shared/model/order.model';

import { QuantityType } from '@/shared/model/enumerations/quantity-type.model';
export interface IItem {
  id?: string;
  name?: string | null;
  description?: string | null;
  quantityType?: QuantityType | null;
  order?: IOrder | null;
}

export class Item implements IItem {
  constructor(
    public id?: string,
    public name?: string | null,
    public description?: string | null,
    public quantityType?: QuantityType | null,
    public order?: IOrder | null
  ) {}
}
