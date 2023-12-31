package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DateTimeTests {
	@Test
	void test() {
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY EEEE");
		
		System.out.println(birthAS.format(dtf));
		LocalDate barMizva =  birthAS.plusYears(13);
		System.out.println(barMizva.format(dtf));
		assertEquals(barMizva, birthAS.with(new BarMizvaAdjuster()));
		assertThrowsExactly(UnsupportedTemporalTypeException.class,
				() -> LocalTime.now().with(new BarMizvaAdjuster()));
		
	}
	@Test
	void nextFriday13Test() {
		TemporalAdjuster fr13 = new NextFriday13();
		//ZonedDateTime zdt = ZonedDateTime.now();
		LocalDate now = LocalDate.now();
		ZonedDateTime zdt = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 
				0, 0, 0, 0, ZoneId.systemDefault());
		ZonedDateTime fr13Expected = ZonedDateTime.of(2023, 10, 13, 0, 0, 0, 0, ZoneId.systemDefault());
		assertEquals(fr13Expected, zdt.with(fr13));
		LocalDate fr13Expected2 = LocalDate.of(2024, 9, 13);
		LocalDate ld = LocalDate.of(2023, 10, 13);
		assertEquals(fr13Expected2, ld.with(fr13));
		
	}
	@Test
	void canadaCurrentTime() {
		displayCurrentTime("Canada");
		//Date / Time (HH:mm) / Time Zone name
	}

	void displayCurrentTime(String zoneName) {
		Set<String> zoneSet = ZoneId.getAvailableZoneIds();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

		Iterator<String> it = zoneSet.iterator();
		while (it.hasNext()) {
			String zone = it.next();
			if (it.hasNext() && zone.contains(zoneName)) {
				System.out.println(zone+":  "+
						ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(zone)).format(dtf));
			}
		}
	}
}
