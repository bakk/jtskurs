package no.bouvet.giskurs;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileReader;

public class LinearTest {

    @Test
    @Ignore
    public void MotorwayTest() throws Exception {
        WKTReader reader = new WKTReader();
        String file = getClass().getResource("/veg.txt").getFile();
        Geometry geometry = reader.read(new FileReader(file));
        System.out.println(geometry.getNumPoints());
        Display.createDisplay(geometry);
    }

    public static void main(String[] args) throws Exception {
        LinearTest test = new LinearTest();
        test.MotorwayTest();
    }
}
