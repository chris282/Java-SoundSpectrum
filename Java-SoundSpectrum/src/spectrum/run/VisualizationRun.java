
package spectrum.run;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import spectrum.visualization.ComputeVisualSpectrum;
import spectrum.visualization.ThreeDimensionSpectrumCloudPoints;
import spectrum.visualization.ThreeDimensionSpectrumLines;
import spectrum.visualization.ThreeDimensionSpectrumTriangles;
import spectrum.visualization.TwoDimensionSpectrum;
import spectrum.visualization.VisualizationMode;

/**
 * Proudly brought to you by Christophe Bordier
 * https://github.com/chris282/Java-SoundSpectrum
 *
 * Please help me by contributing to the same project in Javascript light client with Angular and ThreeJs !
 * https://github.com/chris282/JS-SoundSpectrum
 */
public class VisualizationRun {
    //https://stackoverflow.com/questions/4410803/a-simple-question-about-lib-folder-in-eclipse
    //https://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-%28Java%29
    //https://stackoverflow.com/questions/11758594/how-do-i-put-all-required-jar-files-in-a-library-folder-inside-the-final-jar-fil
    
    public static final  String myFolder="SoundSpectrum"; //You music folder inside Windows Music folder (C:\Users\<your-user>\Music\<myFolder>)
    public static  String activeFile;
    
    public static void main(String args[]){
        String userHome=System.getProperty("user.home");
        String windowsDirectorySeparator="\\";
        String windowsMusicFolder=userHome+windowsDirectorySeparator+"Music";
        String targetPlaylistFolderName=windowsMusicFolder+windowsDirectorySeparator+myFolder;
        List<String> playableMusicFileList=musicPlayList(targetPlaylistFolderName);
        activeFile=playableMusicFileList.get(0);
        fullScreenVisualizationDisplay();
//simpleDisplay();
//twoWindowsVisualizationDisplay();
//fourWindowsVisualizationDisplay();
//smallFourWindowsVisualizationDisplay();
    }
    
    public static void simpleDisplay(){
        ComputeVisualSpectrum.setVisualizationMode(VisualizationMode.MEDIUM);
        ThreeDimensionSpectrumTriangles.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumTriangles" });
    }
    
    public static void twoWindowsVisualizationDisplay(){
        ComputeVisualSpectrum.setVisualizationMode(VisualizationMode.SMALL);
        ThreeDimensionSpectrumTriangles.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumTriangles" });
        ThreeDimensionSpectrumLines.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumLines" });
    }
    
    public static void fourWindowsVisualizationDisplay(){
        ComputeVisualSpectrum.setVisualizationMode(VisualizationMode.SMALL);
        TwoDimensionSpectrum.main(new String[] { "spectrum.visualization.TwoDimensionSpectrum" });
        ThreeDimensionSpectrumCloudPoints.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumCloudPoints" });
        ThreeDimensionSpectrumTriangles.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumTriangles" });
        ThreeDimensionSpectrumLines.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumLines" });
    }
    
    public static void smallFourWindowsVisualizationDisplay(){
        ComputeVisualSpectrum.setVisualizationMode(VisualizationMode.SMALLEST);
        TwoDimensionSpectrum.main(new String[] { "spectrum.visualization.TwoDimensionSpectrum" });
        ThreeDimensionSpectrumCloudPoints.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumCloudPoints" });
        ThreeDimensionSpectrumTriangles.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumTriangles" });
        ThreeDimensionSpectrumLines.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumLines" });
    }
    
    public static void fullScreenVisualizationDisplay(){
        ComputeVisualSpectrum.setVisualizationMode(VisualizationMode.FULLSCREEN);
        ComputeVisualSpectrum.setMANUAL_FREQUENCY_RESCALE(false);
        ThreeDimensionSpectrumTriangles.main(new String[] { "spectrum.visualization.ThreeDimensionSpectrumTriangles" });
    }
    
    /**
     * Returns all playable mp3 files
     * @param targetPlaylistFolderName
     * @return
     */
    public static List<String> musicPlayList(String targetPlaylistFolderName){
        File targetPlaylistFolder=new File(targetPlaylistFolderName);
        ArrayList<String> musicPlayListResult=new ArrayList<String>();
        for (final File fileEntry : targetPlaylistFolder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                if(fileEntry.getName().endsWith(".mp3")){
                    System.out.println(fileEntry.getAbsolutePath());
                    musicPlayListResult.add(fileEntry.getAbsolutePath());
                }
            }
        }
        return musicPlayListResult;
    }
    
}
