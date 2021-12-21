import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
class MontyHallMC{
  
  //Durstenfeld shuffle method
    public static String[] shuffleArray(String[] ar){
      //Initiate rnd
      Random rnd = ThreadLocalRandom.current();
      //Swapping elements in the array
      for (int i=ar.length-1; i>0; i--){
        int index = rnd.nextInt(i+1);
        //Swap
        String a = ar[index];
        ar[index] = ar[i];
        ar[i] = a;
      }
      return ar;
    }
  
  public static void main(String[] args) {
    //Initialization
    //Create string array of the three doors, filled with two goats and a car
    String[] doors = {"car", "goat", "goat"};
    //Create float array to store the probabilities
    float[] switchWinProbabilities = new float[2];
    float[] stickWinProbabilities = new float[2];
    
    //Monte Carlo simulation
    float stickWin = 0;
    float switchWin = 0;
    Float errorSwitch = 1f;
    Float errorStick = 1f;
    int it = 0;
    //Do the iteration until both errors are less than 1e-5
    while(errorStick>1e-5f || errorSwitch>1e-5f){
      it += 1;
      //Randomly placing car and goats in the three doors
      String[] doorsSwapped = shuffleArray(doors);
      //Random door chosen by the contestant
      Random rd = new Random();
      int chosenDoor = rd.nextInt(3);

      //Record win/lose
      if (doorsSwapped[chosenDoor]=="car"){
        stickWin += 1;
      }else{
        switchWin += 1;
      }
      //Calculate probabilities
      float stickWinProb = stickWin / (it+1);
      float switchWinProb = switchWin / (it+1);
      
      //Store in the float arrays
      if (it==1){
        stickWinProbabilities[1] = stickWinProb;
        switchWinProbabilities[1] = switchWinProb;
      }else{
        stickWinProbabilities[0] = stickWinProbabilities[1];
        switchWinProbabilities[0] = switchWinProbabilities[1];
        
        stickWinProbabilities[1] = stickWinProb;
        switchWinProbabilities[1] = switchWinProb;
      }
      //Compute errors
      errorStick = Math.abs((stickWinProbabilities[1]-stickWinProbabilities[0]) / stickWinProbabilities[0]);
      //If error is infinite, assign as 1
      if (errorStick.isInfinite()){
        errorStick = 1f;
      }
      errorSwitch = Math.abs((switchWinProbabilities[1]-switchWinProbabilities[0]) / switchWinProbabilities[0]);
      //If error is infinite, assign as 1
      if (errorSwitch.isInfinite()){
        errorSwitch = 1f;
      }
    }
    //Outputs
    System.out.println("Number of iterations needed: "+it);
    System.out.println("Stick Win Probabilities: "+stickWinProbabilities[1]);
    System.out.println("Error: "+errorStick);
    System.out.println("Switch Win Probabilities: "+switchWinProbabilities[1]);
    System.out.println("Error: "+errorSwitch);
  }
}
