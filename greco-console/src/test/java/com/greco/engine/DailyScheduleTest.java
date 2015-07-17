package com.greco.engine;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.greco.engine.BeforeHandExceededException;
import com.greco.engine.DailySchedule;
import com.greco.engine.InvalidTimeUnitException;
import com.greco.engine.ReservationUnit;
import com.greco.engine.SoMuchTimeException;
import com.greco.engine.TimeUnitUnavailableException;
import com.greco.engine.TooEarlyException;
import com.greco.engine.TooLateException;

public class DailyScheduleTest {
	DailySchedule ds;

	@Before
	public void setUp() throws Exception {
		ds=new DailySchedule();
	}
	/**
	 * Unidad de tiempo mínimo erronea
	 * @throws InvalidTimeUnitException
	 */
	@Test(expected=InvalidTimeUnitException.class)
	public final void testSetMinTime() throws InvalidTimeUnitException  {
		//Unidad de tiempo erronea
		ds.setMintime(1, 0);
	}
	/**
	 * Unidad de tiempo máximo erronea
	 * @throws InvalidTimeUnitException
	 */
	@Test(expected=InvalidTimeUnitException.class)
	public final void testSetMaxTime() throws InvalidTimeUnitException  {
		//Unidad de tiempo erronea
		ds.setMaxtime(1, 0);
	}
	
	/**
	 * Formato de fecha erroneo al asignar inicio de disponibilidad. Fecha=""
	 * @throws ParseException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableFrom()  {
		//Unidad de tiempo erronea
		ds.setAvailableFrom("");
	}
	
	/**
	 * Formato de fecha erroneo al asignar inicio de disponibilidad. Fecha="26:03"
	 * @throws ParseException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableFrom01()  {
		//Unidad de tiempo erronea
		ds.setAvailableFrom("26:03");
	}
	
	/**
	 * Formato de fecha erroneo al asignar inicio de disponibilidad. Fecha="26:03"
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableFrom02()  {
		//Unidad de tiempo erronea
		ds.setAvailableFrom("26:03");
	}
	
	/**
	 * Formato de fecha erroneo al asignar inicio de disponibilidad. Fecha="22061"
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableFrom03()  {
		//Unidad de tiempo erronea
		ds.setAvailableFrom("22061");
	}
	
	/**
	 * Formato de fecha erroneo al asignar fin de disponibilidad. Fecha=""
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableTo()  {
		//Unidad de tiempo erronea
		ds.setAvailableTo("");
	}
	
	/**
	 * Formato de fecha erroneo al asignar fin de disponibilidad. Fecha="26:03"
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableTo01()  {
		//Unidad de tiempo erronea
		ds.setAvailableTo("26:03");
	}
	
	/**
	 * Formato de fecha erroneo al asignar fin de disponibilidad. Fecha="22:61"
	 * @throws ParseException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableTo02()  {
		//Unidad de tiempo erronea
		ds.setAvailableTo("22:61");
	}
	
	/**
	 * Formato de fecha erroneo al asignar fin de disponibilidad. Fecha="22061"
	 * @throws ParseException
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testsetAvailableTo03() throws ParseException  {
		//Unidad de tiempo erronea
		ds.setAvailableTo("22061");
	}
	
	/**
	 * Asignar antelación con unidad no válida.
	 * @throws InvalidTimeUnitException
	 */
	@Test(expected=InvalidTimeUnitException.class)
	public final void testSetBeforehand() throws InvalidTimeUnitException  {
		//Unidad de tiempo erronea
		ds.setBeforehand(1, 0);
	}
/////////////// Reserva entre tiempo máximo y mínimo.
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 2h
	 * Tiempo máximo= 5h
	 * Tiempo de reserva= 1h. 
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable() throws Exception {
		
		
		ds.setMintime(2, DailySchedule.HOUR);
		ds.setMaxtime(5, DailySchedule.HOUR);
		
		//Tiempo solicitado menor que el mínimo.
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
		
	}
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 2h
	 * Tiempo máximo= 5h
	 * Tiempo de reserva= 6h. 
	 */
	@Test(expected=SoMuchTimeException.class)
	public final void testIsAvailable01() throws Exception {
		
		
		ds.setMintime(2, DailySchedule.HOUR);
		ds.setMaxtime(5, DailySchedule.HOUR);
		
	
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 6, DailySchedule.HOUR);
		ds.isAvailable(ru);
		
	}
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 2h
	 * Tiempo máximo= 5h
	 * Tiempo de reserva= 119m. 
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable02() throws Exception {
		
		
		ds.setMintime(2, DailySchedule.HOUR);
		ds.setMaxtime(5, DailySchedule.HOUR);
			
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 119, DailySchedule.MINUTE);
		ds.isAvailable(ru);
		
	}
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 2h
	 * Tiempo máximo= 5h
	 * Tiempo de reserva= 3001m. 
	 */
	@Test(expected=SoMuchTimeException.class)
	public final void testIsAvailable03() throws Exception {
		
		
		ds.setMintime(2, DailySchedule.HOUR);
		ds.setMaxtime(5, DailySchedule.HOUR);
		
	
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 3001, DailySchedule.MINUTE);
		ds.isAvailable(ru);
		
	}
	
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 1d
	 * Tiempo máximo= 2d
	 * Tiempo de reserva= 23h. 
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable04() throws Exception {
		
		
		ds.setMintime(1, DailySchedule.DAY);
		ds.setMaxtime(2, DailySchedule.DAY);
		
	
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 23, DailySchedule.HOUR);
		ds.isAvailable(ru);
		
	}
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 1d
	 * Tiempo máximo= 2d
	 * Tiempo de reserva= 49. 
	 */
	@Test(expected=SoMuchTimeException.class)
	public final void testIsAvailable05() throws Exception {
		
		
		ds.setMintime(1, DailySchedule.DAY);
		ds.setMaxtime(2, DailySchedule.DAY);
		
	
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 49, DailySchedule.HOUR);
		ds.isAvailable(ru);
		
	}
	
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 61m
	 * Tiempo máximo= 89m
	 * Tiempo de reserva= 60m. 
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable06() throws Exception {
		
		
		ds.setMintime(61, DailySchedule.MINUTE);
		ds.setMaxtime(89, DailySchedule.MINUTE);
		
	
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 60, DailySchedule.MINUTE);
		ds.isAvailable(ru);
		
	}
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 61m
	 * Tiempo máximo= 89m
	 * Tiempo de reserva= 90m. 
	 */
	@Test(expected=SoMuchTimeException.class)
	public final void testIsAvailable07() throws Exception {
		
		
		ds.setMintime(61, DailySchedule.MINUTE);
		ds.setMaxtime(89, DailySchedule.MINUTE);
		
	
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 90, DailySchedule.MINUTE);
		ds.isAvailable(ru);
		
	}
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 61m
	 * Tiempo máximo= 89m
	 * Tiempo de reserva= 1h. 
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable08() throws Exception {
		
		
		ds.setMintime(61, DailySchedule.MINUTE);
		ds.setMaxtime(89, DailySchedule.MINUTE);
		
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
		
	}
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 61m
	 * Tiempo máximo= 89m
	 * Tiempo de reserva= 3h. 
	 */
	@Test(expected=SoMuchTimeException.class)
	public final void testIsAvailable09() throws Exception {
		
		
		ds.setMintime(61, DailySchedule.MINUTE);
		ds.setMaxtime(89, DailySchedule.MINUTE);
		
	
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 3, DailySchedule.HOUR);
		ds.isAvailable(ru);
		
	}
	
	/**
	 * Tiempo de reserva fuera del rango de tiempo (mintime, maxtime), asignado al recurso.
	 * Tiempo mínimo= 61m
	 * Tiempo máximo= 89m
	 * Tiempo de reserva= 1D. 
	 */
	@Test(expected=SoMuchTimeException.class)
	public final void testIsAvailable10() throws Exception {
		
		ds.setMintime(61, DailySchedule.MINUTE);
		ds.setMaxtime(89, DailySchedule.MINUTE);
			
		ReservationUnit ru;
		ru=new ReservationUnit(1,DateTime.now().toDate(), 1, DailySchedule.DAY);
		ds.isAvailable(ru);
		
	}
	
//Comprobar que la reserva solicitada está dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	/**
	 * Reserva solicitada dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	 * Disponible desde: 9:00
	 * Disponible hasta: 23:00
	 * Reserva:
	 * - Inicio: 8:59
	 * - Tiempo: 1h
	 */
	@Test(expected=TooEarlyException.class)
	public final void testIsAvailable11() throws Exception {
		ds.setAvailableFrom("09:00");
		ds.setAvailableTo("23:00");
		ReservationUnit ru;
		DateTime dt=new DateTime(2014,8,8,8,59);
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	
	/**
	 * Reserva solicitada dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	 * Disponible desde: 9:00
	 * Disponible hasta: 23:00
	 * Reserva:
	 * - Inicio: 23:30
	 * - Tiempo: 1h
	 */
	@Test(expected=TooLateException.class)
	public final void testIsAvailable12() throws Exception {
		ds.setAvailableFrom("09:00");
		ds.setAvailableTo("23:00");
		ReservationUnit ru;
		DateTime dt=new DateTime(2014,8,8,23,30);
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	/**
	 * Reserva solicitada dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	 * Disponible desde: 9:00
	 * Disponible hasta: 23:00
	 * Reserva:
	 * - Inicio: 22:30
	 * - Tiempo: 1h
	 */
	@Test(expected=TooLateException.class)
	public final void testIsAvailable13() throws Exception {
		ds.setAvailableFrom("09:00");
		ds.setAvailableTo("23:00");
		ReservationUnit ru;
		DateTime dt=new DateTime(2014,8,8,22,30);
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	/**
	 * Reserva solicitada dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	 * Disponible desde: 9:00
	 * Disponible hasta: 23:00
	 * Reserva:
	 * - Inicio: 22:59
	 * - Tiempo: 2m
	 */
	@Test(expected=TooLateException.class)
	public final void testIsAvailable14() throws Exception {
		ds.setAvailableFrom("09:00");
		ds.setAvailableTo("23:00");
		ReservationUnit ru;
		DateTime dt=new DateTime(2014,8,8,22,59);
		ru=new ReservationUnit(1,dt.toDate(), 2, DailySchedule.MINUTE);
		ds.isAvailable(ru);
	}
//Comprobar que la antelación de la reserva coincide con la antelación prefijada para el recurso.
	/**
	 * Reserva solicitada dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	 * Antelación permitida: 2d
	 * Disponible entre las ahora menos 5 horas y ahora más 6 horas.
	 * Reserva:
	 * - Ahora + 3d
	 * - Inicio: 10:00
	 * - Tiempo: 1h
	 */
	@Test(expected=BeforeHandExceededException.class)
	public final void testIsAvailable15() throws Exception {
		ds.setAvailableFrom(DateTime.now().minusHours(5).toString("HH:mm"));
		ds.setAvailableTo(DateTime.now().plusHours(5).toString("HH:mm"));
		ds.setBeforehand(2,DailySchedule.DAY );
		ReservationUnit ru;
		DateTime dt= DateTime.now().plusDays(3);
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	
	/**
	 * Reserva solicitada dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	 * Antelación permitida: 1h
	 * Disponible entre las ahora menos 5 horas y ahora más 6 horas.
	 * Reserva:
	 * - Ahora + 2h
	 * - Inicio: 10:00
	 * - Tiempo: 1h
	 */
	@Test(expected=BeforeHandExceededException.class)
	public final void testIsAvailable16() throws Exception {
		ds.setAvailableFrom(DateTime.now().minusHours(5).toString("HH:mm"));
		ds.setAvailableTo(DateTime.now().plusHours(5).toString("HH:mm"));
		ds.setBeforehand(1,DailySchedule.HOUR );
		ReservationUnit ru;
		DateTime dt= DateTime.now().plusHours(2);
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	
	/**
	 * Reserva solicitada dentro de los márgenes de disponibilidad: hora desde, hora hasta.
	 * Antelación permitida: 30 min
	 * Disponible entre las ahora menos 5 horas y ahora más 6 horas.
	 * Reserva:
	 * - Ahora + 35m
	 * - Inicio: 10:00
	 * - Tiempo: 1h
	 */
	@Test(expected=BeforeHandExceededException.class)
	public final void testIsAvailable17() throws Exception {
		ds.setAvailableFrom(DateTime.now().minusHours(5).toString("HH:mm"));
		ds.setAvailableTo(DateTime.now().plusHours(5).toString("HH:mm"));
		ds.setBeforehand(30,DailySchedule.MINUTE );
		ReservationUnit ru;
		DateTime dt= DateTime.now().plusMinutes(35);
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	
//Comprobación de que el tiempo solicitado es múltiplo exacto de la unidad mínimo de reserva.
	/**
	 * Comprobar que la reserva solicitada es múltiplo exacto de la tiempo mínimo de reserva. 
	 * Tiempo mínimo de reserva 1h
	 * Reserva:
	 * - Inicio: 10:00
	 * - Tiempo: 65 min
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable18() throws Exception {
		ds.setAvailableFrom(DateTime.now().minusHours(5).toString("HH:mm"));
		ds.setAvailableTo(DateTime.now().plusHours(5).toString("HH:mm"));
		ds.setBeforehand(1,DailySchedule.DAY );
		ds.setMintime(1, DailySchedule.HOUR);
		ReservationUnit ru;
		DateTime dt= DateTime.now();
		ru=new ReservationUnit(1,dt.toDate(), 65, DailySchedule.MINUTE);
		ds.isAvailable(ru);
	}
	
	/**
	 * Comprobar que la reserva solicitada es múltiplo exacto de la tiempo mínimo de reserva. 
	 * Tiempo mínimo de reserva 2h
	 * Reserva:
	 * - Inicio: 10:00
	 * - Tiempo: 1h
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable19() throws Exception {
		ds.setAvailableFrom(DateTime.now().minusHours(5).toString("HH:mm"));
		ds.setAvailableTo(DateTime.now().plusHours(5).toString("HH:mm"));
		ds.setBeforehand(1,DailySchedule.DAY );
		ds.setMintime(2, DailySchedule.HOUR);
		ReservationUnit ru;
		DateTime dt= DateTime.now();
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	
	/**
	 * Comprobar que la reserva solicitada es múltiplo exacto de la tiempo mínimo de reserva. 
	 * Tiempo mínimo de reserva 2h
	 * Reserva:
	 * - Inicio: 10:00
	 * - Tiempo: 3h
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable20() throws Exception {
		ds.setAvailableFrom(DateTime.now().minusHours(5).toString("HH:mm"));
		ds.setAvailableTo(DateTime.now().plusHours(5).toString("HH:mm"));
		ds.setBeforehand(1,DailySchedule.DAY );
		ds.setMintime(2, DailySchedule.HOUR);
		ReservationUnit ru;
		DateTime dt= DateTime.now();
		ru=new ReservationUnit(1,dt.toDate(), 3, DailySchedule.HOUR);
		ds.isAvailable(ru);
	}
	
	/**
	 * Comprobar que la reserva solicitada es múltiplo exacto de la tiempo mínimo de reserva. 
	 * Tiempo mínimo de reserva 30h
	 * Reserva:
	 * - Inicio: 10:00
	 * - Tiempo: 35m
	 */
	@Test(expected=TimeUnitUnavailableException.class)
	public final void testIsAvailable21() throws Exception {
		ds.setAvailableFrom(DateTime.now().minusHours(5).toString("HH:mm"));
		ds.setAvailableTo(DateTime.now().plusHours(5).toString("HH:mm"));
		ds.setBeforehand(1,DailySchedule.DAY );
		ds.setMintime(30, DailySchedule.MINUTE);
		ReservationUnit ru;
		DateTime dt= DateTime.now();
		ru=new ReservationUnit(1,dt.toDate(), 35, DailySchedule.MINUTE);
		ds.isAvailable(ru);
	}
	//Validar la disponibilidad en función de la reserva solicitada.
	//Comprobar disponibilidad. Caso positivo
	// Tiempo mínimo de reserva: 1h
	// Tiempo máximo de reserva: 2h
	// Disponible desde: 09:00
	// Disponible hasta: 22:00
	// Antelación: 1D
	// Reservas realizadas en el periodo: ninguna (está todo libre)
	// Reserva:
	// - Día: MAÑANA
	// - Inicio: 09:00
	// - Tiempo: 1h
	@Test
	public final void testIsAvailable22() throws Exception {
		
		ds.setMintime(1, DailySchedule.HOUR);
		ds.setMaxtime(2, DailySchedule.HOUR);
		ds.setAvailableFrom( (new DateTime(DateTime.now().getYear(), 
								DateTime.now().getMonthOfYear(),
								DateTime.now().getDayOfMonth(),
								9,0)).toString("HH:mm") );
		ds.setAvailableTo( (new DateTime(DateTime.now().getYear(), 
				DateTime.now().getMonthOfYear(),
				DateTime.now().getDayOfMonth(),
				22,00)).toString("HH:mm") );
		ds.setBeforehand(1,DailySchedule.DAY );
		ReservationUnit ru;
		DateTime dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
				DateTime.now().getDayOfMonth()+1, 9,0);
		ru=new ReservationUnit(1,dt.toDate(), 1, DailySchedule.HOUR);
		assertTrue("Todos los recursos ocupados. Reserva no disponible",ds.isAvailable(ru));
	}
	
	//Validar la disponibilidad en función de la reserva solicitada.
		//Comprobar disponibilidad. Caso positivo
		// Tiempo mínimo de reserva: 1h
		// Tiempo máximo de reserva: 2h
		// Disponible desde: 09:00
		// Disponible hasta: 22:00
		// Antelación: 1D
		// Reservas realizadas en el periodo: ninguna (está todo libre)
		// Reserva:
		// - Día: MAÑANA
		// - Inicio: 09:00
		// - Tiempo: 120m
		@Test
		public final void testIsAvailable23() throws Exception {
			
			ds.setMintime(1, DailySchedule.HOUR);
			ds.setMaxtime(2, DailySchedule.HOUR);
			ds.setAvailableFrom( (new DateTime(DateTime.now().getYear(), 
									DateTime.now().getMonthOfYear(),
									DateTime.now().getDayOfMonth(),
									9,0)).toString("HH:mm") );
			ds.setAvailableTo( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					22,00)).toString("HH:mm") );
			ds.setBeforehand(1,DailySchedule.DAY );
			ReservationUnit ru;
			DateTime dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 9,0);
			ru=new ReservationUnit(1,dt.toDate(), 120, DailySchedule.MINUTE);
			assertTrue("Todos los recursos ocupados. Reserva no disponible",ds.isAvailable(ru));
		}
		
		//Validar la disponibilidad en función de la reserva solicitada.
		//Comprobar disponibilidad. Caso positivo
		// Tiempo mínimo de reserva: 30h
		// Tiempo máximo de reserva: 120h
		// Disponible desde: 09:00
		// Disponible hasta: 22:00
		// Antelación: 1D
		// Reservas realizadas en el periodo: ninguna (está todo libre)
		// Reserva:
		// - Día: MAÑANA
		// - Inicio: 09:00
		// - Tiempo: 60m
		@Test
		public final void testIsAvailable24() throws Exception {

			ds.setMintime(30, DailySchedule.MINUTE);
			ds.setMaxtime(120, DailySchedule.MINUTE);
			ds.setAvailableFrom( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					9,0)).toString("HH:mm") );
			ds.setAvailableTo( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					22,00)).toString("HH:mm") );
			ds.setBeforehand(1,DailySchedule.DAY );
			ReservationUnit ru;
			DateTime dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 9,0);
			ru=new ReservationUnit(1,dt.toDate(), 60, DailySchedule.MINUTE);
			assertTrue("Todos los recursos ocupados. Reserva no disponible",ds.isAvailable(ru));
		}	
		//Validar la disponibilidad en función de la reserva solicitada.
		//Comprobar disponibilidad. Caso positivo
		// Tiempo mínimo de reserva: 30h
		// Tiempo máximo de reserva: 120h
		// Disponible desde: 09:00
		// Disponible hasta: 22:00
		// Antelación: 2D
		// Reservas realizadas en el periodo: ninguna (está todo libre)
		// Reserva:
		// - Día: MAÑANA
		// - Inicio: 21:30
		// - Tiempo: 30m
		@Test
		public final void testIsAvailable25() throws Exception {

			ds.setMintime(30, DailySchedule.MINUTE);
			ds.setMaxtime(120, DailySchedule.MINUTE);
			ds.setAvailableFrom( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					9,0)).toString("HH:mm") );
			ds.setAvailableTo( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					22,00)).toString("HH:mm") );
			ds.setBeforehand(2,DailySchedule.DAY );
			ReservationUnit ru;
			DateTime dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 21,30);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			assertTrue("Todos los recursos ocupados. Reserva no disponible",ds.isAvailable(ru));
		}	
		
		//Validar la disponibilidad en función de la reserva solicitada.
		//Comprobar disponibilidad. Caso positivo
		// Tiempo mínimo de reserva: 30m
		// Tiempo máximo de reserva: 2h
		// Disponible desde: 09:00
		// Disponible hasta: 22:00
		// Antelación: 2D
		// Reserva:
		// - Día: MAÑANA
		// - Inicio: 21:30
		// - Tiempo: 30m
		// Horas reservadas MAÑANA:
		// - 20:00-20:30; 20:30-21:00; 21:00 a 21:30
				
		@Test
		public final void testIsAvailable26() throws Exception {

			ds.setMintime(30, DailySchedule.MINUTE);
			ds.setMaxtime(2, DailySchedule.HOUR);
			ds.setAvailableFrom( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					9,0)).toString("HH:mm") );
			ds.setAvailableTo( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					22,00)).toString("HH:mm") );
			ds.setBeforehand(2,DailySchedule.DAY );
			ReservationUnit ru;
			DateTime dt;
			//Reservas previas
			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 20,0);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			ds.add(ru);
			
			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 20,30);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			ds.add(ru);
			
			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 21,0);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			ds.add(ru);
			//Reserva solicita
			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 21,30);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			//Comprobamos disponibilidad.
			assertTrue("Todos los recursos ocupados. Reserva no disponible",ds.isAvailable(ru));
			
		}
		//Validar la disponibilidad en función de la reserva solicitada.
		//Comprobar disponibilidad. Caso negativo
		// Tiempo mínimo de reserva: 30m
		// Tiempo máximo de reserva: 2h
		// Disponible desde: 09:00
		// Disponible hasta: 22:00
		// Antelación: 2D
		// Reserva:
		// - Día: MAÑANA
		// - Inicio: 21:00
		// - Tiempo: 30m
		// Horas reservadas MAÑANA (todas la reservas realizadas por el mismo usuario):
		// - 20:00-20:30; 20:30-21:00; 21:00 a 21:30

		@Test
		public final void testIsAvailable27() throws Exception {

			ds.setMintime(30, DailySchedule.MINUTE);
			ds.setMaxtime(2, DailySchedule.HOUR);
			ds.setAvailableFrom( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					9,0)).toString("HH:mm") );
			ds.setAvailableTo( (new DateTime(DateTime.now().getYear(), 
					DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth(),
					22,00)).toString("HH:mm") );
			ds.setBeforehand(2,DailySchedule.DAY );
			ReservationUnit ru;
			DateTime dt;
			//Reservas previas
			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 20,0);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			ds.add(ru);

			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 20,30);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			ds.add(ru);

			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 21,0);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			ds.add(ru);
			//Reserva solicita
			dt= new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(),
					DateTime.now().getDayOfMonth()+1, 21,0);
			ru=new ReservationUnit(1,dt.toDate(), 30, DailySchedule.MINUTE);
			//Comprobamos disponibilidad.
			assertFalse("Reserva disponible aunque ya había una reserva anterior",ds.isAvailable(ru));

		}
}
