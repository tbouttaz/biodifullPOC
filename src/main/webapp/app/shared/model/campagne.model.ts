export interface ICampagne {
  id?: number;
  campagneName?: string;
  campagneDescription?: string;
  formURL?: string;
  challengersLocation?: string;
}

export const defaultValue: Readonly<ICampagne> = {};
