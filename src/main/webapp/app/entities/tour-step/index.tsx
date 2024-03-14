import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TourStep from './tour-step';
import TourStepDetail from './tour-step-detail';
import TourStepUpdate from './tour-step-update';
import TourStepDeleteDialog from './tour-step-delete-dialog';

const TourStepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TourStep />} />
    <Route path="new" element={<TourStepUpdate />} />
    <Route path=":id">
      <Route index element={<TourStepDetail />} />
      <Route path="edit" element={<TourStepUpdate />} />
      <Route path="delete" element={<TourStepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TourStepRoutes;
