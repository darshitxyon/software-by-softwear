export interface IBuyer {
  id?: string;
  busineessName?: string | null;
  phoneNumber?: string | null;
  email?: string | null;
}

export class Buyer implements IBuyer {
  constructor(public id?: string, public busineessName?: string | null, public phoneNumber?: string | null, public email?: string | null) {}
}
