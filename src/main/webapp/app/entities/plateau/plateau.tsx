import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, updateEntity } from './plateau.reducer';
import { IPlateau } from 'app/shared/model/plateau.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import { Switch } from '@material-ui/core';
export interface IPlateauProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> { }

export const Plateau = (props: IPlateauProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    props.history.push(
      `${props.location.pathname}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`
    );
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage
    });
  const toggleActive = plateauEntity => () =>
    props.updateEntity({
      ...plateauEntity,
      valid: !plateauEntity.valid
    });

  const { plateauList, match, loading, totalItems } = props;
  const isAdmin: boolean = (localStorage.getItem('isAdmin') === 'true');
  const login: string = localStorage.getItem('login');
  const inscription = plateauEntity => () => {
    localStorage.setItem('plateauId', plateauEntity.id);
    props.history.push('/inscription/new');
  }
  function isInscription(plateauEntity) {
    let result = true;
    if (isAdmin || login === plateauEntity.user.login) {
       return false;
    }
    else {
      

      if (plateauEntity.inscriptions) {
        plateauEntity.inscriptions.find(function (value) {
          const loginValue = 'club' + value.club.id;
          if (plateauEntity.id === value.plateau.id && loginValue === login) {
            result =  false;
          }
        });
      }
    }
    return result;
  };
  return (
    <div>
      
      <h2 id="plateau-heading">
        Plateaus
        {isAdmin? null :
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Plateau
        </Link>
        }
      </h2>
      
      <div className="table-responsive">
        {plateauList && plateauList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateDebut')}>
                  Date Debut <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateFin')}>
                  Date Fin <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombreEquipeMax')}>
                  Max Equipe <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombreEquipe')}>
                  Nbr Participant <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('statut')}>
                  Statut <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Referent <FontAwesomeIcon icon="sort" />
                </th>


                <th>
                  Categorie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {plateauList.map((plateau, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${plateau.id}`} color="link" size="sm">
                      {plateau.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={plateau.dateDebut} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={plateau.dateFin} format={APP_DATE_FORMAT} />
                  </td>
                  <td className="text-center">{plateau.nombreEquipeMax}</td>
                  <td className="text-center">{plateau.nombreEquipe}</td>
                  <td>{plateau.statut}</td>
                  <td>{plateau.referent ? <Link to={`referent/${plateau.referent.id}`}>{plateau.referent.nom}</Link> : ''}</td>
                  <td>{plateau.categorie ? <Link to={`categorie/${plateau.categorie.id}`}>{plateau.categorie.section}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      {!isAdmin && login === plateau.user.login &&
                        <Button tag={Link} to={`${match.url}/${plateau.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />

                        </Button>}
                      {!isAdmin && login === plateau.user.login &&
                        <Button
                          tag={Link}
                          to={`${match.url}/${plateau.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="primary"
                          size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />

                        </Button>
                      }
                      {!isAdmin && login !== plateau.user.login &&
                        <Button tag={Link} to={`${match.url}/${plateau.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />

                        </Button>
                      }
                      {isInscription(plateau) &&
                        <Button
                          tag={Link}
                          onClick={inscription(plateau)}
                          color="primary"
                          size="sm">
                          <FontAwesomeIcon icon="plus" />
                        </Button>
                      }

                      {!isAdmin && login === plateau.user.login &&
                        <Button
                          tag={Link}
                          to={`${match.url}/${plateau.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="danger"
                          size="sm">
                          <FontAwesomeIcon icon="trash" />
                        </Button>
                      }

                      {isAdmin && (
                        <FormControlLabel
                          control={<Switch checked={plateau.valid} onChange={toggleActive(plateau)} name="checkedB" />}
                          label="Activited"
                        />
                      )}

                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
            !loading && <div className="alert alert-warning">No Plateaus found</div>
          )}
      </div>
      <div className={plateauList && plateauList.length > 0 ? '' : 'd-none'}>
        <Row className="justify-content-center">
          <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
        </Row>
        <Row className="justify-content-center">
          <JhiPagination
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
        </Row>
      </div>
    </div>
  );
};

const mapStateToProps = ({ plateau }: IRootState) => ({
  plateauList: plateau.entities,
  loading: plateau.loading,
  totalItems: plateau.totalItems
});

const mapDispatchToProps = {
  getEntities,
  updateEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Plateau);
