/*
 * Copyright [2013] [Eric Poitras]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package pragmatic.data.adapters.adapters;

import junit.framework.Assert;
import org.junit.Test;
import pragmatic.data.Adapter;
import pragmatic.data.adapters.ObjectAdapters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Test the Object to Number adapters.
 */
public class TestObjectToNumbersAdapters {

    /**
     * Test that number conversion works fine.
     */
    private <T extends Number> void testObjectToNumberAdaptation( Adapter<Object, T> adapter, Number numberValue, T expected ) {
        BigDecimal value = new BigDecimal( numberValue.toString() );

        Assert.assertEquals( adapter.adapt( value.toString() ), expected );
        if ( value.unscaledValue().toString().length() <= 9 ) {
            Assert.assertEquals( adapter.adapt( value.floatValue() ), expected );
        }
        if ( value.unscaledValue().toString().length() <= 18 ) {
            Assert.assertEquals( adapter.adapt( value.doubleValue() ), expected );
        }
        Assert.assertEquals( adapter.adapt( new BigDecimal( value.toString() ) ), expected );
        if ( !( numberValue instanceof Float || numberValue instanceof Double ) ) {
            Assert.assertEquals( adapter.adapt( new BigInteger( value.toString() ) ), expected );
        }
        if ( value.compareTo( BigDecimal.valueOf( Long.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Long.MAX_VALUE ) ) <= 0 ) {
            Assert.assertEquals( adapter.adapt( value.longValue() ), expected );
            Assert.assertEquals( adapter.adapt( new AtomicLong( value.longValue() ) ), expected );
        }
        if ( value.compareTo( BigDecimal.valueOf( Integer.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Integer.MAX_VALUE ) ) <= 0 ) {
            Assert.assertEquals( adapter.adapt( value.intValue() ), expected );
            Assert.assertEquals( adapter.adapt( new AtomicInteger( value.intValue() ) ), expected );
        }
        if ( value.compareTo( BigDecimal.valueOf( Short.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Short.MAX_VALUE ) ) <= 0 ) {
            Assert.assertEquals( adapter.adapt( value.shortValue() ), expected );
        }
        if ( value.compareTo( BigDecimal.valueOf( Byte.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Byte.MAX_VALUE ) ) <= 0 ) {
            Assert.assertEquals( adapter.adapt( value.byteValue() ), expected );
        }

    }

    private <T extends Number> boolean testOneNumberAdaptationFailure( Adapter<Object, T> adapter, Object value ) {
        try {
            adapter.adapt( value );
        } catch ( Exception e ) {
            return true;
        }
        return false;
    }

    private <T extends Number> void testNumberAdaptationFailure( Adapter<Object, T> adapter, Number numberValue ) {
        BigDecimal value = new BigDecimal( numberValue.toString() );

        Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.toString() ) );
        if ( value.unscaledValue().toString().length() < 18 ) {
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.doubleValue() ) );
        }
        if ( value.unscaledValue().toString().length() < 9 ) {
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.floatValue() ) );
        }
        Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value ) );
        Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.toBigInteger() ) );
        if ( value.compareTo( BigDecimal.valueOf( Long.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Long.MAX_VALUE ) ) <= 0 ) {
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.longValue() ) );
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, new AtomicLong( value.longValue() ) ) );
        }
        if ( value.compareTo( BigDecimal.valueOf( Integer.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Integer.MAX_VALUE ) ) <= 0 ) {
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.intValue() ) );
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, new AtomicInteger( value.intValue() ) ) );
        }
        if ( value.compareTo( BigDecimal.valueOf( Short.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Short.MAX_VALUE ) ) <= 0 ) {
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.shortValue() ) );
        }
        if ( value.compareTo( BigDecimal.valueOf( Byte.MIN_VALUE ) ) >= 0 && value.compareTo( BigDecimal.valueOf( Byte.MAX_VALUE ) ) <= 0 ) {
            Assert.assertTrue( testOneNumberAdaptationFailure( adapter, value.byteValue() ) );
        }
    }


    @Test
    public void testObjectToByteAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.BYTE, Byte.MIN_VALUE, Byte.MIN_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.BYTE, Byte.MAX_VALUE, Byte.MAX_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.BYTE, (byte) 0, (byte) 0 );
        testNumberAdaptationFailure( ObjectAdapters.BYTE, -500 );

    }

    @Test
    public void testObjectToShortAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.SHORT, Short.MIN_VALUE, Short.MIN_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.SHORT, Short.MAX_VALUE, Short.MAX_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.SHORT, (short) 0, (short) 0 );
        testNumberAdaptationFailure( ObjectAdapters.SHORT, -500000 );

    }

    @Test
    public void testObjectToIntegerAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.INTEGER, Integer.MIN_VALUE, Integer.MIN_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.INTEGER, Integer.MAX_VALUE, Integer.MAX_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.INTEGER, 0, 0 );
        testNumberAdaptationFailure( ObjectAdapters.INTEGER, Long.MIN_VALUE );

    }

    @Test
    public void testObjectToLongAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.LONG, Long.MIN_VALUE, Long.MIN_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.LONG, Long.MAX_VALUE, Long.MAX_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.LONG, 0L, 0L );
        testNumberAdaptationFailure( ObjectAdapters.LONG, new BigDecimal( Long.MAX_VALUE ).add( BigDecimal.ONE ) );

    }

    @Test
    public void testObjectToFloatAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.FLOAT, (float) Long.MIN_VALUE, (float) Long.MIN_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.FLOAT, (float) Long.MAX_VALUE, (float) Long.MAX_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.FLOAT, 0f, 0f );
        testObjectToNumberAdaptation( ObjectAdapters.FLOAT, new BigDecimal( Long.MAX_VALUE ).add( BigDecimal.ONE ), new BigDecimal( Long.MAX_VALUE ).add( BigDecimal.ONE ).floatValue() );

    }

    @Test
    public void testObjectToDoubleAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.DOUBLE, (double) Long.MIN_VALUE, (double) Long.MIN_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.DOUBLE, (double) Long.MAX_VALUE, (double) Long.MAX_VALUE );
        testObjectToNumberAdaptation( ObjectAdapters.DOUBLE, 0d, 0d );
        testObjectToNumberAdaptation( ObjectAdapters.DOUBLE, new BigDecimal( Long.MAX_VALUE ).add( BigDecimal.ONE ), new BigDecimal( Long.MAX_VALUE ).add( BigDecimal.ONE ).doubleValue() );

    }

    @Test
    public void testObjectToBigDecimalAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.BIG_DECIMAL, BigDecimal.ONE, BigDecimal.ONE );
        testObjectToNumberAdaptation( ObjectAdapters.BIG_DECIMAL, BigDecimal.ONE.negate(), BigDecimal.ONE.negate() );
        testObjectToNumberAdaptation( ObjectAdapters.BIG_DECIMAL, BigDecimal.ZERO, BigDecimal.ZERO );

    }

    @Test
    public void testObjectToBigIntegerAdapter() {

        testObjectToNumberAdaptation( ObjectAdapters.BIG_INTEGER, BigInteger.ONE, BigInteger.ONE );
        testObjectToNumberAdaptation( ObjectAdapters.BIG_INTEGER, BigInteger.ONE.negate(), BigInteger.ONE.negate() );
        testObjectToNumberAdaptation( ObjectAdapters.BIG_INTEGER, BigInteger.ZERO, BigInteger.ZERO );

    }

    @Test
    public void testBigDecimalScientificNotation() throws Exception {
        String test = "123123E-100";
        BigDecimal bdvalue = ObjectAdapters.BIG_DECIMAL.adapt( test );
        Double dvalue = ObjectAdapters.DOUBLE.adapt( test );



    }
}
