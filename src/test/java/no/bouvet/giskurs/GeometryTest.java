package no.bouvet.giskurs;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.util.Assert;
import org.junit.Test;

public class GeometryTest {


    @Test
    public void CreateLine() throws Exception {
        WKTReader reader = new WKTReader();
        LineString line = (LineString)reader.read("LINESTRING(0 0, 10 10)");
        Assert.isTrue(line.isValid());
        LineString line2 = (LineString)reader.read("LINESTRING(5 5, 0 10)");
        Assert.isTrue(line.touches(line2));

        //Display.createDisplay(line,line2, reader.read("POINT(5 5)"));
    }

    public static void main(String[] args) throws Exception {
        GeometryTest geometryTest = new GeometryTest();
        geometryTest.CreateLine();
    }
}
