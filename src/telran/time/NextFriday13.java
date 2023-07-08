package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;
		res = res.with(ChronoField.DAY_OF_WEEK, 5);
		do {
			res = res.plus(1, ChronoUnit.WEEKS);
		}
		while (res.get(ChronoField.DAY_OF_MONTH) != 13);
				
		return res;
	}

}
