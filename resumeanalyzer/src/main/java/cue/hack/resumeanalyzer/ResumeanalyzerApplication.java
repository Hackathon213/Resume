package cue.hack.resumeanalyzer;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cue.hack.resumeanalyzer.controller.ResumeAnalyzerController;

@SpringBootApplication
public class ResumeanalyzerApplication {

	public static void main(String[] args) throws IOException {
		System.out.println("Before application has started analyzing resumes");
		String jDPath = "C:\\Users\\pcadmin\\Desktop\\HACKATHON\\JD\\Job Description.pdf";
		//String jDPath = "C:\\Users\\pcadmin\\Desktop\\HACKATHON\\JD\\Test.pdf";
		String resumePath =  "C:\\Users\\pcadmin\\Desktop\\HACKATHON\\RESUMES";
		ResumeAnalyzerController resumeAnalyzerController = new ResumeAnalyzerController();
		resumeAnalyzerController.readDocument(jDPath, resumePath);
		SpringApplication.run(ResumeanalyzerApplication.class, args);
		System.out.print("After application has finished analyzing resumes");
	}

}
