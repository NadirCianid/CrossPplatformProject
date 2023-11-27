package client.plot;

import client.dto.Point;
import org.math.plot.Plot3DPanel;

import javax.swing.*;
import java.util.List;

public class PlotMaker extends JFrame {

    private final static String TITLE = "Данные";

    private PlotMaker(List<Point> points, long t) {
        super(TITLE + " для t = " + t);

        Plot3DPanel plot = new Plot3DPanel();

        int pointsSize = points.size();

        double[][] data = new double[pointsSize][3];

        for (int i = 0; i < pointsSize; i++) {
            data[i][0] = points.get(i).getXValue();
            data[i][1] = points.get(i).getYValue();
            data[i][2] = points.get(i).getZValue();
        }


        plot.addScatterPlot(TITLE, data);

        setContentPane(plot);
    }

    public static void showChart(List<Point> points, long t) {
        Thread thread = new Thread(() -> SwingUtilities.invokeLater(() -> {
            PlotMaker plotMaker = new PlotMaker(points, t);
            plotMaker.setSize(800, 600);
            plotMaker.setLocationRelativeTo(null);
            plotMaker.setVisible(true);
        }));

        thread.start();
    }


}
