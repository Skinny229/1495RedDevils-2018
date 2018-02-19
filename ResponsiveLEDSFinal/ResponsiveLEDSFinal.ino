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
CRGB elevColor = CRGB::Yellow; //<----Default Color
String elevDir = "up";  //<--- default direction of the leds
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
  getReady();
  //SETUP COMPLETE moving on to the main control loop
  
}

void loop() {
  // put your main code here, to run repeatedly:


     elevUpdate(elevColor,elevDir);


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
  for(int i = 0; i < 10; i ++){
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
      if(hasRioLink){
          int cmdReceived = Wire.read();
          switch(cmdReceived){
              case 0: elevColor = CRGB::Red;
                break;
              case 65: elevColor = CRGB::Green; 
                break;
              case 66: elevColor = CRGB::Yellow;
                break;
              case 67: elevColor = CRGB::Red; 
                  break;
              case 4: elevDir = "up"; break;
              case 5: elevDir = "still"; break;
              case 6: elevDir = "down"; break;
            }
        }else{
            hasRioLink = true;
          }
 }
void getReady(){
  
}
void elevUpdate(CRGB color,String dir){
    if(tickCounter % (elevTimePerUpdate/kDelayPerTick) == 0){
      if(dir != "still"){
        if(dir == "up"){
        //evens = color
        for(int i = elevStartingPos;i <= kNumLedElevator; i += 6){
            elevLeds[i] = color;
            elevLeds[i+1] = color;
            elevLeds[i+2] = color;
          }
        for(int i = elevStartingPos+3; i <= kNumLedElevator; i +=6){
            elevLeds[i] = CRGB::Black;
            elevLeds[i+1] = CRGB::Black;
            elevLeds[i+2] = CRGB::Black;
          }
          elevStartingPos ++;
        }
      }  
    }
    if(elevStartingPos == kNumLedElevator)
      elevStartingPos = 0;
}
