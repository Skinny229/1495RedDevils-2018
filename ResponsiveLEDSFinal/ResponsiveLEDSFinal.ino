/*
    Creator: Rodrigo Lopez
    Latest Revision: 2/18/18

    Notes:
      
    
*/


#include "FastLED.h"
#include <Wire.h>

//Creating Constants
#define kNumLedElevator 100
#define kElevatorPin 4
#define kDelayPerTick 5
#define i2cAdress 4
#define kTickCounterMax 199

//Creating Global Variables
int tickCounter = 0;


//**Creating Elevator Variables**
//Colors the elevator can be in
const CRGB colorCubeIn = CRGB::Green;
const CRGB colorCubeOut = CRGB::Red;
const CRGB colorCubeMiddle = CRGB::Yellow;
//Actual Elevator
CRGB elevLeds[kNumLedElevator];
//Color of elevator lights
CRGB elevColor = CRGB::Cyan; //<----Default Color
String elevDir = "still";  //<--- default direction of the leds
int elevTimePerUpdate = 50; // in ms
int elevStartingPos = 0;

//I2C Link Variables
boolean hasRioLink = false;
int cmdReceived = 0;


void setup() {

  initLEDS(); //Set up LEDS for use
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


    FastLED.show();
    delay(kDelayPerTick);
    tickCounter ++;
    if(tickCounter == kTickCounterMax)
      tickCounter = 0;
}


void initLEDS(){
    FastLED.addLeds<NEOPIXEL, kElevatorPin>(elevLeds, kNumLedElevator);
 
 }
 void startUpSequence(CRGB elevSetUpColor){
          for(int i = 0; i < kNumLedElevator; i++){
              elevLeds[i] = elevSetUpColor;
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
      }
      FastLED.show();
      }
 }

void confirmLinkBlink(){
  for(int i = 0; i < 6; i ++){
    for(int i = 0; i < kNumLedElevator; i++){
        elevLeds[i] = CRGB::Green;
      }
      FastLED.show();
      delay(250);
        for(int i = 0; i < kNumLedElevator; i++){
        elevLeds[i] = CRGB::Black;
      }
      FastLED.show();
      delay(150);
    }
  }


void requestReceived(){
           int cmdReceived = Wire.read();
      if(hasRioLink){
          switch(cmdReceived){
              case 0:
                break;
              case 65: elevColor = CRGB::Green; //A
                break;
              case 66: elevColor = CRGB::Yellow; //B
                break;
              case 67: elevColor = CRGB::Red; //C
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
      if(elevDir != "still"){
        
          
        for(int i = 0; i <= kNumLedElevator; i ++){
            elevLeds[i] = CRGB::Black;
          }
          
        if(elevDir == "up"){
                if(elevStartingPos >= 6)
              elevStartingPos = 0;
        for(int i = elevStartingPos;i < kNumLedElevator; i += 6){
            elevLeds[i] = elevColor;
            elevLeds[i+1] = elevColor;
            elevLeds[i+2] = elevColor;
          }
          elevStartingPos ++;
        }
        if(elevDir == "down"){
               if(elevStartingPos <= -1)
              elevStartingPos = 4;
               for(int i = elevStartingPos;i < kNumLedElevator; i += 6){
            elevLeds[i] = elevColor;
            elevLeds[i+1] = elevColor;
            elevLeds[i+2] = elevColor;
          }
            elevStartingPos --;
        }  
      }  
    }
}
