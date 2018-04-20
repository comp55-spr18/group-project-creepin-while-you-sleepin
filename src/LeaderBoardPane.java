import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import game.Game;
import misc.GraphicsPane;

public class LeaderBoardPane extends GraphicsPane {
	private Game program;
	private GLabel leaderBoard = new GLabel("LEADERBOARD", 400, 400);
	private GRect scoreDisplay = new GRect(220, 60, 270, 520);
	private File scores = new File("scores.txt");
	private boolean fileExists = scores.exists();
	
	private ArrayList<GLabel>scoreList = new ArrayList<GLabel>();
	private List<Integer>allScores = new ArrayList<Integer>();
	
	public LeaderBoardPane(Game app) throws IOException{
		this.program = app;
		scoreDisplay.setFilled(true);
		scoreDisplay.setColor(Color.CYAN);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub

	}
	public void fileSort() throws IOException {
		
	}
	
	public void makeLabel() {
		for(int i = 0; i < 11; i++) {
			GLabel score = new GLabel(null, 340, 160 + 100 * i);
			score.setLabel(allScores.get(i).toString());
			scoreList.add(score);
		}
	}
	
	public void setScoreColor() {
		for (GLabel label : scoreList) {
			label.setColor(Color.RED);
		}
	}

}
