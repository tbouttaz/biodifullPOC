import './home.css';

import React from 'react';
import { Link } from 'react-router-dom';

import { connect } from 'react-redux';
import { Row, Col, Alert } from 'reactstrap';

import { getEntities, reset } from '../../entities/campagne/campagne.reducer';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';

export interface IHomeProp extends StateProps, DispatchProps {}

export class Home extends React.Component<IHomeProp> {
  componentDidMount() {
    this.props.getSession();
  }

  componentWillMount() {
    this.getEntities();
  }

  getEntities = () => {
    this.props.getEntities(0, 20, 'id,asc');
  };

  render() {
    const { campagneList, account } = this.props;
    return (
      <Row>
        <Col md="9">
          <h2>Welcome to the Biodifull Project!</h2>
          <p className="lead">Presentation of the Biodifull Project</p>
          {account && account.login ? (
            <div>
              <Alert color="success">You are logged in as user {account.login}.</Alert>
            </div>
          ) : (
            <div>
              <Alert color="warning">
                If you want to
                <Link to="/login" className="alert-link">
                  {' '}
                  sign in
                </Link>
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Alert>

              <Alert color="warning">
                You do not have an account yet?&nbsp;
                <Link to="/register" className="alert-link">
                  Register a new account
                </Link>
              </Alert>
            </div>
          )}
          <p>List of open campains:</p>
          <ul>
            {/* TODO - this doesn't work - connect() not initialising connection to Campagne in Redux? */}
            {campagneList.map((campagne, i) => (
              <li>
                <a href={`entity/campagne/${campagne.id}`} target="_blank" rel="noopener noreferrer">
                  {campagne.campagneName}
                </a>
              </li>
              // <p>listItem {campagne.id}</p>
            ))}
          </ul>

          <p>
            If you like Biodifull, do not forget to give us a star on{' '}
            <a href="https://github.com/tbouttaz/biodifullPOC" target="_blank" rel="noopener noreferrer">
              Github
            </a>
            !
          </p>
        </Col>
        <Col md="3" className="pad">
          <span className="hipster rounded" />
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
  campagneList: storeState.campagne.entities
});

const mapDispatchToProps = { getEntities, getSession };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
