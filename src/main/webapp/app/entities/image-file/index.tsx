import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ImageFile from './image-file';
import ImageFileDetail from './image-file-detail';
import ImageFileUpdate from './image-file-update';
import ImageFileDeleteDialog from './image-file-delete-dialog';

const ImageFileRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ImageFile />} />
    <Route path="new" element={<ImageFileUpdate />} />
    <Route path=":id">
      <Route index element={<ImageFileDetail />} />
      <Route path="edit" element={<ImageFileUpdate />} />
      <Route path="delete" element={<ImageFileDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ImageFileRoutes;
