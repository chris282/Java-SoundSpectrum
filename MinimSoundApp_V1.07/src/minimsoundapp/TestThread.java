/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimsoundapp;

public class TestThread extends Thread {
    int type=0;
    
  public TestThread(){
      super();
  }
    
   public TestThread(String name, int _type){
    super(name);
    type=_type;
  }

  @Override
  public void run(){
      switch(type){
        case 0: 
        TwoDimensionSpectrum twoDimensionSpectrum = new TwoDimensionSpectrum();
        twoDimensionSpectrum.main(new String[] { "minimsoundapp.TwoDimensionSpectrum" });
        break;
        case 1 :
        ThreeDimensionSpectrumCloudPoints threeDimensionSpectrumCloudPoints = new ThreeDimensionSpectrumCloudPoints();
        threeDimensionSpectrumCloudPoints.main(new String[] { "minimsoundapp.ThreeDimensionSpectrumCloudPoints" });
        break;
  }
     
  }
}