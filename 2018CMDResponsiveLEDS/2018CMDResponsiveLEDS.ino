#include "FastLED.h"
#include <Wire.h>

#define kNumLedElevator 53
#define kElevatorPin 4
#define kDelayPerTick 20
#define i2cAdress 4
#define kTickCounterMax 199
#define testPin 4
#define testNum 24

bool isRequestReceived,roboLink = false;
int tickCounter = 0;
CRGB testRing[testNum];
void setup() {
  // put your setup code here, to run once:
//  FastLED.addLeds<WS2812B, kElevatorPin>(elevLeds, kNumLedElevator);
  FastLED.addLeds<NEOPIXEL, testPin>(testRing, testNum);
  for(int i = 0; i < 24; i ++){
    testRing[i] = CRGB::Red;
    }
  FastLED.show();
  //delay(2000);
 // LedInitSequence();
 Serial.begin(9600);
  Wire.begin(4);
  //waitForRioLink();
  isRequestRecieved = false;
  Wire.onReceive(requestReceived);
}
int commandReceived;

void loop() {
  // put your main code here, to run repeatedly:

  


    

      
    FastLED.show();      
    delay(kDelayPerTick);
    tickCounter ++;
    if(tickCounter == kTickCounterMax)
      tickCounter = 0;
}

bool updateStateRing = false;
void updateLights(int stageToUpdate){
  switch(stageToUpdate){
      case 65: 
             if(updateStateRing){
                color = CRGB::Red;
                updateStateRing = false;
              }else{
                color = CRGB::Green;
                updateStateRing = true
              }
        break;
    
    }
  
}


bool stateBlink = false;
CRGB color;

void constBlink(){

    if(! stateBlink){
      color = statColor;
      stateBlink = true;
    }else{
      color = CRGB::Black;
      stateBlink = false;
     }
  for(int i = 0; i < 24; i++)
    testRing[i] = color;    
}
void requestReceived(){
  isRequestReceived = true;
  commandReceived = Wire.read();
}
void LedInitSequence(){
}
void waitForRioLink(){
    while(! roboLink){
      
      }
  }
