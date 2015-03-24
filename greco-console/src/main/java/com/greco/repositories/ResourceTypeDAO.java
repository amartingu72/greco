package com.greco.repositories;

import java.util.List;

import com.greco.entities.Resourcetype;

public interface ResourceTypeDAO {
	public List<Resourcetype> loadAllResourceTypes();
	public Resourcetype loadSelected(Integer resourceTypeId);
	public Resourcetype loadSelected(String name);
}
