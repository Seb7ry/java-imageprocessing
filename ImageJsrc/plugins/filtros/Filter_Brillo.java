package filtros;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class Filter_Brillo implements PlugInFilter {
    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_ALL;
    }

    @Override
    public void run(ImageProcessor ip) {
        int height = ip.getHeight();
        int width = ip.getWidth();
        int pixel;
        byte[] pixels = (byte[])ip.getPixels();
        
        for(int i=0; i<(height*width); i++){
            /* pixel = (pixels[i] & 0xff) - 50;
            if(pixel > 255) {
                pixels[i]=(byte)(255);
            } else if(pixel < 0) {
                pixels[i]=(byte)(0);
            } else {
                pixels[i] = (byte)pixel;
            }*/
            
            pixel = (pixels[i] & 0xff) - 50;
            pixels[i] = (byte) ((pixel>255)? 255: (pixel<0)? 0: pixel);
            
            imp.updateAndDraw();
        }
    }

}
