package pricing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MoneyCalculatorTest implements MoneyCalculator {

    @Test
    public void testRoundingToFinalPrice() {

        String expectedBehaviour = "Rounding to the final price should round to the nearest neighbour to " +
                "two decimal places, and .5 values should be rounded to the nearest even number";

        assertEquals(expectedBehaviour,
                resultPrecision(12.34), RoundToFinalPrice.apply(calculationPrecision(12.337)));

        assertEquals(expectedBehaviour,
                resultPrecision(12.32), RoundToFinalPrice.apply(calculationPrecision(12.325)));

    }

    @Test
    public void testPercantageOf() {

        String expectedBehaviour = "Calculating the percentage of a value should return the percentage according to the" +
                "provided percent value.";

        assertEquals(expectedBehaviour, calculationPrecision(12.5),
                percentageOf(calculationPrecision(100), calculationPrecision(12.5)));

        assertEquals(expectedBehaviour, calculationPrecision(0.153),
                percentageOf(calculationPrecision(12.75), calculationPrecision(1.2)));

    }

}
