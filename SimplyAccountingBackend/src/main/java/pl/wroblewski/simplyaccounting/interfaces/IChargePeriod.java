package pl.wroblewski.simplyaccounting.interfaces;

public interface IChargePeriod extends IDatePeriod {

    Double getAmount();

    void setAmount(Double amount);
}
