package com.caozj.dao;

import java.util.List;

import com.caozj.model.Buss;

public interface BussDao {

	void add(Buss buss);

	void update(Buss buss);

	void delele(String name);

	Buss get(String name);

	List<Buss> listAll();
}
