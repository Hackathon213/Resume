package cue.hack.resumeanalyzer.dto;

import java.util.List;

public class ResumeMatchDTO {

	private String resumeName;
	private int percentageMatch;
	private List<String> matchedKeywords;

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	public int getPercentageMatch() {
		return percentageMatch;
	}

	public void setPercentageMatch(int percentageMatch) {
		this.percentageMatch = percentageMatch;
	}

	public List<String> getMatchedKeywords() {
		return matchedKeywords;
	}

	public void setMatchedKeywords(List<String> matchedKeywords) {
		this.matchedKeywords = matchedKeywords;
	}

	@Override
	public String toString() {
		return "resumeName= " + resumeName + ", percentageMatch= " + percentageMatch
				+ ", matchedKeywords= " + matchedKeywords + "\n";
	}
	
	

}
