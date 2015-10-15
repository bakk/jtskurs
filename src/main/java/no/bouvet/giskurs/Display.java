package no.bouvet.giskurs;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.swing.styling.JSimpleStyleDialog;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;

public class Display {

    public static void createDisplay(Geometry... geometries) throws Exception {
        Display display = new Display();
        display.displayGeometries(geometries);
    }

    public static void main(String[] args) throws Exception {
        Display me = new Display();
        me.displayShapefile();
    }

    public void displayShapefile() throws Exception {
        File file = JFileDataStoreChooser.showOpenFile("shp", null);
        if (file == null) {
            return;
        }

        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        FeatureSource featureSource = store.getFeatureSource();
        display(featureSource);
    }

    public void displayGeometries(Geometry... geometries) throws Exception {
        FeatureSource featureSource = CreateMemoryFeatureSource(geometries);
        display(featureSource);
    }

    private void display(FeatureSource featureSource) throws Exception {
        MapContent map = new MapContent();
        map.setTitle("Giskurs");

        Style style = createStyle(featureSource);

        Layer layer = new FeatureLayer(featureSource, style);
        map.addLayer(layer);

        JMapFrame.showMap(map);
    }

    private Style createStyle(FeatureSource featureSource) {
        SimpleFeatureType schema = (SimpleFeatureType)featureSource.getSchema();
        return JSimpleStyleDialog.showDialog(null, schema);
    }

    private FeatureSource CreateMemoryFeatureSource(Geometry[] geometries) throws IOException {

        String typename = "simple";
        SimpleFeatureTypeBuilder buildType = new SimpleFeatureTypeBuilder();
        buildType.add("geom", LineString.class);
        buildType.add("name", String.class);
        buildType.setName(typename);

        final SimpleFeatureType schema = buildType.buildFeatureType();
        final MemoryDataStore memory = new MemoryDataStore(schema);
        int i = 1;
        for(Geometry geom : geometries) {
            SimpleFeatureBuilder builder = new SimpleFeatureBuilder(schema);
            builder.add(geom);
            SimpleFeature newFeature = builder.buildFeature(""+i);
            memory.addFeature(newFeature);
            i++;
        }
        return memory.getFeatureSource(typename);
    }
}
