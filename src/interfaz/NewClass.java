/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 *
 * @author Nomad
 */
public class NewClass {
    public static void main(String[] args) {
        float x=(float)2.13;
        BigDecimal number = new BigDecimal(x);
        BigDecimal number2 = new BigDecimal(-1);
BigDecimal divisor = new BigDecimal(4);
BigDecimal result = number.remainder(divisor);
BigDecimal result2 = number.divide(divisor);
double u =Double.parseDouble(result.multiply(result2).subtract(number).multiply(number2).toString());
//((2.13%4) x (2.13-4)) x -1
        System.out.println(u);
NumberFormat nf= NumberFormat.getInstance();
nf.setMaximumFractionDigits(2);
nf.setMinimumFractionDigits(2);
nf.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println("formato:"+nf.format(u));
double monto=(double)2.13%4;
float cuotas=(float)4;
float resultado=(float)monto/4;
System.out.println(nf.format(monto));
System.out.println(nf.format((monto)));

       

    }
}
