package com.canuto.general_app.finance.shared.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MoneyFormatter {
    
    private static final DecimalFormat formatter;

    static {

        DecimalFormatSymbols symbols =
                new DecimalFormatSymbols();

        symbols.setGroupingSeparator('.');

        formatter =
                new DecimalFormat("#,###");

        formatter.setDecimalFormatSymbols(
                symbols);
    }

    public static String format(
            BigDecimal amount
    ) {

        if (amount == null) {
            return "0";
        }

        return formatter.format(amount);
    }
}
