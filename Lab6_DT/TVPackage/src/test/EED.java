package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import sqa.main.TVPlan;

class EED {

	@ParameterizedTest
    @CsvFileSource(resources = "/EED.csv", numLinesToSkip = 1)
    void testByEC(String rule, String ec1, String ec2, String ec3, double expected) throws Exception {
        TVPlan.TVPackage tvPackage = ec1.equalsIgnoreCase("NONE") ? null : TVPlan.TVPackage.valueOf(ec1);
        boolean offline = false, live = false;
        switch (ec2.toLowerCase()) {
            case "offline": offline = true; break;
            case "live": live = true; break;
            case "both": offline = true; live = true; break;
                   }
        boolean discount = ec3.equalsIgnoreCase("Y");

        var plan = new TVPlan(offline, live, discount);

        double actual;
        if (tvPackage == null) {
            actual = 0.0;
        } else {
            Method method = TVPlan.class.getDeclaredMethod("pricePerMonth", TVPlan.TVPackage.class);
            method.setAccessible(true);
            actual = (double) method.invoke(plan, tvPackage);
        }

        Assertions.assertEquals(expected, actual, 0.001,
                String.format("[%s] EC1=%s EC2=%s EC3=%s", rule, ec1, ec2, ec3));
    }

}
