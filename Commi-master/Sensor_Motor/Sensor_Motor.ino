#include <Servo.h> 
Servo myservo;        //creates servo object
const int echoPin1 = 9;   //sets echopin1 @pin 9
const int trigPin1 = 8;  //sets trigpin1 @pin 8 
const int echoPin2 = 12;    //sets echopin2 @pin 12
const int trigPin2 = 11;   //sets trigpin2 @pin 11
const int servo = 13;     //sets servo @pin 13 
long Rightduration, Leftduration, Rightinch, Leftinch; //establishes the variables of the duration and sets distance in inches
int threshold = 15; //Sets the sensor threshold at 10 inches
int angle = 80; //Sets the Initial angle

void setup() 
{
  myservo.attach(13); //attaches the servo on pin 13
}

void loop()
{
  pinMode(trigPin1, OUTPUT);   //trigpin1 is set as output
  digitalWrite(trigPin1, LOW);  //sets the trigpin1 to give low pulse
  delayMicroseconds(3);      //duration is 3 microseconds
  digitalWrite(trigPin1, HIGH);   //sets the trigpin1 to give high pulse
  delayMicroseconds(5);     //duration is 5 microseconds
  digitalWrite(trigPin1, LOW);  
  Rightduration = pulseIn(echoPin1, HIGH); //reads high pulse
  pinMode(trigPin2, OUTPUT);  //trigpin2 is set as output 
  digitalWrite(trigPin2, LOW);  //sets the trigpin2 to give low pulse
  delayMicroseconds(3);       //duration is 3 microseconds
  digitalWrite(trigPin2, HIGH);   //sets the trigpin2 to give high pulse
  delayMicroseconds(5);       //duration is 5 microseconds
  digitalWrite(trigPin2, LOW);  
  Leftduration = pulseIn(echoPin2, HIGH); //reads high pulse
  //this will convert the elapsed time into the distance
  Rightinch = microsecondsToInches(Rightduration);  
  Leftinch = microsecondsToInches(Leftduration);
  follow();   //follows the movement
}
long microsecondsToInches(long microseconds)
{ 
// The speed of sound is 340 m/s or 73.746 microseconds per inch.
// The ping travels out and back, so to find the distance of the
// object we take half of the distance traveled.
  return microseconds / 74 / 2;
}
void follow() //conditions for the follow command
{
  if (Leftinch <= threshold || Rightinch <= threshold)
  {
    if (Leftinch + 1 < Rightinch) 
    {
      angle = angle - 1;
    }
    if (Rightinch + 1 < Leftinch)
    {
      angle = angle + 1;
    }
  }
  if (angle > 180)
  {
    angle = 180;
  }
  if (angle < 0)
  {
    angle = 0;
  }
  myservo.write(angle);
}
