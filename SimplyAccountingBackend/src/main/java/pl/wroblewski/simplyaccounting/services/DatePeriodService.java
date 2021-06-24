package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.exceptions.IncorrectDateException;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;
import pl.wroblewski.simplyaccounting.utils.DateUtilsComponent;

import java.util.Collection;


@Service
@AllArgsConstructor
public class DatePeriodService {

    private final DateUtilsComponent dateUtilsComponent;

    @SuppressWarnings("rawtypes")
    public void save(Collection<? extends IDatePeriod> datePeriodEntities,
                     IDatePeriod newDatePeriod, JpaRepository jpaRepository) {
        if (datePeriodEntities.size() == 0) {
            jpaRepository.saveAndFlush(newDatePeriod);
            return;
        }

        if (newDatePeriod.getStartDate() == null && newDatePeriod.getEndDate() == null) {
            throw new IncorrectDateException("Incorrect dates");
        }

        if (newDatePeriod.getStartDate() == null) {

            var earliestDatePeriod = dateUtilsComponent.findEarliestPeriod(datePeriodEntities);

            if (earliestDatePeriod.getEndDate() != null && !earliestDatePeriod.getEndDate().isAfter(newDatePeriod.getEndDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

            if (earliestDatePeriod.getStartDate() == null) {
                earliestDatePeriod.setStartDate(newDatePeriod.getEndDate().plusDays(1));
                jpaRepository.saveAndFlush(earliestDatePeriod);
            } else if (!earliestDatePeriod.getStartDate().isAfter(newDatePeriod.getEndDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

        } else if (newDatePeriod.getEndDate() == null) {

            var latestDatePeriod = dateUtilsComponent.findLatestPeriod(datePeriodEntities);

            if (latestDatePeriod.getStartDate() != null && !latestDatePeriod.getStartDate().isBefore(newDatePeriod.getStartDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

            if (latestDatePeriod.getEndDate() == null) {
                latestDatePeriod.setEndDate(newDatePeriod.getStartDate().minusDays(1));
                jpaRepository.saveAndFlush(latestDatePeriod);
            } else if (!latestDatePeriod.getEndDate().isBefore(newDatePeriod.getStartDate())) {
                throw new IncorrectDateException("Incorrect dates.");
            }

        } else {
            checkPeopleNumbersOverlapping(datePeriodEntities, newDatePeriod);
        }
        jpaRepository.saveAndFlush(newDatePeriod);
    }

    private void checkPeopleNumbersOverlapping(Collection<? extends IDatePeriod> datePeriodEntities,
                                               IDatePeriod newDatePeriod) {
        datePeriodEntities.forEach(o -> {
            if (dateUtilsComponent.areNullablePeriodsOverlapping(o.getStartDate(), o.getEndDate(),
                    newDatePeriod.getStartDate(), newDatePeriod.getEndDate()))
                throw new IncorrectDateException("Incorrect dates.");
        });
    }

}
