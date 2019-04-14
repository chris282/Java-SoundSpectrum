Welcome to my Sound Spectrum project, based on the Minim Api !

This is a homemade project done for fun purpose, so customize and extend it if you want ! 
Main features : high performance DFT transformation, with 4 visualizations types at the same time.

I recommend using Netbeans for this project, wich work pretty well.

First run :
-  Make sure you import LibsToImportAtFirstRun libs so you can compile the code (it wont compile if you don't import these libs)
-  Customize the data path to you prefered mp3 file in the main class "VisualizationRun"  ex :  public static final  String mp3file1="C:\\Users\\Chris\\Documents\\DevPerso\\SoundSpectrumProjectMinimAndJavaFX\\resources\\DeorroFtElvisCrespoBailar.mp3";
- Right click on the "VisualizationRun" class and 'Run file' and that's it !!
   
Functionallities TODO (HELP and contributors WANTED!) : 
- For a given windows path : Read every mp3 in a specified folder in a row
- Change the camera position in order to improve scene display (like in the picture "3D-Spectrum-of-Sound.jpg")
- Apply a Logarithmic (or Square root) scale on data measures, in order to improve scene display (highlight high frequency measures and minimize low frequency measures)
   
   Demo : https://www.youtube.com/watch?v=VxYBAFk8pIk
   
Processing libs forums : https://discourse.processing.org/t/sharing-minimaslit-project-to-do-a-3d-sound-spectrum-visualiazer/9876
See this documentation page : http://code.compartmental.net/minim/javadoc/ddf/minim/analysis/FFT.html
Christophe Bordier, France
