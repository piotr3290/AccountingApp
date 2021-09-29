package pl.wroblewski.simplyaccounting.interfaces;

import java.time.LocalDate;

public interface IDatePeriod {

    LocalDate getStartDate();

    LocalDate getEndDate();

    void setStartDate(LocalDate startDate);

    void setEndDate(LocalDate endDate);
}
