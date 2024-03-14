import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPassenger } from 'app/shared/model/passenger.model';
import { getEntity, updateEntity, createEntity, reset } from './passenger.reducer';

export const PassengerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const passengerEntity = useAppSelector(state => state.passenger.entity);
  const loading = useAppSelector(state => state.passenger.loading);
  const updating = useAppSelector(state => state.passenger.updating);
  const updateSuccess = useAppSelector(state => state.passenger.updateSuccess);

  const handleClose = () => {
    navigate('/passenger' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.age !== undefined && typeof values.age !== 'number') {
      values.age = Number(values.age);
    }

    const entity = {
      ...passengerEntity,
      ...values,
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
          ...passengerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="citytoursApp.passenger.home.createOrEditLabel" data-cy="PassengerCreateUpdateHeading">
            <Translate contentKey="citytoursApp.passenger.home.createOrEditLabel">Create or edit a Passenger</Translate>
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
                  id="passenger-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('citytoursApp.passenger.name')} id="passenger-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('citytoursApp.passenger.email')}
                id="passenger-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.passenger.mobile')}
                id="passenger-mobile"
                name="mobile"
                data-cy="mobile"
                type="text"
              />
              <ValidatedField label={translate('citytoursApp.passenger.age')} id="passenger-age" name="age" data-cy="age" type="text" />
              <ValidatedField
                label={translate('citytoursApp.passenger.gender')}
                id="passenger-gender"
                name="gender"
                data-cy="gender"
                type="text"
              />
              <ValidatedField
                label={translate('citytoursApp.passenger.nationality')}
                id="passenger-nationality"
                name="nationality"
                data-cy="nationality"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/passenger" replace color="info">
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

export default PassengerUpdate;
