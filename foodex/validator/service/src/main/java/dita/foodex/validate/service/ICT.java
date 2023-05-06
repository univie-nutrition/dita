/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.foodex.validate.service;

import java.io.FileNotFoundException;

import business_rules.TermRules;
import catalogue.Catalogue;
import catalogue_browser_dao.CatalogueDAO;
import catalogue_browser_dao.DatabaseManager;
import dcf_manager.Dcf.DcfType;
import lombok.extern.slf4j.Slf4j;
import utilities.GlobalUtil;

/**
 * Copy-pasted and stripped down from openefsa's {@code main.ICT}. 
 */
@Slf4j
public class ICT extends TermRules {

    

	public ICT(String mtxCode, boolean local) throws ICT.MtxNotFoundException, InterruptedException {
	    
		CatalogueDAO catDao = new CatalogueDAO();
		DcfType type = local ? DcfType.LOCAL : DcfType.PRODUCTION;

		log.info("Enumerate catalogues from {} ...", DatabaseManager.getMainDBURL());
		
		Catalogue mtx = catDao.getLastVersionByCode(mtxCode, type);

		if (mtx == null) {
			throw new MtxNotFoundException(mtxCode, type);
		}
		currentCat = mtx;

		log.info("Loading catalogue data into RAM (file.separator={}) ...", System.getProperty("file.separator"));

		currentCat.loadData();

		loadFileData();
	}

	public class MtxNotFoundException extends FileNotFoundException {
		/**
		 * 
		 */
		private static final long serialVersionUID = -45955835909711360L;

		public MtxNotFoundException(String mtxCode, DcfType type) {
			super();
		}
	}

	private void loadFileData() throws InterruptedException {

		forbiddenProcesses = loadForbiddenProcesses(GlobalUtil.getBRData());
		warningMessages = loadWarningMessages(GlobalUtil.getBRMessages());

	}
	
	/**
	 * Print the warning messages
	 * 
	 * @param event
	 * @param postMessageString
	 * @param attachDatetime
	 * @param stdOut
	 */
	protected void printWarning(WarningEvent event, String postMessageString, boolean attachDatetime, boolean stdOut) {
		
		// create the warning message to be printed
		String message = createMessage(event, postMessageString, attachDatetime);
		
		// get the warning levels for making colours
		WarningLevel semaphoreLevel = getSemaphoreLevel(event);
		WarningLevel textWarningLevel = getTextLevel(event);

		// if the message should be printed into the standard output
		// CSV line semicolon separated
		// do not print the base term successfully added warning! It is useless for the
		// excel macro
		if (stdOut && event != WarningEvent.BaseTermSuccessfullyAdded) {

			StringBuilder sb = new StringBuilder();
			sb.append(message);
			sb.append(";");
			sb.append(semaphoreLevel.toString());
			sb.append(";");
			sb.append(textWarningLevel.toString());

			// print the line
			System.out.print(sb.toString() + "|");
			return;
		}
	}


}
