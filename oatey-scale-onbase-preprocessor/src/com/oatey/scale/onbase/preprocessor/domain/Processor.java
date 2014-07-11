package com.oatey.scale.onbase.preprocessor.domain;

import java.io.File;

import com.oatey.scale.onbase.preprocessor.domain.input.InputObject;
import com.oatey.scale.onbase.preprocessor.domain.input.InputRepository;
import com.oatey.scale.onbase.preprocessor.domain.input.InputObjectFactory;
import com.oatey.scale.onbase.preprocessor.domain.output.Document;
import com.oatey.scale.onbase.preprocessor.domain.output.DocumentFactory;
import com.oatey.scale.onbase.preprocessor.domain.output.DocumentRepository;


public class Processor {
	private DocumentFactory documentFactory;
	private InputObjectFactory inputObjectFactory;
	private InputRepository inputRepository;
	private DocumentRepository documentRepository;
	
	public void run() throws Exception {
		Logger.logStep(null, "Beginning processing run");
		
		String[] inputFiles = inputRepository.getInputFiles();
		if(inputFiles == null){
			Logger.logStep(null, "Zero (0) input files");
			return;
		}
		
		Logger.logStep(null, inputFiles.length + " input files to process");
		
		for(String fileName : inputFiles) {
			Logger.logStep(fileName, "Beginning process");
			
			File inputFile = inputRepository.getUnprocessedFile(fileName);
			
			if(inputRepository.hasFileChanged(inputFile)) {
				Logger.logStep(fileName, "Input File has changed.  Waiting for next pass.");
				continue;
			}
			
			InputObject inputObject = inputObjectFactory.build(inputFile);
			Document document = documentFactory.build(inputObject);
			
			if(document == null) {
				Logger.logFailure(fileName, "Unable to determine document type");
				continue;
			}
			
			File pdfFile = inputRepository.getUnprocessedFile(document.getFileName());
			
			if(inputRepository.hasFileChanged(pdfFile)) {
				Logger.logStep(fileName, "PDF File has changed.  Waiting for next pass.");
				continue;
			}
			
			if(!documentRepository.copyToUnprocessedFolder(pdfFile)) {
				Logger.logFailure(fileName, "Unable to copy PDF file");
				continue;
			}
				
			if(!documentRepository.writeDocument(document)) {
				Logger.logFailure(fileName, "Unable to write document meta data");
				continue;
			}
			
			if(!inputRepository.moveToProcessedFolder(pdfFile)) {
				Logger.logFailure(fileName, "Unable to move PDF file to processed folder");
				continue;
			}
			
			if(!inputRepository.moveToProcessedFolder(inputFile)) {
				Logger.logFailure(fileName, "Unable to move input file to processed folder");
				continue;
			}
			
			Logger.logStep(fileName, "Processed successfully");
		}
	
		Logger.logStep(null, "Ending processing run");
		
	}

	public DocumentFactory getDocumentFactory() {
		return documentFactory;
	}

	public void setDocumentFactory(DocumentFactory documentFactory) {
		this.documentFactory = documentFactory;
	}

	public InputObjectFactory getInputObjectFactory() {
		return inputObjectFactory;
	}

	public void setInputObjectFactory(InputObjectFactory inputObjectFactory) {
		this.inputObjectFactory = inputObjectFactory;
	}

	public InputRepository getInputRepository() {
		return inputRepository;
	}

	public void setInputRepository(InputRepository inputRepository) {
		this.inputRepository = inputRepository;
	}

	public DocumentRepository getDocumentRepository() {
		return documentRepository;
	}

	public void setDocumentRepository(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

}
