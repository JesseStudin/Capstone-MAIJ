package metricAnalysis.Metrics;

import java.util.Arrays;

import com.github.javaparser.ast.body.TypeDeclaration;
import com.ibm.icu.text.DecimalFormat;

import astManager.ClassAST;
import metricAnalysis.MetricResult;

public class CPCCMetric extends Metric {

	/*
	 * @author: Jesse Studin
	 * @Description:  “The ratio of the number
	 * of critical composed-part classes to the total number of critical classes in a design”
	 */
	
	
	//not finished
	public CPCCMetric() {
		super();
		this.levels = Arrays.asList(MetricLevel.CLASS);
	}
	
	
	
	private double CPVal(TypeDeclaration<?> n) {
		double cpVal = 0.0;
		
		//CPP - Critical Part Class
		//
		
		return cpVal;
	}
	
	//end calculations
	
	public void run(ClassAST clasAst) {
		//if it exists
		if(this.alreadyDone(clasAst)) return;
		TypeDeclaration<?> n = clasAst.getNode();
		//get the values for CA and NCIA using their respective functions
		//find the result via NCIA over CA
//		DecimalFormat df = new DecimalFormat("####0.00");
//		double resultVal = Double.valueOf(df.format(CAMethVal(n) / NCAMethVal(n)));
		//will want to check if part class is 0 and whole class >= 1;
		
		double resultVal = 0.0;
		//Store the result within the MetricResult class ***Redefine this comment later
		MetricResult result = new MetricResult(this.identifier, resultVal);
		//log identifier + results
		clasAst.getResults().put(this.identifier, result);
	}
	
}
