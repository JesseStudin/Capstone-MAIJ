package metricAnalysis.Metrics;

import java.util.Arrays;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import astManager.ClassAST;
import metricAnalysis.MetricResult;

public class Checker extends Metric {

	public Checker() {
		super();
		this.levels = Arrays.asList(MetricLevel.CLASS);
	}
	
	
	
	/*Find a value that reads or write a classified attribute (//secrecyvalue//)*/
	
	private double CAVal(TypeDeclaration<?> n) {
		double classifiedVal = 0.0;
	
		//predicted value for testdata?
		for(FieldDeclaration i : n.getFields()) {
			for(MethodDeclaration x : n.getMethods()) {
				if(i.toString().contains("/*secrecy*/")){
					String tempHold = i.getVariables().toString();
					tempHold = tempHold.replaceAll("\\[", "").replaceAll("\\]", "");
					if(x.toString().contains("this." + tempHold)) {
						classifiedVal++;
					}
				}
			}
		}

		
		return classifiedVal;
	}//end CAVal function
	
	//find methods that do not interact with classified attiributes
	private double NCMethod(TypeDeclaration<?> n) {
		double classifiedVal = 0;
		
		//check each method, if count == 0, increase classified val?
		
		//sort through, if method contains /*secrecy*/ dont increase, else increase
//		for(FieldDeclaration i : n.getFields()) {
//			int checkContains = 0;
//			for(MethodDeclaration x : n.getMethods()) {
//				if(i.toString().contains("/*secrecy*/")) {
//					String tempHold = i.getVariables().toString();
//					tempHold = tempHold.replaceAll("\\[", "").replaceAll("\\]", "");
//					if(x.toString().contains("this." + tempHold)) {
//						checkContains++;
//					}
//				}
//				System.out.println("inside for: " + checkContains);
//			}
//			System.out.println("Outside For: " + checkContains);
//			if(checkContains == 0) {
//				classifiedVal++;
//			}
//		}
		//check for methods then fields??
		//if contains this.+hold && public --> methodheader
		for(MethodDeclaration x : n.getMethods()) {
			for(FieldDeclaration i : n.getFields()) {
				if(i.toString().contains("/*secrecy*/")) {
					String tempHold = i.getVariables().toString();
					tempHold = tempHold.replaceAll("\\[", "").replaceAll("\\]", "");
					String modHold = x.getModifiers().toString();
					modHold = modHold.replaceAll("\\[", "").replaceAll("\\]", "");
					System.out.println(modHold.contentEquals("PUBLIC"));
					System.out.println("Modifier: " + x.getModifiers().toString());
					if(modHold == "[PUBLIC]") {
						System.out.println("ENTERED");
						classifiedVal++;
					}
				}
			}
			System.out.println("Classified Val is: " + classifiedVal);
		}
		
		return classifiedVal;
	}

	public void run(ClassAST clasAst) {
		//if it exists
		if(this.alreadyDone(clasAst)) return;
		
		System.out.println("Current path: " + clasAst.getNode().findCompilationUnit().get().getStorage().get().getPath());
		TypeDeclaration<?> n = clasAst.getNode();
		//get the values for CA and NCIA using their respective functions
		double CA = CAVal(n);
		double NCMVal = NCMethod(n);
		//find the result via NCIA over CA
		//double resultVal = NCMVal / CA;
		double resultVal = NCMVal;
		//Store the result within the MetricResult class ***Redefine this comment later
		MetricResult result = new MetricResult(this.identifier, resultVal);
		//log identifier + results
		clasAst.getResults().put(this.identifier, result);
	}
	
}
