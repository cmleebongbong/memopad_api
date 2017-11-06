package com.almond.api.scrap.dao;

import java.util.ArrayList;

import com.almond.api.scrap.domain.Scrap;

public interface ScrapMapper {
	public ArrayList<Scrap> scrapList(String nationCode, String[] cityIdx, String[] categoryIdx) throws Exception;
	public int scrapRegister(Scrap scrap) throws Exception;
}
