package pl.wroblewski.simplyaccounting.utils;

import org.springframework.stereotype.Component;

@Component
public class LogicUtilsComponent {

    public boolean maxOneIsTrue(boolean... variables) {
        boolean isAnyTrue = false;
        for (boolean variable : variables) {
            if (variable && isAnyTrue) {
                return false;
            }
            if (variable) {
                isAnyTrue = true;
            }
        }
        return true;
    }
}
