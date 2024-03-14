import { ITourStep } from 'app/shared/model/tour-step.model';
import { IImageFile } from 'app/shared/model/image-file.model';
import { ICity } from 'app/shared/model/city.model';

export interface IPlace {
  id?: number;
  code?: string | null;
  name?: string;
  description?: string;
  fullDescription?: string;
  longitude?: string | null;
  latitude?: string | null;
  steps?: ITourStep[] | null;
  images?: IImageFile[] | null;
  city?: ICity | null;
}

export const defaultValue: Readonly<IPlace> = {};
