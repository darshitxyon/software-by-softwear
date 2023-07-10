export interface ISeller {
  id?: string;
  busineessName?: string | null;
  invoiceCounter?: number | null;
  phoneNumber?: string | null;
  email?: string | null;
}

export class Seller implements ISeller {
  constructor(
    public id?: string,
    public busineessName?: string | null,
    public invoiceCounter?: number | null,
    public phoneNumber?: string | null,
    public email?: string | null
  ) {}
}
