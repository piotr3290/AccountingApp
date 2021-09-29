package pl.wroblewski.simplyaccounting.enums;

import pl.wroblewski.simplyaccounting.interfaces.IHierarchy;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum RateHierarchyLevel {
    GENERAL,
    COOPERATIVE,
    BUILDING,
    PREMISES;

    public static RateHierarchyLevel getHierarchyLevel(IHierarchy hierarchy) {
        if (hierarchy.getBuildingId() != null) {
            return BUILDING;
        } else if (hierarchy.getPremisesId() != null) {
            return PREMISES;
        } else if (hierarchy.getCooperativeId() != null) {
            return COOPERATIVE;
        } else {
            return GENERAL;
        }
    }

    public static <T extends IHierarchy> List<T> filterMatchingHierarchyForPremises(Collection<T> hierarchyList, Integer cooperativeId,
                                                                              Integer buildingId, Integer premisesId) {
        return hierarchyList.stream()
                .filter(rate -> {
                    switch (rate.getHierarchyLevel()) {
                        case GENERAL:
                            return true;
                        case COOPERATIVE:
                            return rate.getCooperativeId().equals(cooperativeId);
                        case BUILDING:
                            return rate.getBuildingId().equals(buildingId);
                        case PREMISES:
                            return rate.getPremisesId().equals(premisesId);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
