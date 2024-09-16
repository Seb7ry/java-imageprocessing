package filtros;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class Filter_Negative2 implements PlugInFilter {
    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_8G; //Se indica para que solo se ejecute bajo imágenes en grises.
    }

    @Override
    public void run(ImageProcessor ip) {
        int height = ip.getHeight();
        int width = ip.getWidth();
        byte[] pixels = (byte[])ip.getPixels();
        
        for(int i=0; i<(height*width); i++){
            pixels[i]=(byte)(255-pixels[i]);
            imp.updateAndDraw();
        }
    }

}
