package filtros;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import java.util.Random;

public class Filter_Impulsivo implements PlugInFilter {
    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor ip) {
        int height = ip.getHeight();
        int width = ip.getWidth();
        byte[] pixels = (byte[])ip.getPixels();
        
        Random random = new Random();
        double s = 5.0;
        double t = 1 - (s / 100.0);

        for (int i = 0; i < height * width; i++) {
            double a = -1 + 2 * random.nextDouble();
            
            if (a > t) {
                pixels[i] = (byte)255;
            } else if (a < -t) {
                pixels[i] = (byte)0;
            }
        }

        imp.updateAndDraw();
    }
}
