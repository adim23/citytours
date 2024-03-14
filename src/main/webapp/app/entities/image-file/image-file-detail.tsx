import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './image-file.reducer';

export const ImageFileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const imageFileEntity = useAppSelector(state => state.imageFile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="imageFileDetailsHeading">
          <Translate contentKey="citytoursApp.imageFile.detail.title">ImageFile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{imageFileEntity.id}</dd>
          <dt>
            <span id="filename">
              <Translate contentKey="citytoursApp.imageFile.filename">Filename</Translate>
            </span>
          </dt>
          <dd>{imageFileEntity.filename}</dd>
          <dt>
            <span id="data">
              <Translate contentKey="citytoursApp.imageFile.data">Data</Translate>
            </span>
          </dt>
          <dd>
            {imageFileEntity.data ? (
              <div>
                {imageFileEntity.dataContentType ? (
                  <a onClick={openFile(imageFileEntity.dataContentType, imageFileEntity.data)}>
                    <img src={`data:${imageFileEntity.dataContentType};base64,${imageFileEntity.data}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {imageFileEntity.dataContentType}, {byteSize(imageFileEntity.data)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="citytoursApp.imageFile.place">Place</Translate>
          </dt>
          <dd>{imageFileEntity.place ? imageFileEntity.place.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/image-file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/image-file/${imageFileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImageFileDetail;
