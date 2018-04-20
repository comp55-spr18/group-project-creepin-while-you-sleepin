package misc;
import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GObject;
import game.Game;

public class MenuPane extends GraphicsPane {
	private GButton easyButton;
	private GButton hardButton;
	private GButton exitButton;
	private GButton infoButton;
	private GButton muteButton;
	private GButton unmuteButton;
	private GButton musicMuteButton;
	private GButton musicUnmuteButton;
	private GButton leaderBoardButton;
	private GButton shootTestButton;
	private GImage background;
	private GImage instructions;
	
	public MenuPane(Game app) {
		super();
		program = app;
		double scaleX = program.WINDOW_WIDTH/(1920/100.0);
		double scaleY = program.WINDOW_HEIGHT/(1080/100.0);
		easyButton = new GButton("EASY", 4*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		exitButton = new GButton("EXIT", .5*scaleX, .5*scaleY, scaleX, scaleY);
		hardButton = new GButton("HARD", 13*scaleX, 6*scaleY, 2*scaleX, 2*scaleY);
		infoButton = new GButton("INSTRUCTIONS", 8.5*scaleX, 6*scaleY, 2*scaleX, 2*scaleY/3);
		muteButton = new GButton("MUTE SOUND", 8.5*scaleX, infoButton.getY() + 2*scaleY/3, 2*scaleX, 2*scaleY/3);
		unmuteButton = new GButton("UNMUTE SOUND", muteButton.getX(), muteButton.getY(), muteButton.getWidth(), muteButton.getHeight());
		musicMuteButton = new GButton("MUTE MUSIC", 8.5*scaleX, muteButton.getY() + 2*scaleY/3, 2*scaleX, 2*scaleY/3);
		musicUnmuteButton = new GButton("UNMUTE MUSIC", musicMuteButton.getX(), musicMuteButton.getY(), musicMuteButton.getWidth(), musicMuteButton.getHeight());
		leaderBoardButton = new GButton("LEADER BOARD", 8.5*scaleX, musicMuteButton.getY()+ 2*scaleY/3,2*scaleX,2*scaleY/3);
		shootTestButton = new GButton("TEST", 17.5*scaleX, .5*scaleY, scaleX, scaleY);
		easyButton.setFillColor(Color.GREEN);
		hardButton.setFillColor(Color.RED);
		exitButton.setFillColor(Color.WHITE);
		infoButton.setFillColor(Color.CYAN);
		muteButton.setFillColor(Color.LIGHT_GRAY);
		unmuteButton.setFillColor(Color.LIGHT_GRAY);
		musicMuteButton.setFillColor(Color.LIGHT_GRAY);
		musicUnmuteButton.setFillColor(Color.LIGHT_GRAY);
		leaderBoardButton.setFillColor(Color.MAGENTA);
		shootTestButton.setFillColor(Color.PINK);
		background = new GImage("newmainmenu.jpg");
		background.setSize(program.getWidth(), program.getHeight());
		instructions = new GImage("instructionspage.jpg");
		instructions.setSize(program.getWidth(), program.getHeight());
		
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(easyButton);
		program.add(hardButton);
		program.add(exitButton);
		program.add(infoButton);
		program.add(leaderBoardButton);
		program.add(shootTestButton);
		if(!program.mute) {
			program.add(unmuteButton);
			program.add(muteButton);
		} else {
			program.add(muteButton);
			program.add(unmuteButton);
		}
		if(!program.musicMute) {
			program.add(musicUnmuteButton);
			program.add(musicMuteButton);
		} else {
			program.add(musicMuteButton);
			program.add(musicUnmuteButton);
		}
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(easyButton);
		program.remove(hardButton);
		program.remove(exitButton);
		program.remove(infoButton);
		program.remove(muteButton);
		program.remove(unmuteButton);
		program.remove(musicMuteButton);
		program.remove(musicUnmuteButton);
		program.remove(shootTestButton);
		program.remove(leaderBoardButton);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == easyButton) {
			program.easy = true;
			program.startGame();
			program.player.move(e);
		}
		if (obj == hardButton) {
			program.easy = false;
			program.startGame();
			program.player.move(e);
		}
		if (obj == exitButton) {
			System.exit(0);
		}
		if (obj == infoButton) {
			program.add(instructions);
		}
		if (obj == muteButton) {
			program.mute = true;
			unmuteButton.sendToFront();
			mouseMoved(e);
		}
		if (obj == unmuteButton) {
			program.mute = false;
			muteButton.sendToFront();
			mouseMoved(e);
		}
		if (obj == musicMuteButton) {
			program.musicMute = true;
			program.audio.pauseSound("music", "menu.mp3");
			musicUnmuteButton.sendToFront();
			mouseMoved(e);
		}
		if (obj == musicUnmuteButton) {
			program.musicMute = false;
			program.audio.playSound("music", "menu.mp3", true);
			musicMuteButton.sendToFront();
			mouseMoved(e);
		}
		if (obj == instructions) {
			program.remove(instructions);
		}
		if (obj == leaderBoardButton) {
			program.switchToLeaderBoard();
		}
		// (obj == shootTestButton) {
		//	program.shootTest;
		//}
	}
}
