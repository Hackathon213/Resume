package cue.hack.resumeanalyzer.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import cue.hack.resumeanalyzer.dto.ResumeDetailsDTO;
import cue.hack.resumeanalyzer.dto.ResumeMatchDTO;


public class ResumeAnalyzerController {

	public Object readDocument(String jDPath, String resumePath) throws IOException {

		Set<String> jDKeyWords = readJD(jDPath);
		String firstResumeName= resumePath + "\\First Candidate Resume.pdf";
		String secondResumeName= resumePath + "\\Second Candidate Resume.pdf";
		String thirdResumeName= resumePath + "\\Third Candidate Resume.pdf";
		
		System.out.println("Below is the JD being read");
		System.out.println(jDKeyWords);
		System.out.println("Below is the First Resume being read");
		
		String firstResume=readResume(firstResumeName);
		//String firstSkillSet=StringUtils.substringBetween(firstResume, "Skill Set", "Experience");
		System.out.println(firstResume);
		
		System.out.println("Below is the Second Resume being read");
		String secondResume=readResume(secondResumeName);
		//String secondSkillSet=StringUtils.substringBetween(secondResume, "Skill Set", "Experience");
		System.out.println(secondResume);
		
		System.out.println("Below is the Third Resume being read");
		String thirdResume=readResume(thirdResumeName);
		//String thirdSkillSet=StringUtils.substringBetween(thirdResume, "Skill Set", "Experience");
		System.out.println(thirdResume);
		
		List<ResumeDetailsDTO> resumeList = new ArrayList<ResumeDetailsDTO>();
		ResumeDetailsDTO first = new ResumeDetailsDTO();
		first.setResumeName(firstResumeName);
		first.setResumeDetails(firstResume);
		ResumeDetailsDTO second = new ResumeDetailsDTO();
		second.setResumeName(secondResumeName);
		second.setResumeDetails(secondResume);
		ResumeDetailsDTO third = new ResumeDetailsDTO();
		third.setResumeName(thirdResumeName);
		third.setResumeDetails(thirdResume);
		
		resumeList.add(first);
		resumeList.add(second);
		resumeList.add(third);
		
		Object obj= analyzeResume(jDKeyWords, resumeList);
		
		return null;
	}

	public Set<String> readJD(String jDPath) throws IOException {

		System.out.println("DB080C Started reading JD and the path is " + jDPath);

		File file = new File(jDPath);
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdfStripper = new PDFTextStripper();

		String text = pdfStripper.getText(document);

		document.close();
		String spaceRemovedString=text.replaceAll("\\s+", " ");
		String skillSetExtractedString = getExtractedJDSkillSet(spaceRemovedString);
		String[] words = skillSetExtractedString.split(" ");
		//int count = 0;
		Set<String> keyWords = new HashSet<String>();
		for (String word : words) {
			if (word != null && word!="") {
				String commaRemovedString=word.replace(",", ""); 
				//word.replace(",", " ");
				keyWords.add(commaRemovedString);
			}
		}
		// System.out.println(keyWords);
		// System.out.println("DB080C Finished reading JD");

		return keyWords;

	}
	public String readResume (String resumePath) throws IOException
	{
		//System.out.println("DB080C Started reading JD and the path is " + resumePath);
		
		File file = new File(resumePath);
		PDDocument document = PDDocument.load(file);
		PDFTextStripper pdfStripper = new PDFTextStripper();

		String resumeString = pdfStripper.getText(document);

		document.close();
		String skillSetExtractedResume = getExtractedSkillSet(resumeString);
		return skillSetExtractedResume;
		
	}
	
	public String getExtractedSkillSet(String resume)
	{
		
		String skillSetExtractedResume=StringUtils.substringBetween(resume, "Skill Set :", "Experience");
		return skillSetExtractedResume;
	}
	
	public String getExtractedJDSkillSet(String resume)
	{
		
		String skillSetExtractedResume=StringUtils.substringBetween(resume, "Skill Set :", "Role");
		return skillSetExtractedResume;
	}
	
	public Object analyzeResume(Set<String> jDKeyWords, List<ResumeDetailsDTO> resumeList)
	{
		List<ResumeMatchDTO> match = new ArrayList<ResumeMatchDTO>();
		//ResumeMatchDTO resumematch;
		int totalKeyWords=jDKeyWords.size();
		
		for(ResumeDetailsDTO resume : resumeList)
		{
			ResumeMatchDTO resumematch= new ResumeMatchDTO();
			int matchCount=0;
			int percentageMatch=0;
			List<String> matchKeywords= new ArrayList<String>();
			//Iterator<String> keywordsIterator = jDKeyWords.iterator();
			for(String keyWord : jDKeyWords) {
				if(keyWord!=null && keyWord!="" && resume.getResumeDetails().contains(keyWord))
				{
					matchCount++;
					matchKeywords.add(keyWord);
				}
				
			}
			percentageMatch = (matchCount * 100)/totalKeyWords;
			resumematch.setResumeName(resume.getResumeName());
			resumematch.setPercentageMatch(percentageMatch);
			resumematch.setMatchedKeywords(matchKeywords);
			//System.out.println();
			//System.out.println("This resume name " + resume.getResumeName() + " has matched count " + matchCount + " and matched Keywords are " + matchKeywords);
			//System.out.println();
			//System.out.println();
			match.add(resumematch);
			
		}
		
		System.out.println(" Output at the end of the program " + match);
		return null;
		
	}
}
 