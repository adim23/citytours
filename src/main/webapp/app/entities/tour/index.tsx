import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tour from './tour';
import TourDetail from './tour-detail';
import TourUpdate from './tour-update';
import TourDeleteDialog from './tour-delete-dialog';

const TourRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tour />} />
    <Route path="new" element={<TourUpdate />} />
    <Route path=":id">
      <Route index element={<TourDetail />} />
      <Route path="edit" element={<TourUpdate />} />
      <Route path="delete" element={<TourDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TourRoutes;
