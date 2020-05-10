import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILogoClub } from 'app/shared/model/logo-club.model';
import { getEntities as getLogoClubs } from 'app/entities/logo-club/logo-club.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities as getCategories } from 'app/entities/categorie/categorie.reducer';
import { getEntity, updateEntity, createEntity, reset } from './club.reducer';
import { IClub } from 'app/shared/model/club.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClubUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClubUpdate = (props: IClubUpdateProps) => {
  const [idscategorie, setIdscategorie] = useState([]);
  const [logoClubId, setLogoClubId] = useState('0');
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { clubEntity, logoClubs, users, categories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/club' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLogoClubs();
    props.getUsers();
    props.getCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...clubEntity,
        ...values,
        categories: mapIdList(values.categories)
      };
      entity.user = users[values.user];

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="App.club.home.createOrEditLabel">Create or edit a Club</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : clubEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="club-id">ID</Label>
                  <AvInput id="club-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomLabel" for="club-nom">
                  Nom
                </Label>
                <AvField
                  id="club-nom"
                  type="text"
                  name="nom"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="adresseLabel" for="club-adresse">
                  Adresse
                </Label>
                <AvField id="club-adresse" type="text" name="adresse" />
              </AvGroup>
              <AvGroup>
                <Label id="telephoneLabel" for="club-telephone">
                  Telephone
                </Label>
                <AvField id="club-telephone" type="text" name="telephone" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="club-email">
                  Email
                </Label>
                <AvField
                  id="club-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' }
                  }}
                />
              </AvGroup>
             
             
              <AvGroup>
                <Label for="club-categorie">Categorie</Label>
                <AvInput
                  id="club-categorie"
                  type="select"
                  multiple
                  className="form-control"
                  name="categories"
                  value={clubEntity.categories && clubEntity.categories.map(e => e.id)}
                >
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.section}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/club" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  logoClubs: storeState.logoClub.entities,
  users: storeState.userManagement.users,
  categories: storeState.categorie.entities,
  clubEntity: storeState.club.entity,
  loading: storeState.club.loading,
  updating: storeState.club.updating,
  updateSuccess: storeState.club.updateSuccess
});

const mapDispatchToProps = {
  getLogoClubs,
  getUsers,
  getCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClubUpdate);
