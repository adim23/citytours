import { IPlace } from 'app/shared/model/place.model';

export interface IImageFile {
  id?: number;
  filename?: string | null;
  dataContentType?: string | null;
  data?: string | null;
  place?: IPlace | null;
}

export const defaultValue: Readonly<IImageFile> = {};
