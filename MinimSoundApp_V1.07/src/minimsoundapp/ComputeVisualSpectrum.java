package minimsoundapp;

import ddf.minim.analysis.*;
import ddf.minim.*;
import java.awt.Color;
import processing.core.*;
import processing.core.PApplet;
import static processing.core.PApplet.map;
import static processing.core.PConstants.P3D;

public abstract class ComputeVisualSpectrum extends PApplet {
    
    float x,y,z;
    FFT fftLog;
    Minim minim;
    AudioPlayer audioplayer;
    final int TOTAL_TRACE_LENGTH=300;
    final int logAveragesMinBandwidth=100;
    final int logAveragesBandsPerOctave=6;
    final int X_AXIS_SCALE=43;//! il existe un lien entre X_AXIS_SCALE et le deuxwieme paramètre de fftLog.logAverages()
    final int Y_AXIS_SCALE=8;
    final int Z_AXIS_SCALE=7;
    PVector[] tempMatrix;
    PVector[] fullMatrix;
  
    
    @Override
    public final void settings() {
        //size(1280, 720, P3D);
        size(480, 360, P3D);
        //size(500, 300, P3D);
    }
    
    @Override
    public final void setup(){
        noStroke();
       minim = new Minim(this);
        audioplayer = minim.loadFile(VisualizationRun.activeFile, 1024);
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
         System.out.println("fftLog.specSize() = "+fftLog.specSize());
         System.out.println("fftLog.timeSize() = "+fftLog.timeSize());
         System.out.println("fftLog.getBandWidth() = "+fftLog.getBandWidth());
    }
    
    public final void beforeDrawingMatrix(){
        background(0);
        directionalLight(500,500,500,-100,-100,50);
        //directionalLight(150,150,1,sin(radians(frameCount)),cos(radians(frameCount)),1);
        ambientLight(80,80,80);
        camera((x+1000),(y+0)-(TOTAL_TRACE_LENGTH*4),-1000-200,0,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
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
        for(int i = 0; i < fftLog.avgSize(); i++){ //i va de 0 a 40
            //int w = (int)width/fftLog.avgSize();
            x = i*fftLog.getBandWidth();
            y = frameCount*Y_AXIS_SCALE;
            z = (-fftLog.getAvg(i)*Z_AXIS_SCALE);          
            tempMatrix[i].x=x;
            tempMatrix[i].y=y;
            tempMatrix[i].z=z;
        }
    }
    
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
}
