import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/city">
        <Translate contentKey="global.menu.entities.city" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/place">
        <Translate contentKey="global.menu.entities.place" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour">
        <Translate contentKey="global.menu.entities.tour" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour-step">
        <Translate contentKey="global.menu.entities.tourStep" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tour-schedule">
        <Translate contentKey="global.menu.entities.tourSchedule" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/driver">
        <Translate contentKey="global.menu.entities.driver" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vehicle">
        <Translate contentKey="global.menu.entities.vehicle" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/booking">
        <Translate contentKey="global.menu.entities.booking" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/passenger">
        <Translate contentKey="global.menu.entities.passenger" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/image-file">
        <Translate contentKey="global.menu.entities.imageFile" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
