package game;
import javax.swing.JFrame;
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("FrameDemo");
		Game g = new Game();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(g);
		frame.setSize(g.WINDOW_WIDTH, g.WINDOW_HEIGHT - g.WINDOW_HEIGHT/10);
		frame.setVisible(true);
		frame.setResizable(false);
		g.setVisible(true);
	}
}