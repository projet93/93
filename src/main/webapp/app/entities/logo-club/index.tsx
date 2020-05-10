import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LogoClub from './logo-club';
import LogoClubDetail from './logo-club-detail';
import LogoClubUpdate from './logo-club-update';
import LogoClubDeleteDialog from './logo-club-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LogoClubDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LogoClubUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LogoClubUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LogoClubDetail} />
      <ErrorBoundaryRoute path={match.url} component={LogoClub} />
    </Switch>
  </>
);

export default Routes;
