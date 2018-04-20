package game;
import misc.Heart;
import ships.Asteroid;

public class Event {
	Game game;										// The game
	Wave wave;										// The wave this event belongs to
	private int selectedEvent;						// The selected event
	private int eventSize;							// The size of the selected event (how many times it is triggered)
	private int eventDelay = 50;					// The delay between triggers of the event
	private int eventTrigger = 0;					// The event trigger (used as a switch)
	private int counter;							// A basic counter
	public Event(Wave w) {
		wave = w;
		game = wave.getGame();
		selectedEvent = game.rgen.nextInt(1);		// Randomly select one of the events
		counter = 0;
		getNext();									// Initialize the event (or if it a one time event, this just triggers it)
	}

	public void AsteroidEvent() {
		switch(eventTrigger) {
			case 0:
				eventSize = 10;
				eventDelay = 50;
				break;
			default:
				new Asteroid(game, game.rgen.nextInt()%500 + 1500);
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
//			new Heart(game, game.rgen.nextInt()%500 +1500);
//			break;
//		}
//	}

	public void update() {
		if(counter%eventDelay == 0 && eventTrigger < eventSize && !wave.onlyEvent()) {		// If the counter meets the delay and there are more events to be triggered and there are not only event enemies left in the wave
			getNext();																		// Trigger the next event
		}
		counter++;																			// Increment the counter
	}

	public void getNext() {		// This function goes thru all of the possible events in a switch, calls the respective event, and then increments the trigger
		switch(selectedEvent) {
			case 0:
				AsteroidEvent();
				break;
		}
		eventTrigger++;
	}
}
