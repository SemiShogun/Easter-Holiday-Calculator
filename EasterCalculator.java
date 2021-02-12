import java.util.Scanner;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

/**
* Easter Holiday Application Calculator.
*
* This calculator is used to calculate Easter Sunday, Pentecost, Sächsilüüte and Ascension Day.
*
* @author Jamie Lam
* @version 1.5
* @date 19. August 2019
*/

public class EasterCalculator {

	public static LocalDate easter;

	public static void main(String[] args) {
		/** This Constructor asks the user to input a year.
		* The User would then receive answers to when the easter holidays for the corresponding year would take place.
		*/
		Scanner scanner = new Scanner(System.in);
		HolidayApplication holiday = new HolidayApplication();
		System.out.print("\n" + "Ferienberechner" + "\n\n" + "Bitte eine Jahreszahl eingeben... ");

		while(!scanner.hasNextInt()) { //If the user does not type down an integer as an input, the user would have to type down an input again until the "while" loop detects an integer.
			System.out.print("\n" + "Bitte nochmals eine richtige Jahreszahl eingeben... ");
			scanner.next();
		}
		int inputyear = scanner.nextInt(); //User types down their input.

		int days = holiday.ostersonntag(inputyear);
		holiday.saechsilueti(inputyear, days);
		holiday.ascension_day();
		holiday.pentecost();
	}

	public static int ostersonntag(int year) {
		/** This method calculates the remaining days until "Easter Sunday" or in German, "Ostersonntag".
		*/
		int K = year / 100; //die Säkularzahl
		int M = 15 + ((3 * K) + 3) / 4 - ((8 * K) + 13) / 25; //die säkulare Mondschaltung.
		int S = 2 - ((3 * K) + 3) / 4; //die säkulare Sonnenschaltung.
		int A = year % 19; //den Mondparameter.
		int D = ((19 * A) + M) % 30; //den Keim für den ersten Vollmond im Frühling.
		int R = (D + A / 11) / 29; //die kalendarische Korrekturgrösse.
		int OG = 21 + D - R; //die Ostergrenze.
		int SZ = 7 - (year + year / 4 + S) % 7; //den ersten Sonntag im März.
		int OE = 7 - (OG - SZ) % 7; //die Entfernung des Ostersonntags von der Ostergrenze.
		int easterSunday = OG + OE; //das Datum des Ostersonntags als Märzdatum.
		System.out.print("\n\n\n" + "#######################################################################" + "\n\n");
		System.out.print("Die Osterferien von den Jahr "+ (year) + " befindet sich am..." + "\n\n\n");
		easter = LocalDate.of(year, 3, 1).plusDays(easterSunday - 1);
		System.out.print("Ostersonntag: " + easter + "\n");
		
		return easterSunday;
	}

	public static void saechsilueti(int year, int days) {
		/** This method calculates the remaining days until "Saechsilueti".
		*/
		LocalDate saechsiluetiCorrected;
		LocalDate date = 
			YearMonth.of(year, 4) //4 is the month "April"
				.atDay(days - 31)
				.with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY)); //Calculates the third monday of april.

		int easterMonday = days + 1; //osterMontag is Easter Monday
		int goodFriday = days - 2; //karfreitag is Good Friday
		int thirdMonday = date.getDayOfMonth();

		if (thirdMonday >= goodFriday - 4 && thirdMonday <= goodFriday + 2) { //If the third monday of the month is on the same week as karfreitag, then "Saechsilueti" is on the 2nd Monday of the corresponding month.
			saechsiluetiCorrected = date.minusDays(7);
		} else if (thirdMonday >= easterMonday && thirdMonday <= easterMonday + 6) { //If the third monday of the month is on the same week as karfreitag, then "Saechsilueti" is on the 4th Monday of the corresponding month.
			saechsiluetiCorrected = date.plusDays(7);
		} else { //Otherwise, "Saechsilueti" is on the 3rd Monday of the corresponding month.
			saechsiluetiCorrected = date;
		}

		System.out.print("Sächsilüüte: " + saechsiluetiCorrected + "\n");
	}

	public static void ascension_day() {
		/** This method calculates the remaining days until "Ascension Day" or in German, "Auffahrt".
		*/
		LocalDate ascensionDayCorrected = easter.plusDays(39);
		System.out.print("Auffahrt: " + ascensionDayCorrected + "\n");
	}

	public static void pentecost() {
		/** This method calculates the remaining days until "Pentecost" or in German, "Pfingsten"
		*/
		LocalDate pentecostCorrected = easter.plusDays(49);
		System.out.print("Pfingsten: " + pentecostCorrected);
		System.out.print("\n\n" + "#######################################################################" + "\n\n\n");
	}

}
