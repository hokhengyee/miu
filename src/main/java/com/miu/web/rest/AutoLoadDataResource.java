package com.miu.web.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
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
import com.miu.domain.LecturerProfile;
import com.miu.domain.RecordOfCertificate;
import com.miu.domain.ResearchPaper;
import com.miu.domain.StudentModuleResult;
import com.miu.domain.StudentResearchPaperResult;
import com.miu.repository.LecturerProfileRepository;
import com.miu.repository.ModuleRepository;
import com.miu.repository.RecordOfCertificateRepository;
import com.miu.repository.ResearchPaperRepository;
import com.miu.repository.StudentModuleResultRepository;
import com.miu.repository.StudentResearchPaperResultRepository;
import com.miu.service.dto.LecturerProfileDto;
import com.miu.service.dto.ROCDto;
import com.miu.service.dto.StudentResultDto;
import com.miu.service.dto.StudentResultResetDto;
import com.miu.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Public APIs.
 */
@RestController
@RequestMapping("/api/load-data")
public class AutoLoadDataResource {

	@Inject
	private LecturerProfileRepository lecturerProfileRepository;

	private final Logger LOGGER = LoggerFactory.getLogger(AutoLoadDataResource.class);

	@Inject
	private ModuleRepository moduleRepository;

	@Inject
	private RecordOfCertificateRepository recordOfCertificateRepository;

	@Inject
	private ResearchPaperRepository researchPaperRepository;

	@Inject
	private StudentModuleResultRepository studentModuleResultRepository;

	@Inject
	private StudentResearchPaperResultRepository studentResearchPaperResultRepository;

	@GetMapping("/record-of-certificates")
	@Timed
	public ResponseEntity<ROCDto> getAcademicCertificateDto() {
		LOGGER.debug("REST request to get Upload ROC Dto...");
		ROCDto result = new ROCDto();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/lecturer-profile")
	@Timed
	public ResponseEntity<LecturerProfileDto> getLecturerProfileDto() {
		LOGGER.debug("REST request to get Upload Lecturer Profile Dto...");
		LecturerProfileDto result = new LecturerProfileDto();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/student-results")
	@Timed
	public ResponseEntity<StudentResultDto> getStudentResultsDto() {
		LOGGER.debug("REST request to get Upload Student Result Dto...");
		StudentResultDto result = new StudentResultDto();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/student-results/reset")
	@Timed
	public ResponseEntity<StudentResultResetDto> getStudentResultsResetDto() {
		LOGGER.debug("REST request to get Upload Student Result Reset Dto...");
		StudentResultResetDto result = new StudentResultResetDto();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/lecturer-profile")
	@Timed
	public ResponseEntity<Void> loadLecturerProfileTemplate(@RequestBody LecturerProfileDto lpDto)
			throws URISyntaxException {
		LOGGER.debug("REST request to load Lecturer Profile.");

		LecturerProfile lecProf = lecturerProfileRepository.findLecturerProfileByUser(lpDto.getUser());

		LOGGER.info("Prof: " + lecProf.getUser().getLogin());

		List<String> tmpList = new ArrayList<String>();
		try {
			InputStream fis = new ByteArrayInputStream(lpDto.getLecturerProfileTemplate());
			XWPFDocument doc = new XWPFDocument(fis);

			List<XWPFTable> tables = doc.getTables();

			for (XWPFTable table : tables) {
				for (XWPFTableRow row : table.getRows()) {
					tmpList = new ArrayList<String>();
					for (XWPFTableCell cell : row.getTableCells()) {
						// System.out.print(cell.getText());
						tmpList.add(cell.getText().trim());
					}

					LOGGER.info("ROW: " + tmpList.toString());
					
					if (tmpList.size() > 0 && tmpList.get(1) != null &&
							!tmpList.get(1).isEmpty()) {
						switch (tmpList.get(0)) {

						case "Other Titles:":
							lecProf.setOtherTitles(tmpList.get(1));
							break;

						case "Age:":
							lecProf.setAge(Long.valueOf(tmpList.get(1)));
							break;

						case "Ordination:":
							lecProf.setOrdination(tmpList.get(1));
							break;

						case "Academic History:":
							lecProf.setAcademicHistory(tmpList.get(1));
							break;

						case "Professional History:":
							lecProf.setProfessionalHistory(tmpList.get(1));
							break;

						case "Past and Current Ministry:":
							lecProf.setPastAndCurrentMinistry(tmpList.get(1));
							break;

						case "Publications:":
							lecProf.setPublications(tmpList.get(1));
							break;

						case "Family:":
							lecProf.setFamilyDetails(tmpList.get(1));
							break;

						case "Reference:":
							lecProf.setReference(tmpList.get(1));
							break;

						default:
							break;
						}
					}
					
					lecturerProfileRepository.save(lecProf);
				}
			}

			doc.close();
		}

		catch (FileNotFoundException e) {
			LOGGER.error("loadLecturerProfileTemplate() FileNotFoundException: ", e);
		}

		catch (IOException e) {
			LOGGER.error("loadLecturerProfileTemplate() IOException: ", e);
		}

		/* Original */
		// try {
		//
		// InputStream excelFile = new
		// ByteArrayInputStream(lpDto.getLecturerProfileTemplate());
		// Workbook workbook = new XSSFWorkbook(excelFile);
		// Sheet datatypeSheet = workbook.getSheetAt(0);
		// Iterator<Row> iterator = datatypeSheet.iterator();
		//
		// List<String> tmpList = new ArrayList<String>();
		//
		// while (iterator.hasNext()) {
		//
		// tmpList = new ArrayList<String>();
		//
		// Row currentRow = iterator.next();
		// Iterator<Cell> cellIterator = currentRow.iterator();
		//
		// while (cellIterator.hasNext()) {
		// Cell currentCell = cellIterator.next();
		// tmpList.add(currentCell.toString().trim());
		// }
		//
		// LOGGER.info("ROW: " + tmpList.toString());
		//
		// if (tmpList.size() > 0 && tmpList.get(1) != null &&
		// !tmpList.get(1).isEmpty()) {
		// switch (tmpList.get(0)) {
		//
		// case "Other Titles:":
		// lecProf.setOtherTitles(tmpList.get(1));
		// break;
		//
		// case "Age:":
		// lecProf.setAge(Long.valueOf(tmpList.get(1)));
		// break;
		//
		// case "Ordination:":
		// lecProf.setOrdination(tmpList.get(1));
		// break;
		//
		// case "Academic History:":
		// lecProf.setAcademicHistory(tmpList.get(1));
		// break;
		//
		// case "Professional History:":
		// lecProf.setProfessionalHistory(tmpList.get(1));
		// break;
		//
		// case "Past and Current Ministry:":
		// lecProf.setPastAndCurrentMinistry(tmpList.get(1));
		// break;
		//
		// case "Publications:":
		// lecProf.setPublications(tmpList.get(1));
		// break;
		//
		// case "Family:":
		// lecProf.setFamilyDetails(tmpList.get(1));
		// break;
		//
		// case "Reference:":
		// lecProf.setReference(tmpList.get(1));
		// break;
		//
		// default:
		// break;
		// }
		// }
		//
		// lecturerProfileRepository.save(lecProf);
		// }
		//
		// workbook.close();
		// }
		//
		// catch (FileNotFoundException e) {
		// LOGGER.error("loadLecturerProfileTemplate() FileNotFoundException: ",
		// e);
		// }
		//
		// catch (IOException e) {
		// LOGGER.error("loadLecturerProfileTemplate() IOException: ", e);
		// }

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("Lecturer Profile", lecProf.getId().toString())).build();
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

	@PostMapping("/student-results")
	@Timed
	public ResponseEntity<Void> loadStudentResultTemplate(@RequestBody StudentResultDto studentResultDto)
			throws URISyntaxException {
		LOGGER.debug("REST request to load student results.");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		try {

			InputStream excelFile = new ByteArrayInputStream(studentResultDto.getStudentResultTemplate());
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(1);
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

				if (tmpList.get(0).contains("RE")) {
					StudentResearchPaperResult result = studentResearchPaperResultRepository
							.getResultByUserAndResearchPaper(studentResultDto.getUser().getId(), tmpList.get(0));

					if (result == null) {
						/* Generate new */
						result = new StudentResearchPaperResult();
						result.setUser(studentResultDto.getUser());
						List<ResearchPaper> output = researchPaperRepository.getByCode(tmpList.get(0));
						if (output.size() > 0) {
							result.setResearchPaper(output.get(0));
						}
					}

					if (tmpList.get(2) != null && !tmpList.get(2).isEmpty()) {
						result.setResult(tmpList.get(2));
					}

					if (tmpList.get(4) != null && !tmpList.get(4).isEmpty()) {
						LocalDate localDate = LocalDate.parse(tmpList.get(4).replaceAll("\"", ""), formatter);
						result.setDateGraded(localDate);
					}

					studentResearchPaperResultRepository.save(result);
				}

				else {
					StudentModuleResult result = studentModuleResultRepository
							.getResultByUserAndModule(studentResultDto.getUser().getId(), tmpList.get(0));

					if (result == null) {
						/* Generate new */
						result = new StudentModuleResult();
						result.setUser(studentResultDto.getUser());
						result.setModule(moduleRepository.getByCode(tmpList.get(0)));
					}

					if (tmpList.get(2) != null && !tmpList.get(2).isEmpty()) {
						result.setResult(tmpList.get(2));
					}

					if (tmpList.get(4) != null && !tmpList.get(4).isEmpty()) {
						LocalDate localDate = LocalDate.parse(tmpList.get(4).replaceAll("\"", ""), formatter);
						result.setDateGraded(localDate);
					}

					studentModuleResultRepository.save(result);
				}
			}

			workbook.close();
		}

		catch (FileNotFoundException e) {
			LOGGER.error("loadStudentResultTemplate() FileNotFoundException: ", e);
		}

		catch (IOException e) {
			LOGGER.error("loadStudentResultTemplate() IOException: ", e);
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Student Results", null)).build();
	}

	@PostMapping("/student-results/reset")
	@Timed
	public ResponseEntity<Void> loadStudentResultTemplate(@RequestBody StudentResultResetDto studentResultResetDto)
			throws URISyntaxException {
		LOGGER.debug("REST request to reset student results.");

		List<StudentModuleResult> moduleResults = studentModuleResultRepository
				.getResultByUser(studentResultResetDto.getUser().getId());
		if (moduleResults.size() > 0) {
			for (StudentModuleResult item : moduleResults) {
				studentModuleResultRepository.delete(item);
			}
		}

		List<StudentResearchPaperResult> rpResults = studentResearchPaperResultRepository
				.getResultByUser(studentResultResetDto.getUser().getId());
		if (rpResults.size() > 0) {
			for (StudentResearchPaperResult item : rpResults) {
				studentResearchPaperResultRepository.delete(item);
			}
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Reset Student Results",
				studentResultResetDto.getUser().getId().toString())).build();
	}

}
