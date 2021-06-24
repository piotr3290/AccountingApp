package pl.wroblewski.simplyaccounting.interfaces;

import pl.wroblewski.simplyaccounting.enums.RateHierarchyLevel;

public interface IHierarchy {

    Integer getCooperativeId();

    Integer getPremisesId();

    Integer getBuildingId();

    RateHierarchyLevel getHierarchyLevel();

}
