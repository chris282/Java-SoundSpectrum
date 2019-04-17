package spectrum.visualization;

import java.awt.Color;
import static processing.core.PApplet.map;

/**
 * Proudly brought to you by Christophe Bordier 
 * https://github.com/chris282/Java-SoundSpectrum
 * 
 * Please help me by contributing to the same project in Javascript light client with Angular and ThreeJs !
 * https://github.com/chris282/JS-SoundSpectrum
 */
public class TwoDimensionSpectrum extends ComputeVisualSpectrum {
    
    /**
     * Only the latest measure is displayed on screen : single line
     */
    @Override
    public void drawMatrix(){
        int fftLogSpectrumTotalLength = fftLog.avgSize();
        float saturation = 1.0f; //saturation
        float brightness = 1f; //brightness
        for(int i=0; i<(tempMatrix.length-1); i++){
            float color_input = (tempMatrix[i].x);
            float color_rescale = map(color_input, 0,fftLogSpectrumTotalLength*fftLog.avgSize()*X_AXIS_SCALE, 0, 1);
            Color myRGBColor = Color.getHSBColor(color_rescale, saturation, brightness);
            if( (i+1)%fftLogSpectrumTotalLength != 0 ){
                line(tempMatrix[i].x, tempMatrix[i].y, tempMatrix[i].z, tempMatrix[i+1].x, tempMatrix[i+1].y, tempMatrix[i+1].z);
            }
            stroke(myRGBColor.getRed(),myRGBColor.getGreen(), myRGBColor.getBlue());
        }
    }
}