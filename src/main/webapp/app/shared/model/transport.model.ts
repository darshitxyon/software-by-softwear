export interface ITransport {
  id?: number;
  name?: string | null;
}

export class Transport implements ITransport {
  constructor(public id?: number, public name?: string | null) {}
}
