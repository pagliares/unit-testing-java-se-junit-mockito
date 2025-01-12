package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Structure I used to define the method names aiming to
 * ensure uniformity and readability:
 * test[Method]_[Condition]_[ExpectedBehavior]
 * .
 */
@DisplayName("Report with results of testing calculator mathematical operations")
class CalculatorTest {

    private Calculator calculator;

    @BeforeAll
    // The method to be executed before all others must be static!
    static void setUp() {
        System.out.println("Executing setup method annotated with @BeforeAll");
    }

    @AfterAll
    // The method to be executed after all others must be static!
    static void cleanUp() {
        System.out.println("Executing cleanUp method annotated with @AfterAll");
    }

    @BeforeEach
    void beforeEachTestMethod() {
        calculator = new Calculator();
        System.out.println("Executing beforeEachTestMethod annotated with @BeforeEach");
    }

    @AfterEach
    void afterEachTestMethod() {
        System.out.println("Executing afterEachTestMethod annotated with @AfterEach");
    }

    @Test
    @DisplayName("Test 15/3 = 5")
    void testPerformIntegerDivision_WhenFifteenIsDividedByThree_ShouldReturnFive(){
        // Two widely used code structure schemas to implement the tests:
        //   1. Arrange - Act - Assert (AAA)
        //   2. Given - When - Then (Gherkin Syntax)

        // Arrange (Given)
        int dividend = 15;
        int divisor = 3;
        int expectedResult = 5;

        // Act (When)
        int result = calculator.performIntegerDivision(15, 3);

        // Assert (Then)
        assertEquals(5, result, "15 divided by 3 did not return 5");
    }

    // Better disable than comment out the line with @Test since, disable still appears
    // In the test results
    // @Disabled
    @DisplayName("4/0")
    @Test
    void testPerformIntegerDivision_When_FourIsDividedByZero_ShouldThrowArithmeticException(){
        System.out.println("Test 4/0");

        // Arrange (Given)
        int dividend = 4;
        int divisor = 0;
        int expectedResult = 0;
        // This is the message (/ by zero) generated by the Java Runtime Environment
        // when we try to divide by zero
        String expectedExceptionMessage = "/ by zero";
        // Act (When) and Assert (Then)
        ArithmeticException actualException = assertThrows(ArithmeticException.class,
                () -> calculator.performIntegerDivision(dividend, divisor),
                "4/0 should throw ArithmeticException");

        // Assert (Then)
        assertEquals(expectedExceptionMessage, actualException.getMessage(), ()-> "Unexpected exception message");
    }

    @ParameterizedTest
    // If the name of the test case is equal the name of the static method that provide the
    // arguments (source of input parameters), we can remove this name provided as parameter
    // @MethodSource("integerSubtractionInputParameters")
    // @CsvSource({
    //             "5, 3, 2",
    //            "2, 3, -1",
    //            "1, 1, 0"
    //})

    //    private static Stream<Arguments> integerSubtractionInputParameters() {
    //        return Stream.of(
    //                Arguments.of(5, 3, 2),
    //                Arguments.of(2, 3, -1),
    //                Arguments.of(1, 1, 0)
    //        );
    //    }

    // How to use CsvSource with string arguments:
    // Example (notice the second pair uses an empty String representing an empty argument
    // @CsvSource({
    //        "Brasília, New York",
    //        "Paris, ''",
    // })

    // Below an example of how to use  @ParameterizedTest with @ValueSource annotation
    //    @ParameterizedTest
    //    @ValueSource(strings={"Tibet, Athens, Rome"})
    //    void demonstrateValueSource(String firstName){
    //        System.out.println(firstName);
    //        assertNotNull(firstName);
    //    }

    //  Remember:   The ExpectedBehavior in the convention of method names
    //  test[Method]_[Condition]_[ExpectedBehavior]
    //  can be helpful, but it's not always necessary,
    //  especially when the method name or the context already makes the expected behavior clear.
    //  This test case is an example

    /*
        Instead of using:
        assertEquals(expectedResult, result, minuend + " " + subtrahend + " did not return " + expectedResult);

        I prefer using  lambda expression that has better performance, since this third parameter
        is executed ONLY when the test fail:
        assertEquals(expectedResult, result, minuend + " " + subtrahend + " did not return " + expectedResult);
     */

    @CsvFileSource(resources = "/inputParametersPerformIntegerSubtraction.csv")
    @DisplayName("Test integer subtraction [minuend, subtrahend, expectedResult]")
    void testPerformIntegerSubtraction_WithCsvInput(int minuend, int subtrahend, int expectedResult){
        // Usually we don’t use System.out.println statements in Test cases.
        // In this and previous examples I used only for pedagogical reasons
        System.out.println("Running test " + minuend + " - " + subtrahend + "= " + expectedResult);
        Calculator calculator = new Calculator();

        int result = calculator.performIntegerSubtraction(minuend, subtrahend);
        assertEquals(expectedResult, result, () -> minuend + "-" + subtrahend + " did not return " + expectedResult);
    }
}