package client.plot;

import client.plot.chart.JavaFXChartFactory;
import javafx.scene.SubScene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.util.List;


public class SurfaceRenderer {
    private static ImageView imageView;
    private static AWTChart chart;
    private static JavaFXChartFactory factory;

    private static Coord3d viewPoint = new Coord3d(1, 1, 1);

    private SurfaceRenderer(){}

    public static void renderSurface(SubScene subScene, Pane pane, List<Coord3d> points) {
        render(subScene, pane, points, null);
    }

    public static void renderZBoundedSurface(SubScene subScene, Pane pane, List<Coord3d> points, Float zMax) {
        render(subScene, pane, points, zMax);
    }

    private static void render(SubScene subScene, Pane pane, List<Coord3d> points, Float zMax) {
        factory = new JavaFXChartFactory();

        pane.getChildren().remove(imageView);

        chart = getDemoChart(factory, points);
        chart.getView().setViewPoint(viewPoint);

        if (zMax != null) {
            chart.getView().getBounds().setZmax(zMax);
        }

        imageView = factory.bindImageView(chart);

        pane.getChildren().add(imageView);

        factory.addSceneSizeChangedListener(chart, subScene);
        factory.resetSize(chart, subScene.getWidth() * 0.9, subScene.getHeight());

        chart.getView().addViewPointChangedListener((observable) -> saveCurrentViewPoint());
    }

    private static void saveCurrentViewPoint() {
        viewPoint = chart.getView().getViewPoint();
    }

    private static AWTChart getDemoChart(JavaFXChartFactory factory, List<Coord3d> points) {
        final String toolkit = "offscreen";

        Shape surface = Builder.buildDelaunay(points);

        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(),
                surface.getBounds().getZmax(),
                new Color(1, 1, 1, .5f)));

        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        Quality quality = Quality.Advanced;
        quality.setSmoothPolygon(true);
        quality.setAnimated(true);

        AWTChart chart = (AWTChart) factory.newChart(quality, toolkit);
        chart.getScene().getGraph().add(surface);
        return chart;
    }
}