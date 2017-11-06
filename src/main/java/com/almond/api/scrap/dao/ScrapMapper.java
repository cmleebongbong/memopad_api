package com.almond.api.scrap.dao;

import java.util.ArrayList;

import com.almond.api.scrap.domain.Scrap;

public interface ScrapMapper {
	public ArrayList<Scrap> scrapList() throws Exception;
	public ArrayList<Scrap> scrapList(String nationCode) throws Exception;
	public int scrapRegister(Scrap scrap) throws Exception;
}
