import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClub } from 'app/shared/model/club.model';
import { getEntities as getClubs } from 'app/entities/club/club.reducer';
import { IReferent } from 'app/shared/model/referent.model';
import { getEntities as getReferents } from 'app/entities/referent/referent.reducer';
import { IPlateau } from 'app/shared/model/plateau.model';
import { getEntities as getPlateaus } from 'app/entities/plateau/plateau.reducer';
import { getEntity, updateEntity, createEntity, reset } from './inscription.reducer';
import { IInscription } from 'app/shared/model/inscription.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInscriptionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InscriptionUpdate = (props: IInscriptionUpdateProps) => {
  const [clubId, setClubId] = useState('0');
  const [referentId, setReferentId] = useState('0');
  const [plateauId, setPlateauId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { inscriptionEntity, clubs, referents, plateaus, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/plateau');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClubs();
    props.getReferents();
    props.getPlateaus();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...inscriptionEntity,
        ...values
      };

      if (isNew) {
        window.console.log('---->',entity);
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
          <h2 id="App.inscription.home.createOrEditLabel">Create or edit a Inscription</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : inscriptionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="inscription-id">ID</Label>
                  <AvInput id="inscription-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                  <Label for="inscription-plateau">
                    Plateau
                  </Label>
                  <AvInput id="inscription-plateau" type="text" className="form-control" name="plateau.id" value={localStorage.getItem('plateauId')} readOnly />

                </AvGroup>
              <AvGroup>
                <Label id="nombreEquipeLabel" for="inscription-nombreEquipe">
                  Nombre Equipe
                </Label>
                <AvField
                  id="inscription-nombreEquipe"
                  type="number"
                  className="form-control"
                  name="nombreEquipe"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' }
                  }}
                />
              </AvGroup>
              
              <AvGroup>
                <Label for="inscription-referent">Referent</Label>
                <AvInput id="inscription-referent" type="select" className="form-control" name="referent.id">
                  <option value="" key="0" />
                  {referents
                    ? referents.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nom}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              
              <Button tag={Link} id="cancel-save" to="/plateau" replace color="info">
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
  clubs: storeState.club.entities,
  referents: storeState.referent.entities,
  plateaus: storeState.plateau.entities,
  inscriptionEntity: storeState.inscription.entity,
  loading: storeState.inscription.loading,
  updating: storeState.inscription.updating,
  updateSuccess: storeState.inscription.updateSuccess
});

const mapDispatchToProps = {
  getClubs,
  getReferents,
  getPlateaus,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InscriptionUpdate);
