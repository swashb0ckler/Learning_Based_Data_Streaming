/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package code;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppTest {

    @Test
    public void stringShouldBecomeStringAgain(){

        String originalString = "Hello my name is... slim shady!";
        BigInteger intForAlgorithms = TypeConverter.stringToBigInteger(originalString);
        String recreatedString = TypeConverter.bigIntegerToString(intForAlgorithms);
        System.out.println("Original String : " + originalString);
        System.out.println("recreated: " + recreatedString);

        assertThat(originalString, is(recreatedString));

    }




}
