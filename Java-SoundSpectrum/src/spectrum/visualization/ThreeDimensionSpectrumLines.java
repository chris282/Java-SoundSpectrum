
package spectrum.visualization;

import ddf.minim.analysis.*;
import ddf.minim.*;
import java.awt.Color;
import processing.core.*;
import processing.core.PApplet;
import static processing.core.PApplet.map;

/**
 * Proudly brought to you by Christophe Bordier 
 * https://github.com/chris282/Java-SoundSpectrum
 * 
 * Please help me by contributing to the same project in Javascript light client with Angular and ThreeJs !
 * https://github.com/chris282/JS-SoundSpectrum
 */
public class ThreeDimensionSpectrumLines extends ComputeVisualSpectrum {
   
    
    @Override
    public void drawMatrix(){
        int fftLogSpectrumTotalLength = fftLog.avgSize();
        float saturation = 1.0f; //saturation
        float brightness = 0.9f; //brightness
        for(int i=0; i<(fullMatrix.length-1); i++){
            float color_input = (fullMatrix[i].x);
            float color_rescale = map(color_input, 0,fftLogSpectrumTotalLength*fftLog.avgSize()*X_AXIS_SCALE, 0, 1);
            Color myRGBColor = Color.getHSBColor(color_rescale, saturation, brightness);
            if( (i+1)%fftLogSpectrumTotalLength != 0 ){
                line(fullMatrix[i].x, fullMatrix[i].y, fullMatrix[i].z, fullMatrix[i+1].x, fullMatrix[i+1].y, fullMatrix[i+1].z);
            }
            stroke(myRGBColor.getRed(),myRGBColor.getGreen(), myRGBColor.getBlue());
        }
    }
    
   
}