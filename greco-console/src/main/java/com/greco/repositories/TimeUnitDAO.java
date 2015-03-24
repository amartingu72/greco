package com.greco.repositories;

import java.util.List;


import com.greco.entities.Timeunit;

public interface TimeUnitDAO {
	/**
	 * Lee todos las unidades de tiempo.
	 * @return Lista de unidades de tiempo.
	 */
	public List<Timeunit> loadAll();
	/**
	 * Lee la unidad de tiempo indicada.
	 * @param id Identificador de la unidad.
	 * @return Unidad de tiempo.
	 */
	public Timeunit loadSelected(Integer id);
	
	/**
	 * Carga la unidad de tiempo indicada en el nombre.
	 * @param name Nombre de la unidad (Horas, Minutos ...)
	 * @return Unidad de tiempo.
	 */
	public Timeunit loadSelected(String name);

}
