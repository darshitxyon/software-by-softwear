import { IBuyer } from '@/shared/model/buyer.model';
import { ISeller } from '@/shared/model/seller.model';
import { ITransport } from '@/shared/model/transport.model';
import { IItem } from '@/shared/model/item.model';

export interface IOrder {
  id?: number;
  date?: Date | null;
  updatedAt?: Date | null;
  createdAt?: Date | null;
  buyer?: IBuyer | null;
  seller?: ISeller | null;
  transport?: ITransport | null;
  items?: IItem[] | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public date?: Date | null,
    public updatedAt?: Date | null,
    public createdAt?: Date | null,
    public buyer?: IBuyer | null,
    public seller?: ISeller | null,
    public transport?: ITransport | null,
    public items?: IItem[] | null
  ) {}
}
