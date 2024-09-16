package filtros;

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class Filter_Bright implements PlugInFilter {
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
        int a,y;
        
        byte[] pixels = (byte[])ip.getPixels();
        int[][] pixelsM = new int[height][width];
        
        for(a=y=0;y < height; y++){
            for(int x=0; x < width; x++){
                pixelsM[y][x] = pixels[a++] & 0xff;
            }
        }
        
        for(a=y=0;y < height; y++){
            for(int x=0; x < width; x++){
                int pixel = pixelsM[y][x] + 50;
                pixelsM[y][x] = (byte) ((pixel>255)? 255: (pixel<0)? 0: pixel);
            }
        }
        
        for(a=y=0;y < height; y++){
            for(int x=0; x < width; x++){
                pixels[a++] = (byte) pixelsM[y][x];
            }
        }
        imp.updateAndDraw();
    }
}
