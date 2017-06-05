package com.raycloud.overseas.erp.data.web.cobar;

import org.apache.log4j.Logger;


public class HashFunction {

	private static final Logger logger = Logger.getLogger(HashFunction.class);
	
	public Long apply(Long fkId) {
		//logger.info("fk router input = " + fkId);
		return fkId;
	}

}
