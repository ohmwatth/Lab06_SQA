package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import sqa.main.TVPlan;
import sqa.main.TVPlan.TVPackage;

class DecitionTable {

	@ParameterizedTest
    @CsvFileSource(resources = "/Decition.csv", numLinesToSkip = 1)
    void testDecition(String pkg, boolean offline, boolean live, boolean discount, double expectedPrice) throws Exception {

        TVPlan plan = new TVPlan(offline, live, discount);
        double actualPrice;

        if (pkg.equalsIgnoreCase("NONE")) {
            actualPrice = 0;
        } else {
            Method m = TVPlan.class.getDeclaredMethod("pricePerMonth", TVPackage.class);
            m.setAccessible(true);
            actualPrice = (double) m.invoke(plan, TVPackage.valueOf(pkg));
        }

        assertEquals(expectedPrice, actualPrice);
    }

}
