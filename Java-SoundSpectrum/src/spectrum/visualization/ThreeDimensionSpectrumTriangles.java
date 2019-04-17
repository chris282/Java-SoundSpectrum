/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spectrum.visualization;

import java.awt.Color;


/**
 * Proudly brought to you by Christophe Bordier 
 * https://github.com/chris282/Java-SoundSpectrum
 * 
 * Please help me by contributing to the same project in Javascript light client with Angular and ThreeJs !
 * https://github.com/chris282/JS-SoundSpectrum
 */
public class ThreeDimensionSpectrumTriangles extends ComputeVisualSpectrum {
    
    @Override 
    public void drawMatrix(){
        int fftLogSpectrumTotalLength = fftLog.avgSize();
        float saturation = 1.0f; //saturation
        float brightness = 0.9f; //brightness
        for(int i=1; i<fullMatrix.length-fftLogSpectrumTotalLength; i++){
            float color_input = (fullMatrix[i].x);
            float color_rescale = map(color_input, 0,fftLogSpectrumTotalLength*fftLog.avgSize()*X_AXIS_SCALE, 0, 1);
            Color myRGBColor = Color.getHSBColor(color_rescale, saturation, brightness);
            if((i+1)%fftLogSpectrumTotalLength != 0){
                beginShape(TRIANGLE_STRIP);
                fill(myRGBColor.getRed(),myRGBColor.getGreen(), myRGBColor.getBlue());
                vertex(fullMatrix[i+1].x, fullMatrix[i+1].y, fullMatrix[i+1].z);
                vertex(fullMatrix[i].x, fullMatrix[i].y, fullMatrix[i].z);
                vertex(fullMatrix[i+1+fftLogSpectrumTotalLength].x, fullMatrix[i+1+fftLogSpectrumTotalLength].y, fullMatrix[i+1+fftLogSpectrumTotalLength].z);
                vertex(fullMatrix[i+fftLogSpectrumTotalLength].x, fullMatrix[i+fftLogSpectrumTotalLength].y, fullMatrix[i+fftLogSpectrumTotalLength].z);
                endShape(CLOSE);
            }
        }
    }
}
