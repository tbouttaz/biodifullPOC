import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICampagne } from 'app/shared/model/campagne.model';
import { getEntities as getCampagnes } from 'app/entities/campagne/campagne.reducer';
import { getEntity, updateEntity, createEntity, reset } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAnswerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAnswerUpdateState {
  isNew: boolean;
  campagneId: string;
}

export class AnswerUpdate extends React.Component<IAnswerUpdateProps, IAnswerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      campagneId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCampagnes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { answerEntity } = this.props;
      const entity = {
        ...answerEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/answer');
  };

  render() {
    const { answerEntity, campagnes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="biodifullPocApp.answer.home.createOrEditLabel">Create or edit a Answer</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : answerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="answer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="jugeIDLabel" for="jugeID">
                    Juge ID
                  </Label>
                  <AvField id="answer-jugeID" type="text" name="jugeID" />
                </AvGroup>
                <AvGroup>
                  <Label id="challenger1Label" for="challenger1">
                    Challenger 1
                  </Label>
                  <AvField id="answer-challenger1" type="text" name="challenger1" />
                </AvGroup>
                <AvGroup>
                  <Label id="challenger2Label" for="challenger2">
                    Challenger 2
                  </Label>
                  <AvField id="answer-challenger2" type="text" name="challenger2" />
                </AvGroup>
                <AvGroup>
                  <Label id="winnerLabel" for="winner">
                    Winner
                  </Label>
                  <AvField id="answer-winner" type="text" name="winner" />
                </AvGroup>
                <AvGroup>
                  <Label for="campagne.id">Campagne</Label>
                  <AvInput id="answer-campagne" type="select" className="form-control" name="campagneId">
                    <option value="" key="0" />
                    {campagnes
                      ? campagnes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/answer" replace color="info">
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
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  campagnes: storeState.campagne.entities,
  answerEntity: storeState.answer.entity,
  loading: storeState.answer.loading,
  updating: storeState.answer.updating
});

const mapDispatchToProps = {
  getCampagnes,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AnswerUpdate);
