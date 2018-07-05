package com.almond.api.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almond.api.file.dao.FileMapper;
import com.almond.api.file.domain.File;

@Service
public class FileService {
	
    @Autowired
    private FileMapper fileMapper;

    /**
     * 파일 등록
     * 
     * @param userIdx, scrapIdx
     * @return int
     * @throws Exception
     */
    public int insertFile(File file) throws Exception {
    	return fileMapper.insertFile(file);
    }
}
