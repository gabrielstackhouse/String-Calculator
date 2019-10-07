import java.util.ArrayList;

/**
 * A String Calculator class, created in accordance to documentation of String
 * Calculator Kata found here: https://osherove.com/tdd-kata-1/
 *
 * @author Gabriel Stackhouse
 */
public class StringCalculator {

    /**
     * Takes a string of numbers and optional user-defined delimiters, and adds
     * all numbers together.  Default delimiters are ',' and '\n'.
     *
     * Note: Accepted formats of numbers string can be found at the site linked
     *  above.
     * @param numbers - String of numbers and delimiters
     * @return - sum of all numbers in the string
     */
    public int add(String numbers) {

        // Check if string is empty
        if(numbers.isEmpty()) {
            return 0;
        }

        // Check for user-defined delimiter in string
        String delimiter = "";
        if(numbers.charAt(0) == '/' && numbers.charAt(1) == '/') {

            // Strip string of everything other than delimiter data
            String delimiterLine = numbers.substring(2).split("\n")[0];

            // Parse delimiterLine and put in correct format
            delimiter += "[";
            for(int i = 0; i < delimiterLine.length(); i++) {

                // If there is a bracket, we have a grouping delimiter
                if(delimiterLine.charAt(i) == '[') {
                    delimiter += '(';
                    i++;

                    // Iterate through until we reach next bracket. This ends
                    // our grouping
                    while(delimiterLine.charAt(i) != ']') {
                        delimiter += regexBackslashHelper(
                                delimiterLine.charAt(i));
                        i++;
                    }
                    delimiter += ')';
                }
                else {  // No bracket, so delimiter is a single character
                    delimiter += regexBackslashHelper(delimiterLine.charAt(i));
                }
            }
            delimiter += ']';
        }


        // Split into array based off chosen delimiter
        String split[];
        if(delimiter.isEmpty()) {
            // No delimiter defined, so use default
            split = numbers.split("[,\n]");
        }
        else {
            // Separate the first line that defines the delimiter
            String[] splitLines = numbers.split("\n");

            // Split the rest using the defined delimiter
            split = splitLines[1].split(delimiter);
        }

        // Iterate through array, adding each element
        int sum = 0;
        ArrayList<Integer> negatives = new ArrayList<Integer>();
        for(int i = 0; i < split.length; i++) {

            // Ignore empty strings or values greater than 1000
            if(split[i].isEmpty() || Integer.parseInt(split[i]) > 1000) {
                continue;
            }

            // Get current value
            int curValue = Integer.parseInt(split[i]);

            // Record any negative values, to print in exception later
            if(curValue < 0) {
                negatives.add(curValue);
            }
            else {  // Otherwise add
                sum += curValue;
            }
        }

        // Throw an exception if there are negative values included
        if(negatives.size() > 0) {
            throw new IllegalArgumentException("negatives not allowed: " +
                    negatives.toString());
        }

        // Return result
        return sum;
    }

    /**
     * Helper class for regex that adds backslashes for characters that require
     * it
     * @param delim - given delimiter
     * @return - given delimiter in correct format for regex
     */
    private String regexBackslashHelper(char delim) {
        switch(delim) {
            case '^':
            case '.':
            case '[':
            case '$':
            case '(':
            case ')':
            case '|':
            case '*':
            case '+':
            case '?':
            case '{':
            case '\\':
                return "\\" + delim;
            default:
                return Character.toString(delim);
        }
    }
}
