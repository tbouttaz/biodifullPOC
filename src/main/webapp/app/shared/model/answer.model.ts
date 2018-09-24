export interface IAnswer {
  id?: number;
  jugeID?: string;
  challenger1?: string;
  challenger2?: string;
  winner?: string;
  campagneId?: number;
}

export const defaultValue: Readonly<IAnswer> = {};
