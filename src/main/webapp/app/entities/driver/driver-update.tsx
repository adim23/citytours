import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDriver } from 'app/shared/model/driver.model';
import { getEntity, updateEntity, createEntity, reset } from './driver.reducer';

export const DriverUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const driverEntity = useAppSelector(state => state.driver.entity);
  const loading = useAppSelector(state => state.driver.loading);
  const updating = useAppSelector(state => state.driver.updating);
  const updateSuccess = useAppSelector(state => state.driver.updateSuccess);

  const handleClose = () => {
    navigate('/driver' + location.search);
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
    values.hiredAt = convertDateTimeToServer(values.hiredAt);
    if (values.age !== undefined && typeof values.age !== 'number') {
      values.age = Number(values.age);
    }

    const entity = {
      ...driverEntity,
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
      ? {
          hiredAt: displayDefaultDateTime(),
        }
      : {
          ...driverEntity,
          hiredAt: convertDateTimeFromServer(driverEntity.hiredAt),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="citytoursApp.driver.home.createOrEditLabel" data-cy="DriverCreateUpdateHeading">
            <Translate contentKey="citytoursApp.driver.home.createOrEditLabel">Create or edit a Driver</Translate>
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
                  id="driver-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('citytoursApp.driver.firstName')}
                id="driver-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('citytoursApp.driver.lastName')}
                id="driver-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('citytoursApp.driver.hiredAt')}
                id="driver-hiredAt"
                name="hiredAt"
                data-cy="hiredAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('citytoursApp.driver.age')} id="driver-age" name="age" data-cy="age" type="text" />
              <ValidatedField label={translate('citytoursApp.driver.email')} id="driver-email" name="email" data-cy="email" type="text" />
              <ValidatedField
                label={translate('citytoursApp.driver.mobile')}
                id="driver-mobile"
                name="mobile"
                data-cy="mobile"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/driver" replace color="info">
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

export default DriverUpdate;
