import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown 
  	icon="th-list" 
  	name="Entities" id="entity-menu" 
  	style={{ maxHeight: '80vh', overflow: 'auto' }}>
  	
    <MenuItem icon="asterisk" to="/club">
      Club
    </MenuItem>
    <MenuItem icon="asterisk" to="/logo-club">
      Logo Club
    </MenuItem>
    <MenuItem icon="asterisk" to="/stade">
      Stade
    </MenuItem>
    <MenuItem icon="asterisk" to="/categorie">
      Categorie
    </MenuItem>
    <MenuItem icon="asterisk" to="/referent">
      Referent
    </MenuItem>
    <MenuItem icon="asterisk" to="/plateau">
      Plateau
    </MenuItem>
    <MenuItem icon="asterisk" to="/document-plateau">
      Document Plateau
    </MenuItem>
    
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
