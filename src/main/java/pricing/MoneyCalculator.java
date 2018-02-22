package pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

public interface MoneyCalculator {

    RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    int RESULT_PRECISION = 2;
    int CALCAULATION_PRECISION = 8;

    BigDecimal ONE_HUNDRET = new BigDecimal(100);
    BigDecimal ONE = new BigDecimal(1);

    UnaryOperator<BigDecimal> RoundToFinalPrice = price -> price.setScale(RESULT_PRECISION, ROUNDING_MODE);

    default BigDecimal percentageOf(BigDecimal price, BigDecimal percent) {
        return price.multiply(percent).divide(ONE_HUNDRET, CALCAULATION_PRECISION, ROUNDING_MODE);
    }

    default BigDecimal calculationPrecision(double value) {
        return new BigDecimal(value).setScale(CALCAULATION_PRECISION, ROUNDING_MODE);
    }

    default BigDecimal calculationPrecision(int value) {
        return new BigDecimal(value).setScale(CALCAULATION_PRECISION, ROUNDING_MODE);
    }

    default BigDecimal resultPrecision(double value) {
        return new BigDecimal(value).setScale(RESULT_PRECISION, ROUNDING_MODE);
    }

}
