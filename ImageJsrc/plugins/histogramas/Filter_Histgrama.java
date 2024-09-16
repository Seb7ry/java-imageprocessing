package histogramas;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class Filter_Histgrama implements PlugInFilter {
    ImagePlus imp;

    @Override
    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_8G;
    }

@Override
    public void run(ImageProcessor ip) {
        int pixel ;
        int height = ip.getHeight();
        int width = ip.getWidth();
        int [] histograma = new int [256];
        byte[] pixels = (byte[])ip.getPixels();
        for (int a=0 ; a<height*width ; histograma [pixels[a++]&0xff] ++)
        imp.updateAndDraw();
    }
}

