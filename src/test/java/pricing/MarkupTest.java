package pricing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MarkupTest implements MoneyCalculator {

    private Markup markup = new Markup(12.5f);

    @Test
    public void testApply() {
        assertEquals("Result of apply should be the initial value increased by the Markups percent value",
                calculationPrecision(56.25), markup.apply(calculationPrecision(50)));
    }

    @Test
    public void testPlus() {
        assertEquals("Plus should result in a new Markup with the other Markups value added to " +
                        "the original Markups percent value",
                calculationPrecision(125.0), markup.plus(markup).apply(calculationPrecision(100)));

        testThatOriginalMarkupIsUnchanged();
    }

    @Test
    public void testTimes() {
        assertEquals("Times should result in a new Markup with original percent value multiplied by the passed value",
                calculationPrecision(125.0), markup.times(2).apply(calculationPrecision(100.0)));

        testThatOriginalMarkupIsUnchanged();
    }

    private void testThatOriginalMarkupIsUnchanged() {
        assertEquals("The original Markup should remain unchanged",
                calculationPrecision(112.5), markup.apply(calculationPrecision(100.0)));
    }

}
