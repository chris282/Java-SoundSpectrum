/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package spectrum.visualization.config;

import static processing.core.PConstants.P3D;


public class VisualizationParameters {
    
    int windowWidth;
    int windowHeight;
    int TOTAL_TRACE_LENGTH=800;
    int logAveragesMinBandwidth=100;
    //logAveragesBandsPerOctave :6 is a minimum value, be aware of not increasing this value too much, performance drop !
    //The bigger this value, the more precise is the scene
    //The lower this value, the more faster (Frames per second) is the scene
    int logAveragesBandsPerOctave;
    float X_AXIS_SCALE;
    float Y_AXIS_SCALE;
    float Z_AXIS_SCALE;
    //Manual Frequency amplitude rescale
    boolean MANUAL_FREQUENCY_RESCALE=false;
    float HIGH_FREQUENCY_SCALE=1.0f;
    float MEDIUM_FREQUENCY_SCALE=1.0f;
    float LOW_FREQUENCY_SCALE=0.2f;
    //TODO : emphasize on voice frequency range
    //TODO use a sortedMap or TreeMap to create many ranges of frequency with scales
    //16 ranges,
    //4 for very high freq
    //4 for medium freq
    //4 for low freq
    float MEDIUM_FREQUENCY_UPPER_BOUND=3400;
    float LOW_FREQUENCY_UPPER_BOUND=300;
    //
    VisualizationMode visualizationMode=VisualizationMode.FULLSCREEN; //default is fullScreen visualization
    
    public VisualizationParameters(VisualizationMode _visualizationMode){
        this.visualizationMode=_visualizationMode;
        switch (_visualizationMode){
            case FULLSCREEN:
                System.out.println("Setting up spectrum visualization in FULLSCREEN mode");
                windowWidth=1920;
                windowHeight=1080;
                X_AXIS_SCALE=0.35f;
                Y_AXIS_SCALE=18;
                Z_AXIS_SCALE=12.0f;
                TOTAL_TRACE_LENGTH=400;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=12;
                break;
            case MEDIUM:
                System.out.println("Setting up spectrum visualization in SMALL mode");
                windowWidth=900;
                windowHeight=480;
                X_AXIS_SCALE=1.5f;
                Y_AXIS_SCALE=60.0f;
                Z_AXIS_SCALE=12f;
                TOTAL_TRACE_LENGTH=50;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=6;
                System.out.println("TOTAL_TRACE_LENGTH="+TOTAL_TRACE_LENGTH);
                System.out.println("logAveragesMinBandwidth="+logAveragesMinBandwidth);
                System.out.println("logAveragesBandsPerOctave="+logAveragesBandsPerOctave);
                break;
            case SMALL:
                System.out.println("Setting up spectrum visualization in SMALL mode");
                windowWidth=880;
                windowHeight=360;
                X_AXIS_SCALE=0.1f;
                Y_AXIS_SCALE=25.0f;
                Z_AXIS_SCALE=10.5f;
                TOTAL_TRACE_LENGTH=100;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=22;
                System.out.println("TOTAL_TRACE_LENGTH="+TOTAL_TRACE_LENGTH);
                System.out.println("logAveragesMinBandwidth="+logAveragesMinBandwidth);
                System.out.println("logAveragesBandsPerOctave="+logAveragesBandsPerOctave);
                break;
            case SMALLEST:
                System.out.println("Setting up spectrum visualization in SMALL mode");
                windowWidth=360;
                windowHeight=240;
                X_AXIS_SCALE=1.2f;
                Y_AXIS_SCALE=10;
                Z_AXIS_SCALE=14;
                TOTAL_TRACE_LENGTH=30;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=6;
                System.out.println("TOTAL_TRACE_LENGTH="+TOTAL_TRACE_LENGTH);
                System.out.println("logAveragesMinBandwidth="+logAveragesMinBandwidth);
                System.out.println("logAveragesBandsPerOctave="+logAveragesBandsPerOctave);
                break;
                
            case FULLSCREEN_WITH_RESCALE :
                System.out.println("Setting up spectrum visualization in FULLSCREEN mode");
                MANUAL_FREQUENCY_RESCALE=true;
                windowWidth=1920;
                windowHeight=1080;
                X_AXIS_SCALE=0.35f;
                Y_AXIS_SCALE=18;
                Z_AXIS_SCALE=12.0f;
                TOTAL_TRACE_LENGTH=400;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=12;
                break;
            case MEDIUM_WITH_RESCALE :
                MANUAL_FREQUENCY_RESCALE=true;
                break;
            case SMALL_WITH_RESCALE :
                System.out.println("Setting up spectrum visualization in SMALL mode");
                MANUAL_FREQUENCY_RESCALE=true;
                windowWidth=700;
                windowHeight=360;
                X_AXIS_SCALE=0.1f;
                Y_AXIS_SCALE=10.0f;
                Z_AXIS_SCALE=12.5f;
                TOTAL_TRACE_LENGTH=100;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=22;
                System.out.println("TOTAL_TRACE_LENGTH="+TOTAL_TRACE_LENGTH);
                System.out.println("logAveragesMinBandwidth="+logAveragesMinBandwidth);
                System.out.println("logAveragesBandsPerOctave="+logAveragesBandsPerOctave);
                break;
            case SMALLEST_WITH_RESCALE :
                MANUAL_FREQUENCY_RESCALE=true;
                break;
            default:
                break;
        }
    }
    
    public int getTOTAL_TRACE_LENGTH() {
        return TOTAL_TRACE_LENGTH;
    }
    
    public void setTOTAL_TRACE_LENGTH(int TOTAL_TRACE_LENGTH) {
        this.TOTAL_TRACE_LENGTH = TOTAL_TRACE_LENGTH;
    }
    
    public int getLogAveragesMinBandwidth() {
        return logAveragesMinBandwidth;
    }
    
    public void setLogAveragesMinBandwidth(int logAveragesMinBandwidth) {
        this.logAveragesMinBandwidth = logAveragesMinBandwidth;
    }
    
    public int getLogAveragesBandsPerOctave() {
        return logAveragesBandsPerOctave;
    }
    
    public void setLogAveragesBandsPerOctave(int logAveragesBandsPerOctave) {
        this.logAveragesBandsPerOctave = logAveragesBandsPerOctave;
    }
    
    public float getX_AXIS_SCALE() {
        return X_AXIS_SCALE;
    }
    
    public void setX_AXIS_SCALE(float X_AXIS_SCALE) {
        this.X_AXIS_SCALE = X_AXIS_SCALE;
    }
    
    public float getY_AXIS_SCALE() {
        return Y_AXIS_SCALE;
    }
    
    public void setY_AXIS_SCALE(float Y_AXIS_SCALE) {
        this.Y_AXIS_SCALE = Y_AXIS_SCALE;
    }
    
    public float getZ_AXIS_SCALE() {
        return Z_AXIS_SCALE;
    }
    
    public void setZ_AXIS_SCALE(float Z_AXIS_SCALE) {
        this.Z_AXIS_SCALE = Z_AXIS_SCALE;
    }
    
    public boolean isMANUAL_FREQUENCY_RESCALE() {
        return MANUAL_FREQUENCY_RESCALE;
    }
    
    public void setMANUAL_FREQUENCY_RESCALE(boolean MANUAL_FREQUENCY_RESCALE) {
        this.MANUAL_FREQUENCY_RESCALE = MANUAL_FREQUENCY_RESCALE;
    }
    
    public float getHIGH_FREQUENCY_SCALE() {
        return HIGH_FREQUENCY_SCALE;
    }
    
    public void setHIGH_FREQUENCY_SCALE(float HIGH_FREQUENCY_SCALE) {
        this.HIGH_FREQUENCY_SCALE = HIGH_FREQUENCY_SCALE;
    }
    
    public float getMEDIUM_FREQUENCY_SCALE() {
        return MEDIUM_FREQUENCY_SCALE;
    }
    
    public void setMEDIUM_FREQUENCY_SCALE(float MEDIUM_FREQUENCY_SCALE) {
        this.MEDIUM_FREQUENCY_SCALE = MEDIUM_FREQUENCY_SCALE;
    }
    
    public float getLOW_FREQUENCY_SCALE() {
        return LOW_FREQUENCY_SCALE;
    }
    
    public void setLOW_FREQUENCY_SCALE(float LOW_FREQUENCY_SCALE) {
        this.LOW_FREQUENCY_SCALE = LOW_FREQUENCY_SCALE;
    }
    
    public float getMEDIUM_FREQUENCY_UPPER_BOUND() {
        return MEDIUM_FREQUENCY_UPPER_BOUND;
    }
    
    public void setMEDIUM_FREQUENCY_UPPER_BOUND(float MEDIUM_FREQUENCY_UPPER_BOUND) {
        this.MEDIUM_FREQUENCY_UPPER_BOUND = MEDIUM_FREQUENCY_UPPER_BOUND;
    }
    
    public float getLOW_FREQUENCY_UPPER_BOUND() {
        return LOW_FREQUENCY_UPPER_BOUND;
    }
    
    public void setLOW_FREQUENCY_UPPER_BOUND(float LOW_FREQUENCY_UPPER_BOUND) {
        this.LOW_FREQUENCY_UPPER_BOUND = LOW_FREQUENCY_UPPER_BOUND;
    }
    
    public VisualizationMode getVisualizationMode() {
        return visualizationMode;
    }
    
    public void setVisualizationMode(VisualizationMode visualizationMode) {
        this.visualizationMode = visualizationMode;
    }
    
    public int getWindowWidth() {
        return windowWidth;
    }
    
    public int getWindowHeight() {
        return windowHeight;
    }
    
    
}
