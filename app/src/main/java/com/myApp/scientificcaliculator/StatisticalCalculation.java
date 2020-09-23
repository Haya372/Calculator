package com.myApp.scientificcaliculator;

public class StatisticalCalculation {
    String execute(String[] numbers){
        String res;
        try {
            double sum = 0;
            double len = 0;
            for (String num : numbers) {
                sum += Double.parseDouble(num);
                len++;
            }
            double mean = sum / len;
            double sum_square = 0;
            for (String num : numbers) {
                double n = Double.parseDouble(num);
                sum_square += (n - mean) * (n - mean);
            }
            double dispersion = sum_square / len;
            double std = Math.sqrt(dispersion);
            res = "mean:" + String.valueOf(mean) + " dispersion:" + String.valueOf(dispersion) + " sd:" + String.valueOf(std);
        }catch(Exception e){
            res="ERROR";
        }
        return res;
    }

}
