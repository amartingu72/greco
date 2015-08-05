package com.greco.engine;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.greco.entities.Reservation;

public class ReservationUnit implements ITimeUnits{
		
		/**
		 * Usuario que solicita/posee la reserva.
		 */
		private int userId;
		/**
		 * Tiempo de reserva.
		 */
		protected Interval interval;
		
		public ReservationUnit() {
			userId=-1;
			interval=null;
		} 	
		
		public ReservationUnit(int userId, DateTime fromDate, DateTime toDate){
			this.userId=userId;
			this.interval=new Interval(fromDate, toDate);
			
		}
		
		
		public ReservationUnit(Reservation reservation) {
			userId=reservation.getUser().getId();
			interval=new Interval(new DateTime(reservation.getFromDate()), 
									new DateTime(reservation.getToDate()));
		}
		
		/**
		 * 
		 * @param start Hora de inicio en formato HH:mm  (formato H24)
		 * @param time Tiempo de reserva.
		 * @param timeunit  Constantes del interface ITimeUnits.
		 */
		public ReservationUnit(int userId, Date start, int time, int timeunit) 
			throws InvalidTimeUnitException {
			Duration d;
			switch (timeunit) {
			case DAY:
				d=Duration.standardDays(time);
				break;
			case HOUR:
				d=Duration.standardHours(time);
				break;
			case MINUTE:
				d=Duration.standardMinutes(time);
				break;
			default:
				throw new InvalidTimeUnitException(timeunit);
			}
			this.userId=userId;
			interval=new Interval(new DateTime(start),d);
		}

		/**
		 * Devuelve true si el periodo de nuestro objeto contiene o se solapa con el pasado como parámetro
		 * @param obj De tipo ReservationUnit
		 * @return True si contiene o se solapa.
		 */
		@Override
		public boolean equals(Object obj) {
			
			return interval.overlaps(  ((ReservationUnit)obj).getInterval()  );
		}
		/**
		 * Compara las unidades de reserva
		 * @param ru Unidad de reserva.
		 * @return Devuelve "true" si es anterior y no se solapa con la pasada como parámetro.
		 */
		
		public boolean isBefore(ReservationUnit ru){
			return interval.isBefore(ru.getInterval());
		}
		/**
		 * Compara las unidades de reserva.
		 * @param ru Unidad de reserva.
		 * @return evuelve "true" si es posterior y no se solapa con la pasada como parámetro.
		 */
		public boolean isAfter(ReservationUnit ru) {
			return interval.isAfter(ru.getInterval());
		}


		public Interval getInterval() {
			return interval;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}
		
		public Duration getDuration() {
			return interval.toDuration();
		}
		@Override
		public String toString() {		
			String msg;
			DateTimeFormatter fmt = DateTimeFormat.forPattern("E, dd/M/yyyy ");
			msg = fmt.print(interval.getStart());
			fmt=DateTimeFormat.forPattern("HH:mm");
			msg+=fmt.print(interval.getStart())+ "-" + fmt.print(interval.getEnd());
			return msg;
		}
		
}
