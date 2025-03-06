package com.volkov.weather.demo.mapper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DoubleRounder {

    public double round(double value, int scale) {

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
