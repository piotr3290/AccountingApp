package pl.wroblewski.simplyaccounting.utils;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class NumberUtilsComponent {

    private final int CURRENCY_DECIMAL_PLACES = 2;

    public double roundDoubleToCurrency(double value) {
        return roundDoubleToDecimalPlaces(value, CURRENCY_DECIMAL_PLACES);
    }

    public double roundDoubleToDecimalPlaces(double value, int decimalPlaces) {

        String format = "0";
        if (decimalPlaces > 0) {
            format = format + "." + format.repeat(decimalPlaces);
        }

        DecimalFormat decimalFormat = new DecimalFormat(format);

        return Double.parseDouble(decimalFormat.format(value).replace(',', '.'));
    }
}
