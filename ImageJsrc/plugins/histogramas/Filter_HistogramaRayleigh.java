package histogramas;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import javax.swing.*;

public class Filter_HistogramaRayleigh implements PlugInFilter {
    ImagePlus imp;

    private double alfa = 1.0;
    private int rmin = 0; 
    private int rmax = 255;

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
        byte[] pixels = (byte[]) ip.getPixels();
        
        for (int a = 0; a < height * width; histograma[pixels[a++] & 0xff]++);

        double[] p = new double[256];
        p[0] = histograma[0];
        for (int i = 1; i < 256; i++) {
            p[i] = p[i - 1] + histograma[i];
        }

        for (int i = 0; i < 256; i++) {
            p[i] /= (height * width);
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = ip.getPixel(x, y);
                double q = p[pixel]; 
                
                int newPixel = (int) (rmin + Math.sqrt(2 * Math.pow(alfa, 2) * Math.log(1 / (1 - q))));
                
                newPixel = Math.max(0, Math.min(255, newPixel));
                ip.putPixel(x, y, newPixel);
            }
        }

        imp.updateAndDraw();
    }

    private void showInterface() {
        JTextField rminField = new JTextField(Integer.toString(rmin));
        JTextField rmaxField = new JTextField(Integer.toString(rmax));
        JTextField alfaField = new JTextField(Double.toString(alfa));

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("rmin:"));
        panel.add(rminField);
        panel.add(new JLabel("rmax:"));
        panel.add(rmaxField);
        panel.add(new JLabel("alfa:"));
        panel.add(alfaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Parámetros de Ecualización de Rayleigh", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                rmin = Integer.parseInt(rminField.getText());
                rmax = Integer.parseInt(rmaxField.getText());
                alfa = Double.parseDouble(alfaField.getText());
            } catch (NumberFormatException e) {
                IJ.error("Valores no válidos. Se utilizarán los valores por defecto.");
            }
        }
    }
}
