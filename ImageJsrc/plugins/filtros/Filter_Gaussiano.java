package filtros;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import java.util.Random;

public class Filter_Gaussiano implements PlugInFilter {
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
        double media = 0.0;
        double desviacion = 20.0; 

        for(int i=0; i<(height*width); i++){
         
            double ruido = media + desviacion * random.nextGaussian();
            int ruidoValor = (int)((pixels[i] & 0xff) + ruido);
            
            pixels[i] = (byte)ruidoValor;
        }

        imp.updateAndDraw(); 
    }
}
