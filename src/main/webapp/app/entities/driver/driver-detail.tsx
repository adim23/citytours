import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './driver.reducer';

export const DriverDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const driverEntity = useAppSelector(state => state.driver.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="driverDetailsHeading">
          <Translate contentKey="citytoursApp.driver.detail.title">Driver</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{driverEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="citytoursApp.driver.name">Name</Translate>
            </span>
          </dt>
          <dd>{driverEntity.name}</dd>
          <dt>
            <span id="hiredAt">
              <Translate contentKey="citytoursApp.driver.hiredAt">Hired At</Translate>
            </span>
          </dt>
          <dd>{driverEntity.hiredAt ? <TextFormat value={driverEntity.hiredAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="citytoursApp.driver.age">Age</Translate>
            </span>
          </dt>
          <dd>{driverEntity.age}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="citytoursApp.driver.email">Email</Translate>
            </span>
          </dt>
          <dd>{driverEntity.email}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="citytoursApp.driver.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{driverEntity.mobile}</dd>
        </dl>
        <Button tag={Link} to="/driver" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/driver/${driverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DriverDetail;
