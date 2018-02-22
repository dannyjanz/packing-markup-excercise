package pricing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PackingMarkupCalculatorTest implements MoneyCalculator {

    @Test
    public void testCalculate() {
        String expectedBehaviour = "Calculate should add the flat markup to the base price and then apply an additional " +
                "markup for the amount of people required and the material to the base price plus the flat markup";

        PackingMarkupCalculator calculator = new PackingMarkupCalculator(
                new Markup(5),
                new Markup(1.2f));

        assertEquals(expectedBehaviour, resultPrecision(1591.58),
                calculator.calculate(calculationPrecision(1299.99), 3, MaterialMarkup.FOOD));

        assertEquals(expectedBehaviour, resultPrecision(6199.81),
                calculator.calculate(calculationPrecision(5432.00), 1, MaterialMarkup.PHARMACEUTICALS));

        assertEquals(expectedBehaviour, resultPrecision(13707.63),
                calculator.calculate(calculationPrecision(12456.95), 4, MaterialMarkup.OTHER));

    }

}
