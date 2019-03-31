package minimsoundapp;

import java.awt.Color;
import static processing.core.PApplet.map;

public class TwoDimensionSpectrum extends ComputeVisualSpectrum {
    
    @Override
    public void drawMatrix(){
        int fftLogSpectrumTotalLength = fftLog.avgSize();
        float saturation = 1.0f; //saturation
        float brightness = 1f; //brightness
        //n'afficher que la tempmatrix
        for(int i=0; i<(tempMatrix.length-1); i++){
            float color_input = (tempMatrix[i].x);
            float color_rescale = map(color_input, 0,fftLogSpectrumTotalLength*X_AXIS_SCALE, 0, 1);
            Color myRGBColor = Color.getHSBColor(color_rescale, saturation, brightness);
            if( (i+1)%fftLogSpectrumTotalLength != 0 ){
                line(tempMatrix[i].x, tempMatrix[i].y, tempMatrix[i].z, tempMatrix[i+1].x, tempMatrix[i+1].y, tempMatrix[i+1].z);
            }
            stroke(myRGBColor.getRed(),myRGBColor.getGreen(), myRGBColor.getBlue());
        }
    }
}