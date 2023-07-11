package telran.time.application;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {
	private static final int TITLE_OFFSET = 8;
	static DayOfWeek[] daysOfWeek = DayOfWeek.values();

	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstWeekDay());
		printDays(recordArguments.month(), recordArguments.year(), recordArguments.firstWeekDay());
	}

	private static void printDays(int month, int year, DayOfWeek firstWeekDay) {
		int nDays = getNumberOfDays(month, year);
		int currentWeekDay = getFirstWeekDay(month, year, firstWeekDay);
		printOffset(currentWeekDay);
		for (int day = 1; day <= nDays; day++) {
			System.out.printf("%4d",day);
			currentWeekDay++;
			if(currentWeekDay > 7) {
				currentWeekDay = 1;
				System.out.println();
			}
		}
	}

	private static void printOffset(int currentWeekDay) {
		System.out.printf("%s", " ".repeat(4 * (currentWeekDay-1)));
	}

	private static int getFirstWeekDay(int month, int year, DayOfWeek firstWeekDay) {
		int weekDayNumber = LocalDate.of(year, month, 1).get(ChronoField.DAY_OF_WEEK);
		if (firstWeekDay != null) {
			int offset = firstWeekDay.getValue() - 1;
			weekDayNumber -= offset;
			if (weekDayNumber <= 0)
				weekDayNumber += 7;
		}
		return weekDayNumber;
	}

	private static int getNumberOfDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek firstDayInWeek) {
		
		int offset = firstDayInWeek != null ?  firstDayInWeek.getValue() - 1 : 0;
		DayOfWeek[] locDaysOfWeek = new DayOfWeek[7];
		
		for (int i = 0; i < daysOfWeek.length - offset; i++) {
			locDaysOfWeek[i] = daysOfWeek[i + offset];
		}

		for (int i = 0; i < offset; i++) {
			locDaysOfWeek[i + daysOfWeek.length - offset] = daysOfWeek[i];
		}
		
		System.out.print("  ");
		Arrays.stream(locDaysOfWeek).forEach(dw -> System.out.printf("%s ",
				dw.getDisplayName(TextStyle.SHORT, Locale.getDefault())));
		System.out.println();
	}

	private static void printTitle(int monthNumber, int year) {
		Month month = Month.of(monthNumber);
		String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
		System.out.printf("%s%s, %d\n", " ".repeat(TITLE_OFFSET),monthName, year);
	}

	private static RecordArguments getRecordArguments(String[] args) throws Exception {
		LocalDate ld = LocalDate.now();
		int month = args.length > 0 ? getMonth(args[0]) : ld.get(ChronoField.MONTH_OF_YEAR);
		int year = args.length > 1 ? getYear(args[1]) : ld.get(ChronoField.YEAR);
		
		String message = "";
		DayOfWeek dw = null;
		try {
		if (args.length > 2) {
			dw = DayOfWeek.valueOf(args[2].toUpperCase());
		}
		} catch (IllegalArgumentException e) {
			message = "invalid day of week";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		
		return new RecordArguments(month, year, dw);
	}

	private static int getYear(String yearStr) throws Exception {
		String message = "";
		int year = 0;
		try {
			year = Integer.parseInt(yearStr);
			if(year < 0) {
				message = "year must be a positive number";
			}
			
		} catch (NumberFormatException e) {
			message = "year must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return year;
	}

	private static int getMonth(String monthStr) throws Exception {
		String message = "";
		int month = 0;
		try {
			month = Integer.parseInt(monthStr);
			if(month < 1 || month > 12) {
				message = "month must be in the range [1-12]";
			}
			
		} catch (NumberFormatException e) {
			message = "month must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return month;
	}

}
