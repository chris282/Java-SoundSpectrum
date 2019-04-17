package spectrum.visualization;

import ddf.minim.analysis.*;
import ddf.minim.*;
import java.awt.Color;
import processing.core.*;
import processing.core.PApplet;
import spectrum.run.VisualizationRun;

import static processing.core.PApplet.map;
import static processing.core.PConstants.P3D;

/**
 * Proudly brought to you by Christophe Bordier
 * https://github.com/chris282/Java-SoundSpectrum
 *
 * Please help me by contributing to the same project in Javascript light client with Angular and ThreeJs !
 * https://github.com/chris282/JS-SoundSpectrum
 *
 * @see this documentation page : https://processing.github.io/processing-javadocs/core/index.html?processing/core/PApplet.html
 * @see this documentation page : http://code.compartmental.net/minim/javadoc/ddf/minim/analysis/FFT.html
 */
public abstract class ComputeVisualSpectrum extends PApplet {
    
    float x,y,z;
    FFT fftLog;
    Minim minim;
    AudioPlayer audioplayer;
    int TOTAL_TRACE_LENGTH=800;
    int logAveragesMinBandwidth=100;
    int logAveragesBandsPerOctave=12;
    float X_AXIS_SCALE=1.0f;
    float Y_AXIS_SCALE=1.0f;
    float Z_AXIS_SCALE=1.0f;
    PVector[] tempMatrix;
    PVector[] fullMatrix;
    float LOW_FREQUENCY_SCALE=0.5f;
    float HIGH_FREQUENCY_SCALE=2.2f;
    static VisualizationMode visualizationMode=VisualizationMode.FULLSCREEN; //default is fullScreen visualization
    
    @Override
    public final void settings() {
        switch (visualizationMode){
            case FULLSCREEN:
                System.out.println("Setting up spectrum visualization in FULLSCREEN mode");
                size(1920, 1080,P3D);
                X_AXIS_SCALE=1.0f;
                Y_AXIS_SCALE=3;
                Z_AXIS_SCALE=25;
                TOTAL_TRACE_LENGTH=800;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=12;
                break;
            case MEDIUM:
                System.out.println("Setting up spectrum visualization in SMALL mode");
                size(900, 480, P3D);
                X_AXIS_SCALE=1.2f;
                Y_AXIS_SCALE=10;
                Z_AXIS_SCALE=7.0f;
                TOTAL_TRACE_LENGTH=500;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=6;
                System.out.println("TOTAL_TRACE_LENGTH="+TOTAL_TRACE_LENGTH);
                System.out.println("logAveragesMinBandwidth="+logAveragesMinBandwidth);
                System.out.println("logAveragesBandsPerOctave="+logAveragesBandsPerOctave);
                break;
            case SMALL:
                System.out.println("Setting up spectrum visualization in SMALL mode");
                size(700, 360, P3D);
                X_AXIS_SCALE=1.5f;
                Y_AXIS_SCALE=8;
                Z_AXIS_SCALE=7;
                TOTAL_TRACE_LENGTH=300;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=6;
                System.out.println("TOTAL_TRACE_LENGTH="+TOTAL_TRACE_LENGTH);
                System.out.println("logAveragesMinBandwidth="+logAveragesMinBandwidth);
                System.out.println("logAveragesBandsPerOctave="+logAveragesBandsPerOctave);
                break;
            case SMALLEST:
                System.out.println("Setting up spectrum visualization in SMALL mode");
                size(360, 240, P3D);
                X_AXIS_SCALE=0.8f;
                Y_AXIS_SCALE=8;
                Z_AXIS_SCALE=4;
                TOTAL_TRACE_LENGTH=300;
                logAveragesMinBandwidth=100;
                logAveragesBandsPerOctave=6;
                System.out.println("TOTAL_TRACE_LENGTH="+TOTAL_TRACE_LENGTH);
                System.out.println("logAveragesMinBandwidth="+logAveragesMinBandwidth);
                System.out.println("logAveragesBandsPerOctave="+logAveragesBandsPerOctave);
                break;
            default:
                break;
        }
    }
    
    @Override
    public final void setup(){
        noStroke();
        minim = new Minim(this);
        audioplayer = minim.loadFile(VisualizationRun.activeFile, 1024);
        System.out.println("*** Now playing: "+VisualizationRun.activeFile+" ***");
        audioplayer.play();
        background(255);
        fftLog = new FFT(audioplayer.bufferSize(),audioplayer.sampleRate());
        fftLog.logAverages(logAveragesMinBandwidth,logAveragesBandsPerOctave);     //adjust numbers to adjust spacing
        tempMatrix = new PVector[fftLog.avgSize()];
        fullMatrix = new PVector[TOTAL_TRACE_LENGTH*fftLog.avgSize()];
        for(int i=0;i<fullMatrix.length;i++){
            fullMatrix[i]=new PVector(0, 0, 0);
        }
        for(int i=0;i<tempMatrix.length; i++){
            tempMatrix[i]= new PVector(0, 0, 0);
        }
        System.out.println("fftLog.avgSize() = "+fftLog.avgSize());
        System.out.println("fftLog.getBandWidth() = "+fftLog.getBandWidth());
        System.out.println("fftLog.specSize() = "+fftLog.specSize());
        System.out.println("fftLog.timeSize() = "+fftLog.timeSize());
    }
    
    public final void beforeDrawingMatrix(){
        background(0);
        ambientLight(100,100,100);
        directionalLight(500,500,500,-100,-100,50);
        switch (visualizationMode){
            case FULLSCREEN:
                camera((6000),y,-2000,0,y,0,0,0,1);
                break;
            case MEDIUM:
                camera(1400,(y+1100),-1100,1400,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
                break;
            case SMALL:
                camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
                //camera(2400,(y+800),-850,0,y-(TOTAL_TRACE_LENGTH*4),200,0,0,1);
                //camera(1800,(y+900),-1000,0,y-(TOTAL_TRACE_LENGTH*4),700,0,0,1);
                //camera(2400,(y+800),-850,0,y-(TOTAL_TRACE_LENGTH*4),200,0,0,1);
                //camera(1800,(y+900),-1000,0,y-(TOTAL_TRACE_LENGTH*4),700,0,0,1);
                break;
            case SMALLEST:
                camera(750,(y+1100),-1100,750,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
                //camera((x+1000),(y+0)-(TOTAL_TRACE_LENGTH*4),-1200,0,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
                break;
            default:
                break;
        }
        //play the song
        fftLog.forward(audioplayer.mix);
        fillTempMatrix();
        updateFullMatrix();
    }
    
    @Override
    public final void draw(){
        beforeDrawingMatrix();
        drawMatrix();
    }
    
    public abstract void drawMatrix();
    
    @Override
    public final void stop() {
        // always close Minim audio classes when you finish with them
        audioplayer.close();
        // always stop Minim before exiting
        minim.stop();
        super.stop();
    }
    /**
     * Fill the TEMP array with brand new values
     */
    protected final void fillTempMatrix(){
        for(int i = 0; i < fftLog.avgSize(); i++){
            x = (float) (i*fftLog.avgSize()*X_AXIS_SCALE);
            y = (frameCount)*Y_AXIS_SCALE;
            if(i>fftLog.avgSize()*4/5){
                //High frequency are manually increased
                z =(float) ((-fftLog.getAvg(i)*Z_AXIS_SCALE)*HIGH_FREQUENCY_SCALE);
            } else {
                //Low frequency are manually decreased
                z =(float) ((-fftLog.getAvg(i)*Z_AXIS_SCALE)*LOW_FREQUENCY_SCALE);
            }
            tempMatrix[i].x=x;
            tempMatrix[i].y=y;
            tempMatrix[i].z=z;
        }
    }
    
    /**
     * TODO do a log scale on Z compute
     * https://stackoverflow.com/questions/47712818/converting-linear-scale-to-log-scale-in-java
     */
    /**
     * Given an index and the total number of entries, return the
     * log-scaled value.
     */
    /**
     * logScale: function(index, total, opt_base) {
     * var base = opt_base || 2;
     * var logmax = this.logBase(total + 1, base);
     * var exp = logmax * index / total;
     * return Math.round(Math.pow(base, exp) - 1);
     * },
     **/
    //int intResult = (int) Math.pow(2, 3);
    //wil be 8
    
    /**
     * Update the FULL array by
     * - inserting the new values (from TEMP array) in the last slot
     * - removing the first slot
     * - updating all the 'x' slot by setting his value with the 'x+1' slot
     */
    protected final void updateFullMatrix(){
        //la matrice totale fait n*fftLog.avgSize() en taille
        //car elle contient l'ensemble des frequences de 0 à fftLog.avgSize() pour chaque mesure dessinée a l'écran
        for(int index=0;index<(TOTAL_TRACE_LENGTH-1)*fftLog.avgSize();index++){
            fullMatrix[index].x=fullMatrix[index+fftLog.avgSize()].x;
            fullMatrix[index].y=fullMatrix[index+fftLog.avgSize()].y;
            fullMatrix[index].z=fullMatrix[index+fftLog.avgSize()].z;
        }
        //remplir le tableau FULL avec TEMP
        for(int i = 0; i<fftLog.avgSize();i++){
            fullMatrix[(TOTAL_TRACE_LENGTH-1)*fftLog.avgSize()+i].x=tempMatrix[i].x;
            fullMatrix[(TOTAL_TRACE_LENGTH-1)*fftLog.avgSize()+i].y=tempMatrix[i].y;
            fullMatrix[(TOTAL_TRACE_LENGTH-1)*fftLog.avgSize()+i].z=tempMatrix[i].z;
        }
    }
    
    public VisualizationMode getVisualizationMode() {
        return visualizationMode;
    }
    
    public static void setVisualizationMode(VisualizationMode _visualizationMode) {
        visualizationMode = _visualizationMode;
    }
    
    
}
