// Calculation Equations:

//  Aav = (V2-V1) ΔD
//  V2 = V1 + (A)ΔT
//  ΔD = ((V1+V2)/2) ΔT
//  ΔD = V2(ΔT) - 1/2(A)ΔT^2
//  V2^2 = V1^2 + 2(A)ΔD
//  ΔD = V1(ΔT) + 1/2(A)ΔT^2

/** Calculation class
  * Model that performs calculations
  * @since 6/05/2017
  * @author William San */
public class Calculation extends Object
{
  private GUI view;               //The view for the simulation
  
  private double time;            //The time of the projectile motion (seconds)
  private double time2;           //The second time of the projectile motion (quadratic) (seconds)
  private double velocity1;       //The inital speed of the projectile (meters/second)
  private double velocity2;       //The final speed of the projectile (meters/second)
  private double displacementX;   //The horizontal displacement of the projectile (meters)
  private double displacementY;   //The vertical displacement of the projectile (meters)
  private double angle1;          //The inital velocity's angle above or below the horizontal (above +, below -) (degrees)
  private double angle2;          //The final velocity's angle above or below the horizontal (above +, below -) (degrees)'
  private double velocity1Y;      //The y-component of projectile's initial speed (meters/second)
  private double velocity1X;      //The x-component of projectile's initial speed (meters/second)
  private double velocity2Y;      //The y-component of projectile's final speed (meters/second)
  private double velocity2X;      //The x-component of projectile's final speed (meters/second)
  
  private boolean missing;        //Determines if there are still any values to calculate
  private boolean timeB;          //Determines if time is present
  private boolean velocity1B;     //Determines if initial velocity is present
  private boolean velocity1YB;    //Determines if initial velocity is present
  private boolean velocity2B;     //Determines if final velocity is present
  private boolean velocity2YB;    //Determines if final velocity is present
  private boolean velocityXB;     //Determines if any velocity is present (v1X = v2X)
  private boolean displacementXB; //Determines if displacement in the x direction is present
  private boolean displacementYB; //Determines if displacement in the y direction is present

/** Default Constructor */
public Calculation()
{
  super();
   
  this.missing = true;
  this.timeB = false;
  this.velocity1B = false;
  this.velocity1YB = false;
  this.velocity2B = false;
  this.velocity2YB = false;
  this.velocityXB = false;
  this.displacementXB = false;
  this.displacementYB = false;
}

/** Sets view for the calculations */
public void setGUI(GUI aView)
{
  this.view = aView;
}


/** Get the time
  * @return Time of projectile motion */
public double getTime()
{
  return (this.time);
}

/** Get the initial velocity
  * @return The inital speed of the projectile (meters/seconds) */
public double getVelocity1()
{
  return (this.velocity1);
}

/** Get the final velocity
  * @return The final speed of the projectile (meters/seconds) */
public double getVelocity2()
{
  return (this.velocity2);
}

/** Get the displacement in the X direction
  * @return The horizontal displacement of the projectile (meters) */
public double getDisplacementX()
{
  return (this.displacementX);
}

/** Get the displacement in the Y direction
  * @return The vertical displacement of the projectile (meters) */
public double getDisplacementY()
{
  return this.displacementY;
}

/** Get the inital velocity's angle
  * @return The inital velocity's angle above or below the horizontal (above +, below -) (degrees) */
public double getAngle1()
{
  return (this.angle1);
}

/** Get the final velocity's angle
  * @return The final velocity's angle above or below the horizontal (above +, below -) (degrees) */
public double getAngle2()
{
  return (this.angle2);
}


/** Sets the time to the argument provided 
  * @aTime The time */
public void setTime(double aTime)
{
  this.time = aTime;
  timeB = true;
}

/** Sets the initial velocity to the argument provided 
  * @aVelocity1 The initial velocity */
public void setVelocity1(double aVelocity1)
{
  this.velocity1 = aVelocity;
  velocity1B = true;
}

/** Sets the final velocity to the argument provided 
  * @aVelocity1 The final velocity */
public void setVelocity2(double aVelocity2)
{
  this.velocity2 = aVelocity;
  velocity2B = true;
}

/** Sets the displacement x to the argument provided 
  * @aDisplacementX The displacement in the x direction */
public void setDisplacementX(double aDisplacementX)
{
  this.displacementX = aDisplacementX;
  displacementXB = true;
}

/** Sets the displacement y to the argument provided 
  * @aDisplacementY The displacement in the y direction */
public void setDisplacementY(double aDisplacementY)
{
  this.displacementY = aDisplacementY;
  displacementYB = true;
}

/** Sets the initial angle to the argument provided 
  * @anAngle1 The initial angle */
public void setAngle1(double anAngle1)
{
  this.angle1 = anAngle;
  velocity1Y = velocity1*Math.sin(angle1);
  velocity1X = velocity1*Math.cos(angle1);
  velocity2X = velocity1X;
  
  velocity1B = true;
  velocityXB = true;
}

/** Sets the final angle to the argument provided 
  * @anAngle2 The final angle */
public void setAngle2(double anAngle2)
{
  this.angle2 = anAngle;
  velocity2Y = velocity2*Math.sin(angle2);
  velocity2X = velocity2*Math.cos(angle2);
  velocity1X = velocity2X;
  
  velocity2B = true;
  velocityXB = true;
}


/** Determines which operation and calculation to perform based on given variables */
public void calculate()
{ 
  //Solve for time
  if (timeB == false)
  {
    calcTime();
  }
  
  //Solve for remaining info
  while (missing)
  {
    if (velocityXB && displacementXB && velocity1B && velocity2B && displacementYB)
    {
      missing = false;
    }
    else
    {
      missing = true;
    }
    
    if (velocityXB == false)
    {
      calcV1X();
    }
    else if (displacementXB == false)
    {
      calcDX();
    }
    if (velocity1YB == false)
    {
      calcV1Y();
    }
    else if (velocity2YB == false)
    {
      calcV2Y();
    }
    else if (displacementY == false)
    {
      calcDY();
    }
  }
}


/** Calculates the time */
public void calcTime()
{
  //Calculate time from x direction
  if (velocityXB && displacementXB)
  {
    time = displacementX/velocity1X;
  }
  
  
  //Calculate time from y direction
  else if (velocity1YB && velocity2YB)
  {
    time = (velocity2Y-velocity1Y)/-9.8;
  }
  
  else if (velocity1YB && displacementYB)
  {
    if (velocity1Y == 0)
    {
      time = Math.sqrt((2*displacementY)/-9.8);
    }
    else if (displacementY <= 0)
    {
      time = (-velocity1Y+Math.sqrt((Math.pow(velocity1Y,2))-(4*(-9.8/2)*(-displacementY))))/-9.8;
      //Don't use negative time
      if (time >= 0)
      {
      }
      else
      {
        time = (-velocity1Y-Math.sqrt((Math.pow(velocity1Y,2))-(4*(-9.8/2)*(-displacementY))))/-9.8;
      }
    }
    //Quadratic equation (2 solutions)
    else
    {
      time = (-velocity1Y+Math.sqrt((Math.pow(velocity1Y,2))-(4*(-9.8/2)*(-displacementY))))/-9.8;
      time2 = (-velocity1Y-Math.sqrt((Math.pow(velocity1Y,2))-(4*(-9.8/2)*(-displacementY))))/-9.8;
    }
  }
  
  else if (velocity2YB && displacementYB)
  {
    if (velocity2Y == 0)
    {
      time = Math.sqrt((2*displacementY)/9.8);
    }
    else
    {
      time = (-velocity2Y+Math.sqrt((Math.pow(velocity2Y,2))-(4*(9.8/2)*displacementY)))/9.8;
      //Don't use negative time
      if (time >= 0)
      {
      }
      else
      {
        time = (-velocity2Y+Math.sqrt((Math.pow(velocity2Y,2))-(4*(9.8/2)*displacementY)))/9.8;
      }
    }
  }
  
  timeB = true;
}

/** Calculates the initial velocity's x component */
public void calcV1X()
{
  
  velocity1B = true;
}

/** Calculates the initial velocity's y component */
public void calcV1Y()
{
  if (displacementYB && )
  {
    
  }
  else if (velocity2
  velocity1B = true;
}

/** Calculates the initial velocity */
public void calcV1()
{
  velocity1B = true;
}

/** Calculates the final velocity's x component */
public void calcV2X()
{
  velocity1B = true;
}

/** Calculates the final velocity's y component */
public void calcV2Y()
{
  velocity2B = true;
}

/** Calculates the final velocity */
public void calcV2()
{
  velocity2B = true;
}

/** Calculates the displacement x */
public void calcDX()
{
  displacementXB = true;
}

/** Calculates the displacement y */
public void calcDY()
{
  displacementYB = true;
}


/** Updates the view in the GUI */
public void updateView()
{
  view.update();
}

/** Resets all values for new calculations */
public void reset()
{
  missing = true;
  blank = true;
  updateView();
  //
}
}
