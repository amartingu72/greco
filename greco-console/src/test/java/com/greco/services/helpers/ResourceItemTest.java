package com.greco.services.helpers;

import static org.junit.Assert.*;



import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;


public class ResourceItemTest {
	
	@Before
	public void setUp() throws Exception {
		
	}
	/**
	 * Comprobación de constructor
	 */
	@Test
	public final void test01()   {
		ResourceItem ri=new ResourceItem(1, "Recurso01", "Pista de tenis", "");
		String sWeeklyAvailavility=ri.getWeeklyAvailabilityString();
		assertTrue("No crea bien la lista de disponibilidad semanal en el constructor",
				sWeeklyAvailavility.equals("1111111"));
	}
	
	
	/**
	 * Comprobación del set con string.
	 */
	@Test
	public final void test02()   {
		ResourceItem ri=new ResourceItem(1, "Recurso01", "Pista de tenis", "");
		ri.setWeeklyAvailability("0101010");
		String sWeeklyAvailavility=ri.getWeeklyAvailabilityString();
		assertTrue("No asigna bien la disponibilidad partiendo de una cadena",
				sWeeklyAvailavility.equals("0101010"));
	}
	/**
	 * Comprobación de disponibilidad de un día. 
	 */
	@Test
	public final void test03()   {
		ResourceItem ri=new ResourceItem(1, "Recurso01", "Pista de tenis", "");
		ri.setWeeklyAvailability("0101010");
		assertTrue("Error al consultar si lunes disponible", !ri.isAvailableOnMonday());
		assertTrue("Error al consultar si martes disponible", ri.isAvailableOnTuesday());
		assertTrue("Error al consultar si miércoles disponible", !ri.isAvailableOnWednesday());
		assertTrue("Error al consultar si jueves disponible", ri.isAvailableOnThursday());
		assertTrue("Error al consultar si viernes disponible", !ri.isAvailableOnFriday());
		assertTrue("Error al consultar si sábado disponible", ri.isAvailableOnSaturday());
		assertTrue("Error al consultar si domingo disponible", !ri.isAvailableOnSunday());
	}
	/**
	 * Comprobación de bloqueado por fecha concreta.
	 */
	@Test
	public final void test04()   {
		ResourceItem ri=new ResourceItem(1, "Recurso01", "Pista de tenis", "");
		ri.setWeeklyAvailability("0101010");
		
		DateTime monday=new DateTime(2105,9,14,0,0);//L
		DateTime tuesday=new DateTime(2105,9,15,23,59);//M
		DateTime wednesday=new DateTime(2105,9,16,13,21);//X
		DateTime thursday=new DateTime(2105,9,17,16,31);//J
		DateTime friday=new DateTime(2105,9,18,3,1);//V
		DateTime saturday=new DateTime(2105,9,19,18,1);//S
		DateTime sunday=new DateTime(2105,9,20,23,1);//D
		
		assertTrue("Error al consultar si lunes disponible", ri.isBlocked(monday));
		assertTrue("Error al consultar si martes disponible", !ri.isBlocked(tuesday));
		assertTrue("Error al consultar si miécoles disponible", ri.isBlocked(wednesday));
		assertTrue("Error al consultar si jueves disponible", !ri.isBlocked(thursday));
		assertTrue("Error al consultar si viernes disponible", ri.isBlocked(friday));
		assertTrue("Error al consultar si sábado disponible", !ri.isBlocked(saturday));
		assertTrue("Error al consultar si domingo disponible", ri.isBlocked(sunday));
	}
	
	
}
