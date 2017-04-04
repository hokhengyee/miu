package com.miu.web.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.RecordOfCertificate;
import com.miu.repository.RecordOfCertificateRepository;
import com.miu.service.dto.ROCDto;
import com.miu.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Public APIs.
 */
@RestController
@RequestMapping("/api/load-data")
public class AutoLoadDataResource {

	private final Logger LOGGER = LoggerFactory.getLogger(AutoLoadDataResource.class);

	private static final String FILE_NAME = "/home/ho/github/miu/src/main/resources/RecordOfCertificate.xlsx";

	@Inject
	private RecordOfCertificateRepository recordOfCertificateRepository;

	@GetMapping("/record-of-certificates")
	@Timed
	public ResponseEntity<ROCDto> getAcademicCertificate() {
		LOGGER.debug("REST request to get Upload ROC Dto : {}");
		ROCDto result = new ROCDto();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/record-of-certificates")
	@Timed
	public ResponseEntity<Void> loadROCTemplate(@RequestBody ROCDto rocDto) throws URISyntaxException {
		LOGGER.debug("REST request to load record of certificates.");

		try {

			// FileInputStream excelFile = new FileInputStream(new
			// File(FILE_NAME));
			InputStream excelFile = new ByteArrayInputStream(rocDto.getRocTemplate());
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			List<String> tmpList = new ArrayList<String>();

			/* Skip header row */
			if (iterator.hasNext()) {
				iterator.next();
			}

			while (iterator.hasNext()) {

				tmpList = new ArrayList<String>();

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					tmpList.add(currentCell.toString().trim());
				}

				LOGGER.info("ROW: " + tmpList.toString());

				RecordOfCertificate rc = recordOfCertificateRepository.findByNameAndDegree(tmpList.get(1).trim(),
						tmpList.get(2).trim());
				if (rc == null) {
					rc = new RecordOfCertificate();
					rc.setName(tmpList.get(1));
				}

				rc.setDegree(tmpList.get(2));
				rc.setStudentNo(tmpList.get(3));
				rc.setCertNumber(tmpList.get(4));
				recordOfCertificateRepository.save(rc);
			}

			workbook.close();
		}

		catch (FileNotFoundException e) {
			LOGGER.error("loadROCTemplate() FileNotFoundException: ", e);
		}

		catch (IOException e) {
			LOGGER.error("loadROCTemplate() IOException: ", e);
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Record Of Certificates", null)).build();
	}

}
