package game;
import misc.Heart;
import ships.Asteroid;

public class Event {
	Game game;
	Wave wave;
	private int selectedEvent;
	private int eventSize;
	private int eventDelay = 50;
	private int eventTrigger = 0;
	private int counter;
	public Event(Wave w) {
		wave = w;
		game = wave.getGame();
		selectedEvent = game.rgen.nextInt(1);
		counter = 0;
		getNext();
	}

	public void AsteroidEvent() {
		switch(eventTrigger) {
			case 0:
				eventSize = 10;
				eventDelay = 50;
				break;
			default:
				Asteroid a = new Asteroid(game, game.rgen.nextInt()%500 + 1500);
				a.setEventEnemy(true);
				break;
		}
	}
//	public void HeartEvent() {
//		switch (eventTrigger) {
//		case 0:
//			eventSize = 1;
//			eventDelay = 50;
//			break;
//		default:
//			new Heart(game,game.rgen.nextInt()%500 +1500);
//			break;
//		}
//	}

	public void update() {
		if(counter%eventDelay == 0 && eventTrigger < eventSize && !wave.onlyEvent()) {		// If an asteroid wave is triggered, and the delay satisfies
			getNext();
		}
		counter++;
	}

	public void getNext() {
		switch(selectedEvent) {
			case 0:
				AsteroidEvent();
				break;
		}
		eventTrigger++;
	}
}
