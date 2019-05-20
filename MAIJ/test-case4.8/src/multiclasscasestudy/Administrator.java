package multiclasscasestudy;

public class Administrator extends Staff{

	private Administrator(Name name, String jobTitle) {
		super(name, jobTitle);
		
	}
	

	//private String jobTitle;
	
	public void SetJobTitle(String jobTitle) {
		
		this.jobTitle = jobTitle;
	}
	
	public String GetJobTitle() {
		
		return jobTitle;
	}
}
