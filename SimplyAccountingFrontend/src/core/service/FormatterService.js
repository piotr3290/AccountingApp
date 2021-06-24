import {toString, trim} from 'lodash'

export const currency = (value) => {
    return new Intl.NumberFormat(
        'pl-PL',
        {
            style: 'currency',
            currency: 'PLN'
        }).format(value);
};

export const fixedCurrency = (value) => {
    return (toString(value) || null) != null
        ? Number(Math.round(parseFloat(value + 'e' + 2)) + 'e-' + 2).toFixed(2)
        : null;
};

export const emptyToNull = (value) => {
    return trim(toString(value)) || null;
};