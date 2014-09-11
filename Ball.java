import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Ball
{
    //Variables
    private int ball_x_pos;
    private int ball_y_pos;
    private int ball_radius;
    private int maxX;
    private int maxY;
    private int ball_x_speed;
    private int ball_y_speed;
    private int ball_speed;
    private Color ballColour;


    //creates Ball, assigns values to values of the ball
    public Ball (int x, int y, int radius, Color randomColour)
    {
	ball_x_pos = x;
	ball_y_pos = y;
	ball_radius = radius;
	ballColour = randomColour;
    }


    //set up the maximum x-value the ball can move
    public void setMaxX (int pInput)
    {
	maxX = pInput;
    }


    public int getMaxX ()
    {
	return maxX;
    }


    //set up the maximum y-value the ball can move
    public void setMaxY (int pInput)
    {
	maxY = pInput;
    }


    public int getMaxY ()
    {
	return maxY;
    }


    //method to get xPos of ball from main class ** xPos always changes since balls
    //constantly move
    public int getXPos ()
    {
	return ball_x_pos;
    }


    //method to get yPos of ball from main class ** yPos always changes since balls
    //constantly move
    public int getYPos ()
    {
	return ball_y_pos;
    }


    //method to get radius of ball from main class ** radius of ball is generated randomly from
    //main class
    public int getRadius ()
    {
	return ball_radius;
    }


    // set up the speed properties for the ball
    // speed of ball is generated randomly in main class
    public void setSpeed (int pInput)
    {
	ball_speed = pInput;
	ball_x_speed = pInput;
	ball_y_speed = -pInput;

    }

    
    public void move ()
    {

	//if ball x_pos hits right side of applet, reverse speed
	if (ball_x_pos > maxX - 2 * ball_radius)
	{
	    ball_x_speed = -ball_speed;
	}
	//if ball x_pos hits left side of applet, reverse speed
	else if (ball_x_pos < 0)
	{
	    ball_x_speed = ball_speed;

	}
	//if ball y_pos hits bottom of applet, reverse speed 
	else if (ball_y_pos > maxY - 2 * ball_radius)
	{
	    ball_y_speed = -ball_speed; 
	}
	//if ball y_pos hits top of applet, reverse speed
	else if (ball_y_pos < 0)
	{
	    ball_y_speed = ball_speed;
	}
     
	//ball_x_pos and y_pos are always changing since they are always moving
	ball_x_pos += ball_x_speed;
	ball_y_pos += ball_y_speed;
    }


    public void drawBall (Graphics g)
    {
	//draws ball
	//ballColour is randomly generated in main
	g.setColor (ballColour);
	g.fillOval (ball_x_pos, ball_y_pos, 2 * ball_radius, 2 * ball_radius);

    }
}

