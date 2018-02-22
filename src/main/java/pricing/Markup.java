package pricing;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public class Markup implements UnaryOperator<BigDecimal>, MoneyCalculator {

    private BigDecimal markupPercent;

    public Markup(double markupPercent) {
        this.markupPercent = calculationPrecision(markupPercent);
    }

    public Markup(BigDecimal markupPercent) {
        this.markupPercent = markupPercent;
    }

    @Override
    public BigDecimal apply(BigDecimal price) {
        return price.add(percentageOf(price, markupPercent));
    }

    public Markup times(int amount) {
        return new Markup(markupPercent.multiply(calculationPrecision(amount)));
    }

    public Markup plus(Markup additionalMarkup) {
        return new Markup(markupPercent.add(additionalMarkup.markupPercent));
    }

}