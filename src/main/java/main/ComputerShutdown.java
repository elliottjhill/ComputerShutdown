package main;

import java.io.IOException;
import java.util.Calendar;

/**
 * This is a class to shutdown a computer using {@link Runtime}. It is possible to run this class from the command line
 * 
 * @author Elliott
 */
public class ComputerShutdown implements Runnable {
	
	/**
	 * Main method, will instantiate the class and call the {@link #shutdown()} command
	 * @param args
	 */
	public static void main(String[] args) {
		new ComputerShutdown().run();
	}
	
	/**
	 * Get the DOS Command to run to shutdown the computer
	 * 
	 * @return the DOS Command to run to shutdown the computer
	 */
	private String getCommand() {
		return "shutdown /s";
	}

	@Override
	public void run() {
		// Turn off at 23:00
		Calendar cal = getCalendar(23, 0, 0);
		long turnOffAt = cal.getTime().getTime();
		
		while(System.currentTimeMillis() < turnOffAt) {
			try {
				// 60 seconds
				Thread.sleep(60*1000);
			} catch (InterruptedException ie) {
				// Nothing to do here, just carry on waiting
			}
		}
		
		Runtime r = Runtime.getRuntime();
		try {
			r.exec(getCommand());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			// Quit, computer stays on
		}
	}
	
	/**
	 * Get a {@link Calendar} set to the next valid time where the time of day will be hour:minute:second
	 * 
	 * @param hour		The hour of the day to set the calendar to, this will be between 0 and 23 (e.g. 24 hr clock)
	 * @param minute	The minute of the day to set the calendar to
	 * @param second	The second of the day to set the calendar to
	 */
	private Calendar getCalendar(int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.HOUR_OF_DAY) >= hour 
				&& cal.get(Calendar.MINUTE) >= minute
				&& cal.get(Calendar.SECOND) >= second) {
			cal.roll(Calendar.DAY_OF_MONTH, true);
		}
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		
		return cal;
	}

}
