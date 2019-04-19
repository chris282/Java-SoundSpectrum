package spectrum.visualization;

import spectrum.visualization.config.VisualizationMode;
import ddf.minim.analysis.*;
import ddf.minim.*;
import java.awt.Color;
import java.util.ArrayList;

import processing.core.*;
import processing.core.PApplet;
import spectrum.run.VisualizationRun;

import static processing.core.PApplet.map;
import static processing.core.PConstants.P3D;
import spectrum.visualization.config.VisualizationParameters;

/**
 * Proudly brought to you by Christophe Bordier
 * https://github.com/chris282/Java-SoundSpectrum
 *
 * Please help me by contributing to the same project in Javascript light client with Angular and ThreeJs !
 * https://github.com/chris282/JS-SoundSpectrum
 * 
 * The page where all this started : "john locke » Blog Archive » visualizing sound in processing"
 * http://gracefulspoon.com/blog/2009/04/02/visualizing-sound-in-processing/comment-page-1/#comment-64576
 * https://www.youtube.com/watch?v=5DJW9aepfTc
 * 
 * @see this documentation page : https://processing.github.io/processing-javadocs/core/index.html?processing/core/PApplet.html
 * @see this documentation page : http://code.compartmental.net/minim/javadoc/ddf/minim/analysis/FFT.html
 * @see http://code.compartmental.net/minim/moogfilter_class_moogfilter.html
 */
public abstract class ComputeVisualSpectrum extends PApplet {

	float x,y,z;
	FFT fftLog; //make it singleton
	Minim minim; //make it singleton
	AudioPlayer audioplayer;
	PVector[] tempMatrix;
	PVector[] fullMatrix;
	//TODO : more color saturation and brightness in high amplitudes 
        static VisualizationParameters parameters;
        
	@Override
	public final void settings() {
		size(parameters.getWindowWidth(),parameters.getWindowHeight(), P3D);
	}

	@Override
	public final void setup(){
		noStroke();
		minim = new Minim(this);
		audioplayer = minim.loadFile(VisualizationRun.activeFile, 1024);
		System.out.println("*** Now playing: "+VisualizationRun.activeFile+" ***");
		System.out.println("Length : "+ audioplayer.length());
		System.out.println("Gain : "+ audioplayer.getGain());
		//System.out.println("Volume : "+ audioplayer.getVolume());
		System.out.println("sampleRate : "+ audioplayer.sampleRate());
		audioplayer.play();
		background(255);
		fftLog = new FFT(audioplayer.bufferSize(),audioplayer.sampleRate());
		fftLog.logAverages(parameters.getLogAveragesMinBandwidth(),parameters.getLogAveragesBandsPerOctave());     //adjust numbers to adjust spacing
		//Allocating Memory for the vectors
		tempMatrix = new PVector[fftLog.avgSize()];
		fullMatrix = new PVector[parameters.getTOTAL_TRACE_LENGTH()*fftLog.avgSize()];
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
		frequencyValuesPrint(fftLog);
	}

	public final void beforeDrawingMatrix(){
		background(0);
		ambientLight(100,100,100);
		directionalLight(500,500,500,-100,-100,50);
		switch (parameters.getVisualizationMode()){
		case FULLSCREEN:
			camera(1500,(y+1100),-1100,1500,y-(parameters.getTOTAL_TRACE_LENGTH()*4),0,0,0,1);
			break;
		case MEDIUM:
			camera(1400,(y+1100),-1100,1400,y-(parameters.getTOTAL_TRACE_LENGTH()*4),0,0,0,1);
			break;
		case SMALL:
			camera(1500,(y+1100),-1100,1500,y-(parameters.getTOTAL_TRACE_LENGTH()*4),-600,0,0,1);
			//nice : camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),-500,0,0,1);
			//OK : camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
			//camera(2400,(y+800),-850,0,y-(TOTAL_TRACE_LENGTH*4),200,0,0,1);
			//camera(1800,(y+900),-1000,0,y-(TOTAL_TRACE_LENGTH*4),700,0,0,1);
			//camera(2400,(y+800),-850,0,y-(TOTAL_TRACE_LENGTH*4),200,0,0,1);
			//camera(1800,(y+900),-1000,0,y-(TOTAL_TRACE_LENGTH*4),700,0,0,1);
			break;
		case SMALLEST:
			camera(1500,(y+1100),-1100,1500,y-(parameters.getTOTAL_TRACE_LENGTH()*4),0,0,0,1);
			//camera(750,(y+1100),-1100,750,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
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
		//AudioInput input;
		// input.close();
	}
	/**
	 * Fill the TEMP array with brand new values
	 */
	protected final void fillTempMatrix(){
		for(int i = 0; i < fftLog.avgSize(); i++){
			x = (float) (i*fftLog.avgSize()*parameters.getX_AXIS_SCALE());
			y = (frameCount)*parameters.getY_AXIS_SCALE();
			z=(-fftLog.getAvg(i)*parameters.getZ_AXIS_SCALE());
			float frequency=fftLog.getAverageCenterFrequency(i);
			//System.out.println("frequency = "+frequency);
			//float freqAmplitude=fftLog.getFreq(fftLog.getAverageCenterFrequency(i));
			//System.out.println("freqAmplitude="+freqAmplitude);
			if(parameters.isMANUAL_FREQUENCY_RESCALE()){
				if(frequency<parameters.getLOW_FREQUENCY_UPPER_BOUND()){
					//Low frequencies are manually decreased
					z =(float) z*parameters.getLOW_FREQUENCY_SCALE();
				} else if(frequency>parameters.getLOW_FREQUENCY_UPPER_BOUND() && frequency<parameters.getMEDIUM_FREQUENCY_UPPER_BOUND()) {
					//Medium frequencies are manually increase (a bit)
					z =(float) z*parameters.getMEDIUM_FREQUENCY_SCALE();
				} else if(frequency > parameters.getMEDIUM_FREQUENCY_UPPER_BOUND()){
					//High frequencies are manually increased
					z =(float) z*parameters.getHIGH_FREQUENCY_SCALE();
				}
			}
			//System.out.println("i = "+i);
			//System.out.println("fftLog.getAvg(i) = "+fftLog.getAvg(i));
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
		for(int index=0;index<(parameters.getTOTAL_TRACE_LENGTH()-1)*fftLog.avgSize();index++){
			fullMatrix[index].x=fullMatrix[index+fftLog.avgSize()].x;
			fullMatrix[index].y=fullMatrix[index+fftLog.avgSize()].y;
			fullMatrix[index].z=fullMatrix[index+fftLog.avgSize()].z;
		}
		//remplir le tableau FULL avec TEMP
		for(int i = 0; i<fftLog.avgSize();i++){
			fullMatrix[(parameters.getTOTAL_TRACE_LENGTH()-1)*fftLog.avgSize()+i].x=tempMatrix[i].x;
			fullMatrix[(parameters.getTOTAL_TRACE_LENGTH()-1)*fftLog.avgSize()+i].y=tempMatrix[i].y;
			fullMatrix[(parameters.getTOTAL_TRACE_LENGTH()-1)*fftLog.avgSize()+i].z=tempMatrix[i].z;
		}
	}

	private void frequencyValuesPrint(FFT fftLog){
		int totalNumberOfFrequencies=fftLog.avgSize();
		System.out.println(totalNumberOfFrequencies+" Frequencies : ");
		String line=""; 
		StringBuffer stringBuffer=new StringBuffer();
		for(int i = 0; i < totalNumberOfFrequencies; i++){
			float frequency=fftLog.getAverageCenterFrequency(i);
			String frequencyString=frequency+" Hz | ";
			String toAppend=fixedLengthString(frequencyString,16);
			if(i>(totalNumberOfFrequencies-(totalNumberOfFrequencies%10))){
				line=stringBuffer.append(toAppend).toString();
			} else {
				line=stringBuffer.append(toAppend).toString();
				if((i+1)%10==0){
					System.out.println(line);
					stringBuffer.setLength(0);
				}
			}
		}
		//Print the last values
		System.out.println(line);
	}

    private static String fixedLengthString(String string, int length) {
            return String.format("%1$"+length+ "s", string);
    }

    public static void setParameters(VisualizationParameters _parameters) {
        ComputeVisualSpectrum.parameters = _parameters;
    }

}
