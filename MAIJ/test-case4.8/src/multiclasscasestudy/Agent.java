package multiclasscasestudy;

//critical

public class Agent {

	/*secrecy*/
	private Clearance clearance;
	
	public void SetClearance(Clearance clearance) {
		
		this.clearance = clearance;
	}
	
	public String GetClearance() {
		
		return clearance.GetClearance();
	}
}
