package misc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import game.Game;
import misc.GraphicsPane;

public class LeaderBoardPane extends GraphicsPane {
	private GLabel leaderBoard = new GLabel("LEADERBOARD", 407, 100);
	private File scores = new File("scores.txt");
	private boolean fileExists = scores.exists();
	private GImage background;
	
	private ArrayList<GLabel>scoreList = new ArrayList<GLabel>();
	private List<Integer>allScores = new ArrayList<Integer>();
	
	public LeaderBoardPane(Game app) throws IOException{
		this.program = app;
		background = new GImage("levels/betweenbackground.jpg");
		background.setSize(program.getWidth(), program.getHeight());
		leaderBoard.setColor(Color.MAGENTA);
		leaderBoard.setFont("Aerial-Bold-60");
		leaderBoard.sendToFront();
	}

	@Override
	public void showContents() {
		try {
			arrangeScores();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		program.add(background);
		program.add(leaderBoard);
		for(GLabel labels: scoreList) {
			program.add(labels);
		}
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(leaderBoard);
		for(GLabel labels: scoreList) {
		program.remove(labels);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		program.switchToMenu();
		
	}
	public void arrangeScores() throws IOException {
		if(fileExists) {
			allScores.removeAll(allScores);
			scoreList.removeAll(scoreList);
			try {
				fileSort();
				makeLabel();
				setScoreColor();
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
	public void fileSort() throws IOException {
		Scanner scan = new Scanner(scores);
		while(scan.hasNextLine()) {
			String nextLine = scan.nextLine();
			allScores.add(Integer.parseInt(nextLine));
		}
		scan.close();
		Collections.sort(allScores);
		Collections.reverse(allScores);
	}
	
	public void makeLabel() {
		for(int i = 0; i< allScores.size() && i < 5; i++) {
			GLabel score = new GLabel(null, 600, 150 + 50 * i);
			score.setLabel(allScores.get(i).toString());
			scoreList.add(score);
			
		}
	}
	
	public void setScoreColor() {
		for (GLabel label : scoreList) {
			label.setColor(Color.RED);
			label.setFont("Arial-Bold-40");
		}
	}

}
