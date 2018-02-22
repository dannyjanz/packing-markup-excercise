package pricing;

import java.math.BigDecimal;

public class PackingMarkupCalculator implements MoneyCalculator {

    private final Markup flatMarkup;
    private final Markup basePersonnelMarkup;

    public PackingMarkupCalculator(Markup flatMarkup, Markup basePersonnelMarkup) {
        this.flatMarkup = flatMarkup;
        this.basePersonnelMarkup = basePersonnelMarkup;
    }

    public BigDecimal calculate(BigDecimal price, int numberOfPeople, MaterialMarkup materialMarkup) {

        if (numberOfPeople < 0) {
            throw new IllegalArgumentException("Negative amount of Personnel not allowed for packing markup calculation");
        }

        return flatMarkup
                .andThen(basePersonnelMarkup.times(numberOfPeople).plus(materialMarkup.markup()))
                .andThen(RoundToFinalPrice).apply(price);

    }

}
