/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package minimsoundapp;

import ddf.minim.analysis.*;
import ddf.minim.*;
import java.awt.Color;
import processing.core.*;
import processing.core.PApplet;
import static processing.core.PApplet.map;

public class ThreeDimensionSpectrumLines extends ComputeVisualSpectrum {
    
     
    @Override
    public void drawMatrix(){
        int fftLogSpectrumTotalLength = fftLog.avgSize();
        float saturation = 1.0f; //saturation
        float brightness = 1f; //brightness
        for(int i=0; i<(fullMatrix.length-1); i++){
            float color_input = (fullMatrix[i].x);
            float color_rescale = map(color_input, 0,fftLogSpectrumTotalLength*X_AXIS_SCALE, 0, 1);
            Color myRGBColor = Color.getHSBColor(color_rescale, saturation, brightness);
            if( (i+1)%fftLogSpectrumTotalLength != 0 ){
                line(fullMatrix[i].x, fullMatrix[i].y, fullMatrix[i].z, fullMatrix[i+1].x, fullMatrix[i+1].y, fullMatrix[i+1].z);
            }
            stroke(myRGBColor.getRed(),myRGBColor.getGreen(), myRGBColor.getBlue());
        }
    }
    
   
}