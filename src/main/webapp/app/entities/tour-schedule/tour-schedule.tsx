import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './tour-schedule.reducer';

export const TourSchedule = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const tourScheduleList = useAppSelector(state => state.tourSchedule.entities);
  const loading = useAppSelector(state => state.tourSchedule.loading);
  const totalItems = useAppSelector(state => state.tourSchedule.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="tour-schedule-heading" data-cy="TourScheduleHeading">
        <Translate contentKey="citytoursApp.tourSchedule.home.title">Tour Schedules</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="citytoursApp.tourSchedule.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tour-schedule/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="citytoursApp.tourSchedule.home.createLabel">Create new Tour Schedule</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tourScheduleList && tourScheduleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="citytoursApp.tourSchedule.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="citytoursApp.tourSchedule.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('startDatetime')}>
                  <Translate contentKey="citytoursApp.tourSchedule.startDatetime">Start Datetime</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startDatetime')} />
                </th>
                <th className="hand" onClick={sort('noPassengers')}>
                  <Translate contentKey="citytoursApp.tourSchedule.noPassengers">No Passengers</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('noPassengers')} />
                </th>
                <th className="hand" onClick={sort('noKids')}>
                  <Translate contentKey="citytoursApp.tourSchedule.noKids">No Kids</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('noKids')} />
                </th>
                <th className="hand" onClick={sort('noPets')}>
                  <Translate contentKey="citytoursApp.tourSchedule.noPets">No Pets</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('noPets')} />
                </th>
                <th className="hand" onClick={sort('startPlace')}>
                  <Translate contentKey="citytoursApp.tourSchedule.startPlace">Start Place</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startPlace')} />
                </th>
                <th className="hand" onClick={sort('endPlace')}>
                  <Translate contentKey="citytoursApp.tourSchedule.endPlace">End Place</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endPlace')} />
                </th>
                <th>
                  <Translate contentKey="citytoursApp.tourSchedule.tour">Tour</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="citytoursApp.tourSchedule.vehicle">Vehicle</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="citytoursApp.tourSchedule.driver">Driver</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tourScheduleList.map((tourSchedule, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tour-schedule/${tourSchedule.id}`} color="link" size="sm">
                      {tourSchedule.id}
                    </Button>
                  </td>
                  <td>{tourSchedule.code}</td>
                  <td>
                    {tourSchedule.startDatetime ? (
                      <TextFormat type="date" value={tourSchedule.startDatetime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{tourSchedule.noPassengers}</td>
                  <td>{tourSchedule.noKids}</td>
                  <td>{tourSchedule.noPets}</td>
                  <td>{tourSchedule.startPlace}</td>
                  <td>{tourSchedule.endPlace}</td>
                  <td>{tourSchedule.tour ? <Link to={`/tour/${tourSchedule.tour.id}`}>{tourSchedule.tour.title}</Link> : ''}</td>
                  <td>
                    {tourSchedule.vehicle ? <Link to={`/vehicle/${tourSchedule.vehicle.id}`}>{tourSchedule.vehicle.plate}</Link> : ''}
                  </td>
                  <td>{tourSchedule.driver ? <Link to={`/driver/${tourSchedule.driver.id}`}>{tourSchedule.driver.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tour-schedule/${tourSchedule.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tour-schedule/${tourSchedule.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/tour-schedule/${tourSchedule.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="citytoursApp.tourSchedule.home.notFound">No Tour Schedules found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={tourScheduleList && tourScheduleList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default TourSchedule;
