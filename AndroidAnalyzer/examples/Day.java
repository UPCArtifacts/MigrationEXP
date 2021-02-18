package com.example.enums;

import static java.lang.System.out;

/**
 * An {@code enum} type. Implicitly extends {@code java.lang.Enum}.
 */
public enum Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;   // When there are fields or methods, the list of enum constants must end with a semicolon.

    /**
     * {@code main} method. Declared here for simplicity.
     *
     * @param args  command-line arguments (ignored in this example)
     */
    public static void main(String ... args) {

        /*
         *  The compiler automatically adds some special methods when it creates
         *  an enum. For example, they have a static values method that returns
         *  an array containing all of the values of the enum in the order they
         *  are declared.
         */
        for (Day day : Day.values()) {

            switch (day) {
                case SATURDAY:
                case SUNDAY:

                    /**
                     * "day.name()" and "day.toString()" would yield the same result here.
                     */
                    out.printf("%s is a weekend day.\n", day);
                    break;

                case MONDAY:
                case TUESDAY:
                case WEDNESDAY:
                case THURSDAY:
                case FRIDAY:
                    out.printf("%s is a weekday.\n", day);
                    break;
            }
        }
    } // main(String [])
} // Day