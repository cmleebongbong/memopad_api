package com.almond.api.nation.dao;

import java.util.ArrayList;

import com.almond.api.nation.domain.Nation;

public interface NationMapper {
	public ArrayList<Nation> nationList() throws Exception;
}
