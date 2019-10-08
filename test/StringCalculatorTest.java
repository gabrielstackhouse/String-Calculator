import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringCalculatorTest {

    // Instance variables
    private static StringCalculator calculator;

    /**
     * Runs before all other tests are run, creating a StringCalculator to use
     */
    @BeforeClass
    public static void setUp() {
        calculator = new StringCalculator();
    }

    /**
     * Rule for testing thrown exceptions and their messages
     */
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    /**
     * Test simple change of delimiter to a single character
     */
    @Test
    public void addChangeDelimiter() {
        assertEquals(3, calculator.add("//;\n1;2"));
        assertEquals(12, calculator.add("//&\n1&9&2"));
    }

    /**
     * Test changing a delimiter to longer than one character
     */
    @Test
    public void addChangeDelimiterAnyLength() {
        assertEquals(6, calculator.add("//[***]\n1***2***3"));
        assertEquals(7, calculator.add("//[####]\n4####2####1"));
    }

    /**
     * Test adding multiple delimiters
     */
    @Test
    public void addChangeDelimiterMultiple() {
        assertEquals(6, calculator.add("//[*][%]\n1*2%3"));
    }

    /**
     * Test adding multiple delimiters of various lengths
     */
    @Test
    public void addChangeDelimiterMultipleLongerLengths() {
        assertEquals(6, calculator.add("//[***][%%]\n1***2%%3"));
        assertEquals(10, calculator.add("//[^^^][@][$$$$]\n1@2$$$$3^^^4"));
    }

    /**
     * Test adding delimiters where some need backslashes and some don't
     */
    public void addDelimiterWithAndWithoutBackslashes() {
        assertEquals(6, calculator.add("//[^][@]^\n1^2@3"));
        assertEquals(6, calculator.add("//[^^][@@@]^\n1^^2@@@3"));
        assertEquals(10, calculator.add("//[^^][@@@][....]^\n1^^2@@@3....4"));
    }

    /**
     * Test that delimiters needing backslashes function correctly
     */
    @Test
    public void addDelimiterWithBackslashes() {
        assertEquals(6, calculator.add("//^\n1^2^3"));
        assertEquals(6, calculator.add("//.\n1.2.3"));
        assertEquals(6, calculator.add("//$\n1$2$3"));
        assertEquals(6, calculator.add("//(\n1(2(3"));
        assertEquals(6, calculator.add("//)\n1)2)3"));
        assertEquals(6, calculator.add("//|\n1|2|3"));
        assertEquals(6, calculator.add("//*\n1*2*3"));
        assertEquals(6, calculator.add("//+\n1+2+3"));
        assertEquals(6, calculator.add("//?\n1?2?3"));
        assertEquals(6, calculator.add("//{\n1{2{3"));
        assertEquals(6, calculator.add("//\\\n1\\2\\3"));
    }

    /**
     * Test adding multiple delimiters needing backslashes
     */
    @Test
    public void addDelimiterWithBackslashesMultiple() {
        assertEquals(6, calculator.add("//[^][.]^\n1^2.3"));
        assertEquals(6, calculator.add("//[^^][...]^\n1^^2...3"));
        assertEquals(10, calculator.add("//[^^][....][$$$]^\n1^^2$$$3....4"));
    }

    /**
     * Test passing an empty string into add()
     */
    @Test
    public void addEmptyString() {
        assertEquals(0, calculator.add(""));
    }

    /**
     * Test that numbers larger than 1000 are ignored
     */
    @Test
    public void addIgnoreValuesLargerThan1000() {
        assertEquals(1002, calculator.add("2,1000"));
        assertEquals(2, calculator.add("2,1001"));
        assertEquals(5, calculator.add("2,2306,3"));
    }

    /**
     * Test that both commas and new lines can be used interchangeably as
     * delimiters
     */
    @Test
    public void addUseCommaAndNewLineDelimiters() {
        assertEquals(6, calculator.add("1,2,3"));
        assertEquals(6, calculator.add("1\n2\n3"));
        assertEquals(6, calculator.add("1,2\n3"));
    }

    /**
     * Test adding multiple numbers
     */
    @Test
    public void addMultipleNumbers() {
        assertEquals(11, calculator.add("1,4,3,2,1"));
        assertEquals(8, calculator.add("4,2,1,1"));
        assertEquals(15, calculator.add("1,11,3"));
        assertEquals(15, calculator.add("1,2,1,2,1,2,1,2,1,2"));
    }

    /**
     * Test that an exception is thrown when a negative number is included, and
     * the correct message is displayed to the user
     */
    @Test
    public void addNegativeNumber() {
        // Define rules
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("negatives not allowed: [-4]");

        // This should throw an IllegalArgumentException
        calculator.add("1,-4,7");
    }

    /**
     * Test that an exception is thrown when negative numbers are included, and
     * the correct message is displayed to the user
     */
    @Test
    public void addNegativeNumbers2() {
        // Define rules
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("negatives not allowed: [-3, -10, -21]");

        // This should throw an IllegalArgumentException
        calculator.add("1,2,-3,5,-10,-21");
    }

    /**
     * Test passing one number into add()
     */
    @Test
    public void addOneNumber() {
        assertEquals(0, calculator.add("0"));
        assertEquals(1, calculator.add("1"));
        assertEquals(157, calculator.add("157"));
    }

    /**
     * Test adding two numbers
     */
    @Test
    public void addTwoNumbers() {
        assertEquals(1, calculator.add("0,1"));
        assertEquals(3, calculator.add("1,2"));
        assertEquals(11, calculator.add("3,8"));
    }
}
