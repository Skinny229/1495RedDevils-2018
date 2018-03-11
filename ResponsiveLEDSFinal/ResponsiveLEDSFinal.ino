/*
    Creator: Rodrigo Lopez
    Latest Revision: 2/20/18

    Notes:
      Every change that doesn't include a fundamental change of how the leds animate should be inside the variables before the setup function
      For Example if you change the number of LEDS the animation should adjust for the change in LEDS same as per changes in the delay etc....
      Works!!!1
    
*/


#include "FastLED.h"
#include <Wire.h>

//Creating Constants
#define kNumLedElevator 70
#define kNumLedTop 45
#define kElevatorPin 4
#define kDelayPerTick 5
#define i2cAdress 4
#define kTickCounterMax 199

//Creating Global Variables


//**Creating Top Var
CRGB topLeds[kNumLedTop];

//**Creating Elevator Variables**
//Colors the elevator can be in
const CRGB colorCubeIn = CRGB::Green;
const CRGB colorCubeOut = CRGB::Red;
const CRGB colorCubeMiddle = CRGB::Yellow;
//Actual Elevator
CRGB elevLeds[kNumLedElevator];
//Color of elevator lights
CRGB elevColor = CRGB::Cyan; //<----Default Color or starting color, once roborio boots it should change to colors defined in the consts above
String elevDir = "still";  //<--- default direction of the leds
int elevTimePerUpdate = 50; // in ms **must have remainer of ---> 0 <--- when divided by the kDelayPerTick **

//No No Touch variables
int elevStartingPos = 0;
int tickCounter = 0;
boolean hasRioLink = false;
int cmdReceived = 0;


void setup() {

  initLEDS(); //Set up LEDS for use
  FastLED.setBrightness(80);
  startUpSequence(elevColor); //First Light up sequence
  initI2CLink(); //Setup connection to RoboRIO
  waitForRioLink(); //Lets wait until we are connected to the roboRio
  confirmLinkBlink(); //Blink the lights a color for three times
 // getReady();
  //SETUP COMPLETE moving on to the main control loop
  
}

void loop() {
  // put your main code here, to run repeatedly:


     elevUpdate();
     updateTop(); 


    FastLED.show();
    delay(kDelayPerTick);
    tickCounter ++;
    if(tickCounter == kTickCounterMax)
      tickCounter = 0;
}


void initLEDS(){
    FastLED.addLeds<WS2812B, kElevatorPin, GRB>(elevLeds, kNumLedElevator);
    FastLED.addLeds<WS2812B, 3, GRB>(topLeds, kNumLedTop);
 
 }
 void startUpSequence(CRGB elevSetUpColor){
          for(int i = 0; i < kNumLedTop; i++){
              elevLeds[i] = elevSetUpColor;
              topLeds[i] = elevSetUpColor;
                FastLED.show();
               delay(10);
          }
  }

void initI2CLink(){
    Serial.begin(9600);
    Wire.begin(4);
    Wire.onReceive(requestReceived);
 }
 void waitForRioLink(){
    while(! (hasRioLink)){
      //While we do not have the link keep waiting
            for(int i = 0; i < kNumLedElevator; i++){
        elevLeds[i] = CRGB::Red;
        topLeds[i] = CRGB::Red;
      }
      FastLED.show();
      }
 }

void confirmLinkBlink(){
  for(int i = 0; i < 6; i ++){
    for(int i = 0; i < kNumLedElevator; i++){
        elevLeds[i] = CRGB::Green;
        topLeds[i] = CRGB::Green;
      }
      FastLED.show();
      delay(250);
        for(int i = 0; i < kNumLedElevator; i++){
        elevLeds[i] = CRGB::Black;
        topLeds[i] = CRGB::Black;
      }
      FastLED.show();
      delay(150);
    }
  }

void updateTop(){
    for(int i = 0; i < kNumLedTop; i ++)
      topLeds[i] = elevColor;
  
}

void requestReceived(){
           int cmdReceived = Wire.read();
      if(hasRioLink){
          switch(cmdReceived){
              case 65: elevColor = colorCubeIn; //A
                break;
              case 66: elevColor = colorCubeMiddle; //B
                break;
              case 67: elevColor = colorCubeOut; //C
                  break;
              case 68: elevDir = "up"; break;//D
              
              case 69: elevDir = "still"; break;//E
              
              case 70: elevDir = "down"; break;//F
            }
        }else{
            hasRioLink = true;
          }
 }
void getReady(){
  for(int i = 0; i <= kNumLedElevator; i+=6){
    elevLeds[i] = elevColor;
    elevLeds[i+1] = elevColor;
    elevLeds[i+2] = elevColor;
  }
}
void elevUpdate(){
    if(tickCounter % (elevTimePerUpdate/kDelayPerTick) == 0){
      for(int i = 0; i <= kNumLedElevator; i ++){
            elevLeds[i] = CRGB::Black;
          }
      if(elevDir != "still"){
        if(elevDir == "up"){
                if(elevStartingPos >= 6)
              elevStartingPos = 0;
          fillInThrees();
          elevStartingPos ++;
        }
        if(elevDir == "down"){
               if(elevStartingPos <= -1)
              elevStartingPos = 4;
              fillInThrees();
            elevStartingPos --;
        }  
      }else{
           fillInThrees();
        }
    }
}

void fillInThrees(){
    for(int i = elevStartingPos; i < kNumLedElevator; i += 6){
        elevLeds[i] = elevColor;
        elevLeds[i+1] = elevColor;
        elevLeds[i+2] = elevColor;
      }
  }
