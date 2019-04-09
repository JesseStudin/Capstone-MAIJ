/*
 * Finds the ratio of non-private classified instance attributes to the number of classified attributes within a class. (CIDA)
 * Aim to change this to not allow comments, but until further deliberation is met, is hard to find the secrecy value without 'secrecy' tag
 * @author Jesse Studin
 */

package metricAnalysis.Metrics;
import java.util.Arrays;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import astManager.ClassAST;
import metricAnalysis.MetricResult;

public class Base extends Metric {
	
	public Base() {
		super();
		this.levels = Arrays.asList(MetricLevel.CLASS);
	}
	
	/*
	 * Finds the CA Value
	 * Sorts each field into x (for-each) and if "/*secrecy exists, then CA is +=1, then returns CA
	 */
	
	
	private double CAVal(TypeDeclaration<?> n) {
		double classifiedVal = 0.0;
		//checks if the current field contains the secrecy comment, if so CA is icreased by one
		for(FieldDeclaration x : n.getFields()) {
			if(x.toString().contains("/*secrecy*/")) {
				//print ln to show the CA value found (proof it was secrecy)
				System.out.println("Found a CA: " + x.toString() + "\n"); //delete later
				classifiedVal++;
			}		
		}//end for-each
		return classifiedVal;
	}//end CAVal function
	
	/*
	 * Find the total for NCIA
	 * Saves each field to X and finds whether it contains the word "public".. if so adds 1 to classifiedVal, when complete, returns the value
	 */
	private double NCIAVal(TypeDeclaration<?> n) {
		double classifiedVal = 0.0;
		//checks whether the current field has the secrecy comment and is set to public, if so, NCIA is increased by one.
		for(FieldDeclaration x : n.getFields()) {
			if(x.toString().contains("/*secrecy*/") && x.toString().contains("public")) {
				//print ln to show what the NCIA String was (proof it was secrecy and public)
				System.out.println("Found a NCIA: " + x.toString() + "\n"); //delete later
				classifiedVal++;
			}
		}//end for-each
		return classifiedVal;
	}//end NCIAVal function
	public void run(ClassAST clasAst) {
		//if it exists
		if(this.alreadyDone(clasAst)) return;
		
		System.out.println("Current path: " + clasAst.getNode().findCompilationUnit().get().getStorage().get().getPath());
		TypeDeclaration<?> n = clasAst.getNode();
		//get the values for CA and NCIA using their respective functions
		double CA = CAVal(n);
		double NCIA = NCIAVal(n);
		//find the result via NCIA over CA
		double resultVal = NCIA / CA;
		//Store the result within the MetricResult class ***Redefine this comment later
		MetricResult result = new MetricResult(this.identifier, resultVal);
		//log identifier + results
		clasAst.getResults().put(this.identifier, result);
	}
}
