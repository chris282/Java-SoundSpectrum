/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimsoundapp;

import java.awt.Color;
import static processing.core.PApplet.map;

/**
 *
 * @author Chris
 */
public class ThreeDimensionSpectrumCloudPoints extends ComputeVisualSpectrum {
    
    @Override
    public void drawMatrix(){
        int fftLogSpectrumTotalLength = fftLog.avgSize();
        float saturation = 1.0f; //saturation
        float brightness = 1f; //brightness
        for(int i=0; i<fullMatrix.length; i++){
            float color_input = (fullMatrix[i].x);    
            float color_rescale = map(color_input, 0,fftLogSpectrumTotalLength*X_AXIS_SCALE, 0, 1);
            Color myRGBColor = Color.getHSBColor(color_rescale, saturation, brightness);
            point(fullMatrix[i].x, fullMatrix[i].y, fullMatrix[i].z);       
            stroke(myRGBColor.getRed(),myRGBColor.getGreen(), myRGBColor.getBlue());
        }
    }
}
