import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import javax.swing.*;

public class Filter_HistogramaLog implements PlugInFilter {
    ImagePlus imp;

    private double rmin = 1.0;
    private double rmax = 255.0;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        showInterface(); 
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor ip) {
        int height = ip.getHeight();
        int width = ip.getWidth();
        int[] histograma = new int[256];
        double[] Pq = new double[256];
        byte[] pixels = (byte[]) ip.getPixels();
        int numPixels = height * width;

        for (int i = 0; i < numPixels; histograma[pixels[i++] & 0xff]++);

        Pq[0] = histograma[0];
        for (int i = 1; i < 256; i++) {
            Pq[i] = Pq[i - 1] + histograma[i];
        }

        for (int i = 0; i < 256; i++) {
            Pq[i] /= numPixels;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = ip.getPixel(x, y);
                double q = Pq[pixel];

                double newPixel = rmin * Math.pow((rmax / rmin), q);
                
                newPixel = Math.max(0, Math.min(255, newPixel));

                ip.putPixel(x, y, (int) newPixel);
            }
        }

        imp.updateAndDraw();
    }

    private void showInterface() {
        JTextField rminField = new JTextField(Double.toString(rmin));
        JTextField rmaxField = new JTextField(Double.toString(rmax));

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("rmin:"));
        panel.add(rminField);
        panel.add(new JLabel("rmax:"));
        panel.add(rmaxField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Parámetros de transformación logarítmica", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                rmin = Double.parseDouble(rminField.getText());
                rmax = Double.parseDouble(rmaxField.getText());
            } catch (NumberFormatException e) {
                IJ.error("Valores no válidos. Se utilizarán los valores por defecto.");
            }
        }
    }
}
