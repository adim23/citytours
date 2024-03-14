import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import City from './city';
import Place from './place';
import Tour from './tour';
import TourStep from './tour-step';
import TourSchedule from './tour-schedule';
import Driver from './driver';
import Vehicle from './vehicle';
import Booking from './booking';
import Passenger from './passenger';
import ImageFile from './image-file';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="city/*" element={<City />} />
        <Route path="place/*" element={<Place />} />
        <Route path="tour/*" element={<Tour />} />
        <Route path="tour-step/*" element={<TourStep />} />
        <Route path="tour-schedule/*" element={<TourSchedule />} />
        <Route path="driver/*" element={<Driver />} />
        <Route path="vehicle/*" element={<Vehicle />} />
        <Route path="booking/*" element={<Booking />} />
        <Route path="passenger/*" element={<Passenger />} />
        <Route path="image-file/*" element={<ImageFile />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
