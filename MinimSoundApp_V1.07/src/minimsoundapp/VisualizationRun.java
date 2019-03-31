/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimsoundapp;

/**
 *
 * @author formation
 */
public class VisualizationRun {
    
    /**
     * Functionallities TODO : 
     * - For a given windows path : Read every mp3 in a specified folder in a row
     */
    public static final  String mp3file1="C:\\Users\\Chris\\Documents\\DevPerso\\SoundSpectrumProjectMinimAndJavaFX\\resources\\DeorroFtElvisCrespoBailar.mp3";
    public static final  String mp3file2="C:\\Users\\Chris\\Documents\\DevPerso\\SoundSpectrumProjectMinimAndJavaFX\\resources\\Complicated.mp3";
    public static final  String mp3file3="C:\\Users\\Chris\\Documents\\DevPerso\\SoundSpectrumProjectMinimAndJavaFX\\resources\\NeverComeDownPRESIDENTIALREMASTER.mp3";
            
            
    public static final  String activeFile=mp3file1;
    public static void main(String args[]){   
        
        TwoDimensionSpectrum twoDimensionSpectrum = new TwoDimensionSpectrum();
        twoDimensionSpectrum.main(new String[] { "minimsoundapp.TwoDimensionSpectrum" });
        //
        ThreeDimensionSpectrumCloudPoints threeDimensionSpectrumCloudPoints = new ThreeDimensionSpectrumCloudPoints();
        threeDimensionSpectrumCloudPoints.main(new String[] { "minimsoundapp.ThreeDimensionSpectrumCloudPoints" });
        //
        ThreeDimensionSpectrumTriangles threeDimensionSpectrumTriangles = new ThreeDimensionSpectrumTriangles();
        threeDimensionSpectrumTriangles.main(new String[] { "minimsoundapp.ThreeDimensionSpectrumTriangles" });
        //
        ThreeDimensionSpectrumLines threeDimensionSpectrum = new ThreeDimensionSpectrumLines();
        threeDimensionSpectrum.main(new String[] { "minimsoundapp.ThreeDimensionSpectrumLines" });
    }   
    
   
}
