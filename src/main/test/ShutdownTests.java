import java.lang.reflect.Method;
import java.util.Calendar;

import org.junit.Test;
import static org.junit.Assert.*;

import main.ComputerShutdown;

public class ShutdownTests {
	
	@Test
	public void testCalendarEarlier() {
		ComputerShutdown cs = new ComputerShutdown();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 50);
		cal.set(Calendar.SECOND, 0);
		Object[] args = new Object[3];
		args[0] = cal.get(Calendar.HOUR);
		args[1] = cal.get(Calendar.MINUTE);
		args[2] = cal.get(Calendar.SECOND);
	
		Calendar execCal = (Calendar) callPrivateMethod(cs, "getCalendar",
				new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE}, args);
		
		assertTrue(cal.after(execCal));
		// We want to have a calendar that is on the same day and same time that we just gave it.
		assertTrue(""+cal.get(Calendar.HOUR)+" "+execCal.get(Calendar.HOUR),
				cal.get(Calendar.HOUR) == execCal.get(Calendar.HOUR));
		
		assertTrue(""+cal.get(Calendar.MINUTE)+" "+execCal.get(Calendar.MINUTE),
				cal.get(Calendar.MINUTE) == execCal.get(Calendar.MINUTE));
		
		assertTrue(""+cal.get(Calendar.SECOND)+" "+execCal.get(Calendar.SECOND),
				cal.get(Calendar.SECOND) == execCal.get(Calendar.SECOND));
		
		assertTrue(""+cal.get(Calendar.DAY_OF_YEAR)+" "+execCal.get(Calendar.DAY_OF_YEAR),
				cal.get(Calendar.DAY_OF_YEAR) == execCal.get(Calendar.DAY_OF_YEAR));
		
	}
	
	@Test
	public void testCalendarLater() {
		ComputerShutdown cs = new ComputerShutdown();
		Calendar cal = Calendar.getInstance();
		cal.roll(Calendar.MINUTE, false);
		Object[] args = new Object[3];
		args[0] = cal.get(Calendar.HOUR);
		args[1] = cal.get(Calendar.MINUTE);
		args[2] = cal.get(Calendar.SECOND);
	
		Calendar execCal = (Calendar) callPrivateMethod(cs, "getCalendar",
				new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE}, args);
		
		assertTrue(cal.before(execCal));
		// We want to have a calendar that is on the next day and same time that we just gave it.
		assertTrue(""+cal.get(Calendar.HOUR)+" "+execCal.get(Calendar.HOUR),
				cal.get(Calendar.HOUR) == execCal.get(Calendar.HOUR));
		
		assertTrue(""+cal.get(Calendar.MINUTE)+" "+execCal.get(Calendar.MINUTE),
				cal.get(Calendar.MINUTE) == execCal.get(Calendar.MINUTE));
		
		assertTrue(""+cal.get(Calendar.SECOND)+" "+execCal.get(Calendar.SECOND),
				cal.get(Calendar.SECOND) == execCal.get(Calendar.SECOND));
		
		assertTrue(""+cal.get(Calendar.DAY_OF_YEAR)+" "+execCal.get(Calendar.DAY_OF_YEAR),
				cal.get(Calendar.DAY_OF_YEAR)+1 == execCal.get(Calendar.DAY_OF_YEAR));
	}
	
	/**
	 * Call a private method in a class and get the return value
	 * 
	 * @param toInvoke	The object to invoke the method on
	 * @param params	The classes of the parameters to pass to the method 
	 * @param arguments	The method arguments
	 * @return			The returned Object from the method invocation
	 */
	private Object callPrivateMethod(Object toInvoke, String method,  Class<?>[] params, Object[] arguments) {
		try {
			Method m = ComputerShutdown.class.getDeclaredMethod(method, params);
			m.setAccessible(true);
			return m.invoke(toInvoke, arguments);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception");
			return null;
		}
	}

}
