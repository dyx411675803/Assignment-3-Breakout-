/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 8;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/* Create necessary objects */
	GRect paddle;
	GOval ball;
	GLabel remaining;
	
/* Create velocity instance variables*/
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double vx;
	private double vy;
	
/* x,y represent the ball's coordinates */
	private double x;
	private double y;
	
/* number of bricks*/
	int bricks = NBRICKS_PER_ROW * NBRICK_ROWS;
	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		while (true){
			moveTheBall();
			checkCollision();
			pause(10);
		}
	}
	
	@Override
	 public void init(){
		setSize(WIDTH, HEIGHT);
		addMouseListeners();
		genBricks();
		genPaddle();
		genBall();
		genLabels();
		genRandomVelocity();
	}
	
	private void genBricks(){
		for (int i=1; i<=NBRICK_ROWS; i++){
			for (int j=1; j<=NBRICKS_PER_ROW; j++){
				GRect rects = new GRect((j - 1) * (BRICK_WIDTH + BRICK_SEP),
						BRICK_Y_OFFSET + (i - 1) * (BRICK_HEIGHT + BRICK_SEP), BRICK_WIDTH, BRICK_HEIGHT);
				rects.setFilled(true);
				switch(i){
				case 1: case 2:
					rects.setFillColor(Color.RED);
					break;
				case 3: case 4:
					rects.setFillColor(Color.ORANGE);
					break;
				case 5: case 6:
					rects.setFillColor(Color.YELLOW);
					break;
				case 7: case 8:
					rects.setFillColor(Color.GREEN);
					break;
				case 9: case 10:
					rects.setFillColor(Color.CYAN);
					break;
				}
				add(rects);
			}
		}
	}
	
	private void genPaddle(){
		paddle = new GRect(0, HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setFillColor(Color.BLACK);
		add(paddle);
	}
	
	private void genBall(){
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT - PADDLE_Y_OFFSET - 2 * BALL_RADIUS,
				2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setFillColor(Color.BLACK);
		add(ball);
	}
	
	private void genRandomVelocity(){
		vx = rgen.nextDouble(1.0, 3.0);
		vy = 3.0;
		if (rgen.nextBoolean(0.5)) vx = -vx;
	}
	
	private void moveTheBall(){
		ball.move(vx, vy);
	}
	
	private void genLabels(){
		remaining = new GLabel("สฃำเ:" + bricks, 50, 20);
		add(remaining);
	}
	
	private void checkCollision(){
		x = ball.getX();
		y = ball.getY();
		if (x <= 0 || x>= WIDTH - BALL_RADIUS * 2){
			vx = -vx;
		}
		if (y <= 0 || y >= HEIGHT - BALL_RADIUS * 2){
			vy = -vy;
		}
		GObject colObj = getCollisionObj();
		if (colObj != null && colObj != paddle){
			remove(colObj);
			bricks -= 1;
			remaining.setLabel("สฃำเ:" + bricks);
		}
	}
	
	private GObject getCollisionObj(){
		GObject objN = getElementAt(x + BALL_RADIUS, y - 0.01);
		GObject objW = getElementAt(x - 0.01, y + BALL_RADIUS);
		GObject objE = getElementAt(x + 2 * BALL_RADIUS + 0.01, y + BALL_RADIUS);
		GObject objS = getElementAt(x + BALL_RADIUS, y + 2 * BALL_RADIUS + 0.01);
		if (objN != null) {
			vy = -vy;
			return objN;
		}
		else if (objW != null) {
			vx = -vx;
			return objW;
		}
		else if (objE != null) {
			vx = -vx;
			return objE;
		}
		else if (objS != null){
			vy = -vy;
			return objS;
		}
		else return null;
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		int x = e.getX();
		// Set a variable to set the mouse at the central of paddle
		int xx = x - PADDLE_WIDTH / 2; 
		if(xx >= 0 && xx <= WIDTH - PADDLE_WIDTH){
			paddle.setLocation(x - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET);
		}
	}
}
