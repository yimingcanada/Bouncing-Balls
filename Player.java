import java.awt.*;

public class Player
{
    // Constants
    private final int walk_x_speed = 3;

    //max space the player can move
    private int maxX;

    // Variables
    private int player_x_pos;
    private int player_y_pos;

    // Counter for animation
    private int picture_counter;
    private int step_counter;

    // boolean values, is player walking
    private boolean walking_left;
    private boolean walking_right;

    // value tells if player looks left (look_left = true) or right (look_left = false)
    private boolean look_left;

    // Images
    private Image mario_stop_left;
    private Image mario_walk_left;
    private Image mario_stop_right;
    private Image mario_walk_right;

    // Size of the player
    private static final int player_image_height = 27;
    private static final int player_image_width = 16;

    private int player_image_startingXPos;

    private Component parent;

    // Constructor
    public Player (int x, int y, Component parent)
    {
	//initialize player positions
	player_x_pos = x;
	player_y_pos = y;
	player_image_startingXPos = x;

	// initialize counter
	picture_counter = 0;
	step_counter = 0;

	// initialize boolean values
	look_left = false;

	this.parent = parent;

    }


    // method sets images
    public void setImages (Image mario_stop_right, Image mario_walk_right,
	    Image mario_stop_left, Image mario_walk_left)
    {
	this.mario_stop_right = mario_stop_right;
	this.mario_walk_right = mario_walk_right;
	this.mario_stop_left = mario_stop_left;
	this.mario_walk_left = mario_walk_left;

    }

    //method to reset player position everytime a new game starts   
    public void playerPosReset ()
    {
	player_x_pos = player_image_startingXPos;
    }


    //set up the maximum x-value the player can move
    public void setMaxX (int pInput)
    {
	maxX = pInput;
    }


    public int getMaxX ()
    {
	return maxX;
    }


    //method to determine the player's current x_pos. 
    public int getPlayerX ()
    {
	return player_x_pos;
    }




    // sets boolean value walking_left
    public void playerWalkLeft (boolean value)
    {
	walking_left = value;
    }


    // sets boolean value walking_right
    public void playerWalkRight (boolean value)
    {
	walking_right = value;
    }


    // method moves player according to the given boolean values (walking_left...)
    public void playerMove ()
    {

	// player walks left
	if (walking_left)
	{
	    player_x_pos -= walk_x_speed;

	    if (player_x_pos <= 0)
	    {
		player_x_pos = 0;
	    }


	    if (step_counter % 15 == 0) 
	    //every 15 steps, picture will change, creating a walking movement
	    {
		picture_counter++;

		if (picture_counter == 2)
		{
		    picture_counter = 0;
		}

		step_counter = 1;
	    }
	    else
	    {
		step_counter++;
	    }

	    look_left = true;
	}
	// player walks right
	else if (walking_right)
	{
	    player_x_pos += walk_x_speed;

	    if (player_x_pos >= maxX - player_image_width)
	    {
		player_x_pos = maxX - player_image_width;
	    }

	    if (step_counter % 15 == 0)
	    //every 15 steps, picture will change, creating a walking movement
	    {
		picture_counter++;

		if (picture_counter == 2)
		{
		    picture_counter = 0;
		}

		step_counter = 1;
	    }
	    else
	    {
		step_counter++;
	    }

	    look_left = false;

	}

    }


    // draw player to the screen
    public void paintPlayer (Graphics g)
    {
	//paints player according to which boolean values are true
	if (walking_right)
	{
	    if (picture_counter == 0)
	    {
		g.drawImage (mario_stop_right, player_x_pos, player_y_pos, parent);
	    }
	    else
	    {
		g.drawImage (mario_walk_right, player_x_pos, player_y_pos, parent);
	    }
	}
	else if (walking_left)
	{
	    if (picture_counter == 0)
	    {
		g.drawImage (mario_stop_left, player_x_pos, player_y_pos, parent);
	    }
	    else
	    {
		g.drawImage (mario_walk_left, player_x_pos, player_y_pos, parent);
	    }
	}
	else
	{
	    if (look_left)
	    {
		g.drawImage (mario_stop_left, player_x_pos, player_y_pos, parent);
	    }
	    else
	    {
		g.drawImage (mario_stop_right, player_x_pos, player_y_pos, parent);
	    }
	}

    }



}
