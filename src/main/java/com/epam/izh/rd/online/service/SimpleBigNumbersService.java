package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal aBD = new BigDecimal(a);
        BigDecimal bBD = new BigDecimal(b);

        return aBD.divide(bBD, range, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int count = 0;
        int primaryNumber = 2;

        while (count != range) {
            primaryNumber += 1;
            if (isPrimary(primaryNumber)) {
                count++;
            }
        }

        return new BigInteger(String.valueOf(primaryNumber));
    }

    boolean isPrimary(int number) throws IllegalArgumentException {
        if (number < 2) {
            throw  new IllegalArgumentException("Number must be more than 2");
        }

        boolean result = true;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                result = false;
                break;
            }
        }

        return result;
    }
}
