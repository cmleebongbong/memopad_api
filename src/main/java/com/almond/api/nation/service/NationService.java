package com.almond.api.nation.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.nation.dao.NationMapper;
import com.almond.api.nation.domain.Nation;

@Service
public class NationService {
    @Autowired
    private NationMapper nationMapper;
	
    /**
     * @return ArrayList<Location>
     * @throws Exception
     */
    public ArrayList<Nation> nationList() throws Exception {
    	ArrayList<Nation> nationList = nationMapper.nationList();
    	return nationList;
    }
}
