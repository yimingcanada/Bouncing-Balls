/*
/* Yi Ming Zhao 713496, Amr Salem  
   January 19, 2011
   
    This game has 3 different classes: Ball class, Player class, and Main class. These three classes work together to create
   "Bouncing Balls".    
   
   The player class creates the player and controls its movements. It has the x and y positions of the 
   player and it creates certain boundaries for it to walk in. The player consists of 4 images: standing left picture,
   standing right picture, walking left picture, and  walking right picture, There is a step counter so that every 15 steps, 
   the image changes, creating a walking animation. The player class is very essential to the game. Without it, there would
   be no game.
   
   The ball class creates the "Enemies" or balls. It has the x and y positions of each new ball generated and provides 
   boundaries for it to be in. If a ball hits a wall, it will be reflected back. The speed, size, colour and starting
   position of each ball is randomly generated in the main class. In the game, if any ball hits the player, the game will be
   over.
   
   The main class combines both the player class and the ball class, making them function together, at the same time. The main
   class is also where all graphics of the game are created, such as buttons, screen changes, modes, and instructions. The 
   main class moves the player and generates new balls as the levels progress. 
   
   At the starting screen, there are 3 buttons for the user to choose from: normal mode, challenge mode, and instructions. If
   the user chooses normal mode, he or she will play the game normally. If the user chooses challenge mode, he or she 
   will play the game with the controls inverted. If the user chooses instructions, a screen will come up, telling
   the user how to play the game. 
   
   The player can only move left and right and its position is on the bottom of the screen. At first, we had the player to
   move up and down as well, but there was a glitch that occurred if the position of a newly generated ball was exactly on the 
   player, many balls would appear, filling up the screen. We fixed the problem by never allowing the balls to be generated 
   on the player. The only way to avoid this problem was to limit the directional movements of the player to only left and right.
   
   Java objects was learned from http://javacooperation.gmxhome.de/PlatformGameBasicsEng.html and the animation for the 
   player was also learned from that site. We used some of the code from the website to perform the animation of the player
   but, we fully understand how it works. What got our game starting was the moving ball applets from Mr.Sayad. Everything
   else was researched on our own. 
   
   Yi Ming did most of coding/comments. Amr did most of the graphics and helped with some coding. 
   Over all, we split the work 60%/40%.

  
*/



import java.applet.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;

public class Main extends Applet implements Runnable, ActionListener
{
    // variables
    private Thread th;
    private Player player;

    // Variables for key events
    private boolean key_left;
    private boolean key_right;

    // Variables for player images
    private Image mario_walk_left;
    private Image mario_stop_left;
    private Image mario_jump_left;
    private Image mario_walk_right;
    private Image mario_stop_right;
    private Image mario_jump_right;

    //applet size
    private final int appletsize_x = 500;
    private final int appletsize_y = 280;

    //player image size
    public final int player_image_height = 27;
    public final int player_image_width = 16;

    //player image starting position
    private int player_x_pos = appletsize_x / 2 + player_image_width / 2;
    private int player_y_pos = appletsize_y - player_image_height;

    private ArrayList balls;
    private int ballSpeed;
    private int normalScore = 0;
    private int challengeScore = 0;

    private Font font1 = new Font ("Century Gothic", Font.PLAIN, 15);
    private Font font2 = new Font ("Century Gothic", Font.PLAIN, 50);
    private Font font3 = new Font ("Courier New", Font.BOLD, 20);
    private Font font4 = new Font ("Century Gothic", Font.BOLD, 24);
    private Font font5 = new Font ("Comic Sans MS", Font.PLAIN, 55);
    private Font font6 = new Font ("Century Gothic", Font.PLAIN, 20);
    private Font font7 = new Font ("Calibri", Font.PLAIN, 12);
    private Font font8 = new Font ("Calibri", Font.BOLD, 16);

    Random random = new Random ();

    //graphic flags
    private boolean showMainTitle = true;
    private boolean showNormalScore = false;
    private boolean showChallengeScore = false;
    private boolean showNormalTitle = false;
    private boolean showChallengeTitle = false;
    private boolean showLevel = false;
    private boolean showPlayer = false;
    private boolean showGameOver = false;
    private boolean selectNormal = false;
    private boolean selectChallenge = false;
    private boolean selectInstructions = false;

    //level flags
    private boolean level_1, level_2, level_3, level_4, level_5, level_6, level_7, level_8, level_9, level_10, level_11, level_12, level_13, level_14, level_15, level_16, level_17, level_18, level_19, level_20, level_21, level_22 = false;

    private Button normalMode, challengeMode, instructions, restartButton, backButton;

    // double buffering
    private Image dbImage;
    private Graphics dbg;

    public void init ()
    {
	//resize applet
	resize (appletsize_x, appletsize_y);

	setLayout (null);
	setBackground (Color.black);
	//creates player
	player = new Player (player_x_pos, player_y_pos, this);
	//no key pressed
	key_left = false;
	key_right = false;
	// get images
	getImages ();
	// set player images
	player.setImages (mario_stop_right, mario_walk_right, mario_stop_left, mario_walk_left);
	//sets maximum x value player can move
	player.setMaxX (appletsize_x);
	//creats balls, an array list for adding and removing balls
	balls = new ArrayList ();
	//creats buttons
	normalMode = new Button ("Normal Mode");
	challengeMode = new Button ("Challenge Mode");
	instructions = new Button ("Instructions");
	restartButton = new Button ("Restart");
	backButton = new Button ("Back");
	//sets button bounderies
	normalMode.setBounds (210, 115, 100, 30);
	challengeMode.setBounds (210, 150, 100, 30);
	instructions.setBounds (210, 185, 100, 30);
	restartButton.setBounds (210, 163, 100, 30);
	backButton.setBounds (210, 230, 100, 30);
	//adds buttons
	add (normalMode);
	add (challengeMode);
	add (instructions);
	//adds an actionlistener for buttons
	normalMode.addActionListener (this);
	challengeMode.addActionListener (this);
	instructions.addActionListener (this);
	restartButton.addActionListener (this);
	backButton.addActionListener (this);
    }


    private void getImages ()
    {
	//get images from folder
	//assign values to images
	MediaTracker tracker = new MediaTracker (this);

	mario_stop_right = getImage (getCodeBase (), "MarioStandingRight.gif");
	tracker.addImage (mario_stop_right, 1);

	mario_walk_right = getImage (getCodeBase (), "MarioWalkingRight.gif");
	tracker.addImage (mario_walk_right, 2);

	mario_stop_left = getImage (getCodeBase (), "MarioStandingLeft.gif");
	tracker.addImage (mario_stop_left, 4);

	mario_walk_left = getImage (getCodeBase (), "MarioWalkingLeft.gif");
	tracker.addImage (mario_walk_left, 5);

	try
	{
	    tracker.waitForAll ();
	}
	catch (Exception exception)
	{

	}
    }


    public void start ()
    {
	th = new Thread (this);
	th.start ();
    }


    public void stop ()
    {
	th.stop ();
    }


    public void destroy ()
    {
	th.stop ();
    }


    public void run ()
    {
	Thread.currentThread ().setPriority (Thread.MIN_PRIORITY);

	while (true)
	{
	    //gets current player xPos from player class
	    player_x_pos = player.getPlayerX ();
	    //moves player from player class
	    player.playerMove ();
	    //increases balls array list if new balls are created 
	    for (int i = 0 ; i < balls.size () ; i++)
	    {
		int ball_x_pos;
		int ball_y_pos;
		int ball_radius;

		Ball ball = (Ball) balls.get (i);
		//assign values to current ball xPos,yPos, and radius from ball class
		ball_x_pos = ball.getXPos ();
		ball_y_pos = ball.getYPos ();
		ball_radius = ball.getRadius ();

		if (showPlayer == true)
		{
		    if (player_x_pos <= ball_x_pos + 2 * ball_radius && player_x_pos + player_image_width >= ball_x_pos && player_y_pos <= ball_y_pos + 2 * ball_radius && player_y_pos + player_image_height >= ball_y_pos)
			//if the player image gets hit on any side, the game will be over
			{
			    showGameOver = true;
			    gameOver ();
			}
		}
	    }

	    if (showGameOver == false)
		//if the game isn't over...
		{
		    if (selectInstructions == true)
			//if person selects the instructions, scores will be 0 meaning no balls will be created.
			//balls are created according to the score
			{
			    normalScore = 0;
			    challengeScore = 0;
			}
		    else
			//else, if person selects to play the game in normal mode, the normal score will continuously increase
			//by two eaeh time
			{
			    normalScore = normalScore + 2;

			    if (selectChallenge == true)
				//if person selects to play the game in challenge mode, the challenge score will continously increase
				//by two each time
				{
				    challengeScore = challengeScore + 2;
				}
			}
		}
	    //statements to create new balls according to the score
	    //every 1000 increase in score, a new ball is created
	    //createBall () is a method to create ball
	    if (normalScore == 2 || challengeScore == 2)
	    {
		createBall ();
		level_1 = true;
	    }

	    else if (normalScore == 1000 || challengeScore == 1000)
	    {
		createBall ();
		level_2 = true;
		level_1 = false;
	    }
	    else if (normalScore == 2000 || challengeScore == 2000)
	    {
		createBall ();
		level_3 = true;
		level_2 = false;
	    }
	    else if (normalScore == 3000 || challengeScore == 3000)
	    {
		createBall ();
		level_4 = true;
		level_3 = false;
	    }
	    else if (normalScore == 4000 || challengeScore == 4000)
	    {
		createBall ();
		level_5 = true;
		level_4 = false;
	    }
	    else if (normalScore == 5000 || challengeScore == 5000)
	    {
		createBall ();
		level_6 = true;
		level_5 = false;
	    }
	    else if (normalScore == 6000 || challengeScore == 6000)
	    {
		createBall ();
		level_7 = true;
		level_6 = false;
	    }
	    else if (normalScore == 7000 || challengeScore == 7000)
	    {
		createBall ();
		level_8 = true;
		level_7 = false;
	    }
	    else if (normalScore == 8000 || challengeScore == 8000)
	    {
		createBall ();
		level_9 = true;
		level_8 = false;
	    }
	    else if (normalScore == 9000 || challengeScore == 9000)
	    {
		createBall ();
		level_10 = true;
		level_9 = false;
	    }
	    else if (normalScore == 10000 || challengeScore == 10000)
	    {
		createBall ();
		level_11 = true;
		level_10 = false;
	    }
	    else if (normalScore == 11000 || challengeScore == 11000)
	    {
		createBall ();
		level_12 = true;
		level_11 = false;
	    }
	    else if (normalScore == 12000 || challengeScore == 12000)
	    {
		createBall ();
		level_13 = true;
		level_12 = false;
	    }
	    else if (normalScore == 13000 || challengeScore == 13000)
	    {
		createBall ();
		level_14 = true;
		level_13 = false;
	    }
	    else if (normalScore == 14000 || challengeScore == 14000)
	    {
		createBall ();
		level_15 = true;
		level_14 = false;
	    }
	    else if (normalScore == 15000 || challengeScore == 15000)
	    {
		createBall ();
		level_16 = true;
		level_15 = false;
	    }
	    else if (normalScore == 16000 || challengeScore == 16000)
	    {
		createBall ();
		level_17 = true;
		level_16 = false;
	    }
	    else if (normalScore == 17000 || challengeScore == 17000)
	    {
		createBall ();
		level_18 = true;
		level_17 = false;
	    }
	    else if (normalScore == 18000 || challengeScore == 18000)
	    {
		createBall ();
		level_19 = true;
		level_18 = false;
	    }
	    else if (normalScore == 19000 || challengeScore == 19000)
	    {
		createBall ();
		level_20 = true;
		level_19 = false;
	    }
	    else if (normalScore == 20000 || challengeScore == 20000)
	    {
		createBall ();
		level_21 = true;
		level_20 = false;
	    }
	    else if (normalScore == 21000 || challengeScore == 21000)
	    {
		createBall ();
		level_22 = true;
		level_21 = false;
	    }

	    //loops balls arrayList, moving each ball in the arrayList
	    for (int i = 0 ; i < balls.size () ; i++)
	    {
		Ball pBall = (Ball) balls.get (i);
		pBall.move ();
	    }
	    // repaint applet
	    repaint ();

	    try
	    {
		Thread.sleep (10);
	    }
	    catch (InterruptedException ex)
	    {
		// do nothing
	    }
	    Thread.currentThread ().setPriority (Thread.MAX_PRIORITY);
	}
    }


    public boolean keyDown (Event e, int key)
    {
	if (key == Event.LEFT)
	    //if left key is pressed in normal mode, player will walk left
	    //if left key is pressed in challenge mode, player will walk right
	    {
		if (selectNormal == true)
		{
		    key_left = true;
		    player.playerWalkLeft (true);
		}
		else if (selectChallenge == true)
		{
		    key_right = true;
		    player.playerWalkRight (true);
		}
	    }
	//if right key is pressed in normal mode, player will walk right
	//if right key is pressed in challenge mode, player will walk left
	else if (key == Event.RIGHT)
	{
	    if (selectNormal == true)
	    {
		key_right = true;
		player.playerWalkRight (true);
	    }
	    else if (selectChallenge == true)
	    {
		key_left = true;
		player.playerWalkLeft (true);
	    }
	}
	return true;
    }


    public boolean keyUp (Event e, int key)
    {
	if (key == Event.LEFT)
	    //if left key is lifted up in normal mode, player will not move left
	    //if left key is lifted up in challenge mode, player will not move right
	    {
		if (selectNormal == true)
		{
		    key_left = false;
		    player.playerWalkLeft (false);
		}

		else if (selectChallenge == true)
		{
		    key_right = false;
		    player.playerWalkRight (false);
		}
	    }
	//if right key is lifted up in normal mode, player will not move right
	//if right key is lifted up in challenge mode, player will not move left
	else if (key == Event.RIGHT)
	{
	    if (selectNormal == true)
	    {
		key_right = false;
		player.playerWalkRight (false);
	    }
	    else if (selectChallenge == true)
	    {
		key_left = false;
		player.playerWalkLeft (false);
	    }
	}
	return true;
    }


    public void update (Graphics g)
    {
	//double buffering
	if (dbImage == null)
	{
	    dbImage = createImage (this.getSize ().width, this.getSize ().height);
	    dbg = dbImage.getGraphics ();
	}

	dbg.setColor (getBackground ());
	dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height);

	dbg.setColor (getForeground ());
	paint (dbg);

	g.drawImage (dbImage, 0, 0, this);
    }


    public void paint (Graphics g)
    {
	// draw player
	if (showPlayer)
	{
	    player.paintPlayer (g);
	}
	//loops balls arrayList, drawing each ball in the arrayList
	for (int i = 0 ; i < balls.size () ; i++)
	{
	    Ball pBall = (Ball) balls.get (i);
	    pBall.drawBall (g);
	}

	//***************************************
	//below are booleans values. If they are set true, they will draw what they are assigned to draw

	if (showMainTitle)
	{
	    g.setColor (Color.white);
	    g.setFont (font5);
	    g.drawString ("Bouncing Balls", 80, 80);

	    g.setFont (font7);
	    g.drawString ("Java Applet game by: Yi Ming and Amr", 288, 275);
	}

	if (showNormalScore)
	{
	    g.setColor (Color.white);

	    g.setFont (font1);
	    g.drawString ("Score: " + normalScore, 395, 22);
	}

	else if (showChallengeScore)
	{
	    g.setColor (Color.white);

	    g.setFont (font1);
	    g.drawString ("Score: " + challengeScore, 395, 22);
	}

	if (showGameOver)
	{
	    g.setColor (Color.white);

	    g.setFont (font2);
	    g.drawString ("GAME OVER", 105, 125);

	    if (selectNormal)
	    {
		g.setFont (font6);
		g.drawString ("Final Score: " + normalScore, 175, 150);
	    }
	    else if (selectChallenge)
	    {
		g.setFont (font6);
		g.drawString ("Final Score: " + challengeScore, 175, 150);
	    }
	}
	if (showNormalTitle)
	{
	    g.setFont (font4);
	    g.drawString ("NORMAL MODE", 165, 25);
	}
	else if (showChallengeTitle)
	{
	    g.setFont (font4);
	    g.drawString ("CHALLENGE MODE", 155, 25);
	}
	if (selectInstructions)
	{
	    g.setColor (Color.white);
	    g.setFont (font8);
	    g.drawString ("Instructions:", 30, 25);

	    g.setFont (font7);
	    g.drawString ("Before playing, you will be presented with a Starting Screen. From here, you can ", 30, 45);
	    g.drawString ("choose to play Normal Mode or Challenge Mode. ", 30, 60);
	    g.drawString ("In Normal mode, you will be able to move your player using the arrow keys, right", 30, 80);
	    g.drawString ("and left.", 30, 95);
	    g.drawString ("In Challenge mode, your movements are reversed. Pressing the right arrow key will", 30, 115);
	    g.drawString ("move you left and pressing the left arrow key will move you right.", 30, 130);
	    g.drawString ("The objective of the game is to survive as long as possible. An additional ball will", 30, 150);
	    g.drawString ("be added each level. Your final score will be displayed when you are hit.", 30, 165);
	    g.drawString ("Good Luck!", 30, 190);
	}
	g.setColor (Color.white);
	g.setFont (font1);

	if (showLevel)
	{
	    if (level_1)
	    {
		g.drawString ("Level: 1", 5, 20);
	    }
	    else if (level_2)
	    {
		g.drawString ("Level: 2", 5, 20);
	    }
	    else if (level_3)
	    {
		g.drawString ("Level: 3", 5, 20);
	    }
	    else if (level_4)
	    {
		g.drawString ("Level: 4", 5, 20);
	    }
	    else if (level_5)
	    {
		g.drawString ("Level: 5", 5, 20);
	    }
	    else if (level_6)
	    {
		g.drawString ("Level: 6", 5, 20);
	    }
	    else if (level_7)
	    {
		g.drawString ("Level: 7", 5, 20);
	    }
	    else if (level_8)
	    {
		g.drawString ("Level: 8", 5, 20);
	    }
	    else if (level_9)
	    {
		g.drawString ("Level: 9", 5, 20);
	    }
	    else if (level_10)
	    {
		g.drawString ("Level: 10", 5, 20);
	    }
	    else if (level_11)
	    {
		g.drawString ("Level: 11", 5, 20);
	    }
	    else if (level_12)
	    {
		g.drawString ("Level: 12", 5, 20);
	    }
	    else if (level_13)
	    {
		g.drawString ("Level: 13", 5, 20);
	    }
	    else if (level_14)
	    {
		g.drawString ("Level: 14", 5, 20);
	    }
	    else if (level_15)
	    {
		g.drawString ("Level: 15", 5, 20);
	    }
	    else if (level_16)
	    {
		g.drawString ("Level: 16", 5, 20);
	    }
	    else if (level_17)
	    {
		g.drawString ("Level: 17", 5, 20);
	    }
	    else if (level_18)
	    {
		g.drawString ("Level: 18", 5, 20);
	    }
	    else if (level_19)
	    {
		g.drawString ("Level: 19", 5, 20);
	    }
	    else if (level_20)
	    {
		g.drawString ("Level: 20", 5, 20);
	    }
	    else if (level_21)
	    {
		g.drawString ("Level: 21", 5, 20);
	    }
	    else if (level_22)
	    {
		g.drawString ("Level: 22", 5, 20);
	    }

	}


    }



    public void actionPerformed (ActionEvent evt)
    {

	//if user clicks any button, that button will perform its assigned method
	if (evt.getSource () == normalMode)
	{
	    selectNormal ();
	}
	if (evt.getSource () == challengeMode)
	{
	    selectChallenge ();
	}
	if (evt.getSource () == restartButton)
	{
	    returnToStart ();
	}
	if ((evt.getSource () == instructions))
	{
	    selectInstructions ();
	}
	if ((evt.getSource () == backButton))
	{
	    returnToStart ();
	}
    }


    //method for creating balls
    private void createBall ()
    {
	//generates random RGB values and assigns it to the ball when they are created
	Color randomColor = new Color (random.nextInt (255), random.nextInt (255), random.nextInt (255));

	//ball will be created randomly on the appletsize_x, and appletsize_y - 50
	int ball_x_pos = random.nextInt (appletsize_x);
	int ball_y_pos = random.nextInt (appletsize_y - 50);

	//ball radius will be randomly generated between 5-12
	int ball_radius = random.nextInt (12) + 5;

	//creates a new ball
	Ball ball = new Ball (ball_x_pos, ball_y_pos, ball_radius, randomColor);
	//sets maximum X the ball can move for ball class
	ball.setMaxX (appletsize_x);
	//sets maximum Y the ball can move for ball class
	ball.setMaxY (appletsize_y);
	//ball speed will be randomly generated between 1-2
	ballSpeed = random.nextInt (2) + 1;
	//sets the ball speed for ball class
	ball.setSpeed (ballSpeed);
	//add the ball to balls array list
	balls.add (ball);
    }


    private void resetGame ()
	//method to reset the game
    {
	//all balls in balls array list will be reset to 0
	balls.clear ();
	//normal score and challenge score will be reset to 0
	normalScore = 0;
	challengeScore = 0;
	//players position will be reset
	player.playerPosReset ();
    }


    private void returnToStart ()
	//method when game restarts and returns to the start screen
    {
	add (normalMode);
	add (challengeMode);
	add (instructions);
	remove (restartButton);
	remove (backButton);
	resetGame ();
	showGameOver = false;
	showPlayer = false;
	selectNormal = false;
	selectChallenge = false;
	showNormalScore = false;
	showChallengeScore = false;
	showLevel = false;
	showChallengeTitle = false;
	showNormalTitle = false;
	showMainTitle = true;
	selectInstructions = false;
    }


    private void selectNormal ()
	//method when normal mode is selected
    {
	remove (normalMode);
	remove (challengeMode);
	remove (instructions);
	remove (backButton);

	resetGame ();
	showGameOver = false;
	showPlayer = true;
	showNormalScore = true;
	showChallengeScore = false;
	selectNormal = true;
	showLevel = true;
	showNormalTitle = true;
	showMainTitle = false;
	selectInstructions = false;
    }


    private void selectChallenge ()
	//method when challenge mode is selected
    {

	remove (normalMode);
	remove (challengeMode);
	remove (instructions);
	remove (backButton);

	resetGame ();
	showGameOver = false;
	showPlayer = true;
	showNormalScore = false;
	showChallengeScore = true;
	selectChallenge = true;
	showLevel = true;
	showChallengeTitle = true;
	showMainTitle = false;
    }


    private void gameOver ()
	//method when game is over
    {
	remove (normalMode);
	remove (challengeMode);
	remove (instructions);
	remove (backButton);
	add (restartButton);
	showPlayer = false;
	showNormalScore = false;
	showChallengeScore = false;
	showLevel = true;
	showMainTitle = false;
	selectInstructions = false;
    }


    private void selectInstructions ()
	//method when person selects the instructions
    {
	remove (normalMode);
	remove (challengeMode);
	remove (instructions);
	add (backButton);
	showPlayer = false;
	showNormalScore = false;
	showChallengeScore = false;
	showLevel = false;
	showMainTitle = false;
	selectInstructions = true;
	balls.clear ();
    }
}



