package spectrum.visualization;

import ddf.minim.analysis.*;
import ddf.minim.*;
import java.awt.Color;
import java.util.ArrayList;

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
	int TOTAL_TRACE_LENGTH=800;
	int logAveragesMinBandwidth=100;
	//logAveragesBandsPerOctave :6 is a minimum value, be aware of not increasing this value too much, performance drop !
	//The bigger this value, the more precise is the scene
	//The lower this value, the more faster (Frames per second) is the scene
	int logAveragesBandsPerOctave; 
	float X_AXIS_SCALE;
	float Y_AXIS_SCALE;
	float Z_AXIS_SCALE;
	PVector[] tempMatrix;
	PVector[] fullMatrix;
	//Manual Frequency amplitude rescale
	static boolean MANUAL_FREQUENCY_RESCALE=true;
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
	static VisualizationMode visualizationMode=VisualizationMode.FULLSCREEN; //default is fullScreen visualization
	//TODO : more color saturation and brightness in high amplitudes 
	@Override
	public final void settings() {
		switch (visualizationMode){
		case FULLSCREEN:
			System.out.println("Setting up spectrum visualization in FULLSCREEN mode");
			size(1920, 1080,P3D);
			X_AXIS_SCALE=0.35f;
			Y_AXIS_SCALE=18;
			Z_AXIS_SCALE=12.0f;
			TOTAL_TRACE_LENGTH=400;
			logAveragesMinBandwidth=100;
			logAveragesBandsPerOctave=12;
			break;
		case MEDIUM:
			System.out.println("Setting up spectrum visualization in SMALL mode");
			size(900, 480, P3D);
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
			size(700, 360, P3D);
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
		case SMALLEST: //PERFORMANCE ISSUE (4 windows)
			System.out.println("Setting up spectrum visualization in SMALL mode");
			size(360, 240, P3D);
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
		System.out.println("Length : "+ audioplayer.length());
		System.out.println("Gain : "+ audioplayer.getGain());
		//System.out.println("Volume : "+ audioplayer.getVolume());
		System.out.println("sampleRate : "+ audioplayer.sampleRate());
		audioplayer.play();
		background(255);
		fftLog = new FFT(audioplayer.bufferSize(),audioplayer.sampleRate());
		fftLog.logAverages(logAveragesMinBandwidth,logAveragesBandsPerOctave);     //adjust numbers to adjust spacing
		//Allocating Memory for the vectors
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
		frequencyValuesPrint(fftLog);
	}

	public final void beforeDrawingMatrix(){
		background(0);
		ambientLight(100,100,100);
		directionalLight(500,500,500,-100,-100,50);
		switch (visualizationMode){
		case FULLSCREEN:
			camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
			break;
		case MEDIUM:
			camera(1400,(y+1100),-1100,1400,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
			break;
		case SMALL:
			camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),-600,0,0,1);
			//nice : camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),-500,0,0,1);
			//OK : camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
			//camera(2400,(y+800),-850,0,y-(TOTAL_TRACE_LENGTH*4),200,0,0,1);
			//camera(1800,(y+900),-1000,0,y-(TOTAL_TRACE_LENGTH*4),700,0,0,1);
			//camera(2400,(y+800),-850,0,y-(TOTAL_TRACE_LENGTH*4),200,0,0,1);
			//camera(1800,(y+900),-1000,0,y-(TOTAL_TRACE_LENGTH*4),700,0,0,1);
			break;
		case SMALLEST:
			camera(1500,(y+1100),-1100,1500,y-(TOTAL_TRACE_LENGTH*4),0,0,0,1);
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
			x = (float) (i*fftLog.avgSize()*X_AXIS_SCALE);
			y = (frameCount)*Y_AXIS_SCALE;
			z=(-fftLog.getAvg(i)*Z_AXIS_SCALE);
			float frequency=fftLog.getAverageCenterFrequency(i);
			//System.out.println("frequency = "+frequency);
			//float freqAmplitude=fftLog.getFreq(fftLog.getAverageCenterFrequency(i));
			//System.out.println("freqAmplitude="+freqAmplitude);
			if(MANUAL_FREQUENCY_RESCALE){
				if(frequency<LOW_FREQUENCY_UPPER_BOUND){
					//Low frequencies are manually decreased
					z =(float) z*LOW_FREQUENCY_SCALE;
				} else if(frequency>LOW_FREQUENCY_UPPER_BOUND && frequency<MEDIUM_FREQUENCY_UPPER_BOUND) {
					//Medium frequencies are manually increase (a bit)
					z =(float) z*MEDIUM_FREQUENCY_SCALE;
				} else if(frequency > MEDIUM_FREQUENCY_UPPER_BOUND){
					//High frequencies are manually increased
					z =(float) z*HIGH_FREQUENCY_SCALE;
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

	public VisualizationMode getVisualizationMode() {
		return visualizationMode;
	}

	public static void setVisualizationMode(VisualizationMode _visualizationMode) {
		visualizationMode = _visualizationMode;
	}

	public static void setMANUAL_FREQUENCY_RESCALE( boolean MANUAL_FREQUENCY_RESCALE) {
		ComputeVisualSpectrum.MANUAL_FREQUENCY_RESCALE = MANUAL_FREQUENCY_RESCALE;
	}

	private static String fixedLengthString(String string, int length) {
		return String.format("%1$"+length+ "s", string);
	}

}
