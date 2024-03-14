import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlace } from 'app/shared/model/place.model';
import { getEntities as getPlaces } from 'app/entities/place/place.reducer';
import { IImageFile } from 'app/shared/model/image-file.model';
import { getEntity, updateEntity, createEntity, reset } from './image-file.reducer';

export const ImageFileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const places = useAppSelector(state => state.place.entities);
  const imageFileEntity = useAppSelector(state => state.imageFile.entity);
  const loading = useAppSelector(state => state.imageFile.loading);
  const updating = useAppSelector(state => state.imageFile.updating);
  const updateSuccess = useAppSelector(state => state.imageFile.updateSuccess);

  const handleClose = () => {
    navigate('/image-file' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlaces({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...imageFileEntity,
      ...values,
      place: places.find(it => it.id.toString() === values.place.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...imageFileEntity,
          place: imageFileEntity?.place?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="citytoursApp.imageFile.home.createOrEditLabel" data-cy="ImageFileCreateUpdateHeading">
            <Translate contentKey="citytoursApp.imageFile.home.createOrEditLabel">Create or edit a ImageFile</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="image-file-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('citytoursApp.imageFile.filename')}
                id="image-file-filename"
                name="filename"
                data-cy="filename"
                type="text"
              />
              <ValidatedBlobField
                label={translate('citytoursApp.imageFile.data')}
                id="image-file-data"
                name="data"
                data-cy="data"
                isImage
                accept="image/*"
              />
              <ValidatedField
                id="image-file-place"
                name="place"
                data-cy="place"
                label={translate('citytoursApp.imageFile.place')}
                type="select"
              >
                <option value="" key="0" />
                {places
                  ? places.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/image-file" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ImageFileUpdate;
