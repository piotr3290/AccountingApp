export const monthYearDateString = (year, month) => {
    if ((year || null) != null && (month || null) != null) {
        return new Date(year, month).toISOString().substr(0, 7);
    } else {
        return null;
    }
}