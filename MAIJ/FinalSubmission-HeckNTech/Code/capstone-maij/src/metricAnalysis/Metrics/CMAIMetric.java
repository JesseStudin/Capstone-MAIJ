package metricAnalysis.Metrics;
//import java.util.ArrayList;
import java.util.Arrays;

//import com.github.javaparser.Range;
//import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import astManager.ClassAST;
//import javassist.compiler.ast.MethodDecl;
import metricAnalysis.MetricResult;

public class CMAIMetric extends Metric {
	
	public CMAIMetric() {
		super();
		this.levels = Arrays.asList(MetricLevel.CLASS);
	}
	
	
	/*
	 * Finds the CA Value
	 */
//	private double CAVal(TypeDeclaration<?> n) {
//		double value = 0.0;
//		for(FieldDeclaration x : n.getFields()) {
//			if(x.toString().contains("/*secrecy*/")) {
//				System.out.println("Found a CA: " + x.toString() + "\n"); 
//				value++;
//			}		
//		}
//		return value;
//	}//end CAVal function
	
	
	/*
	 * Finds the MM Value
	 */
	private double MMVal(TypeDeclaration<?> n) {
		double value = 0.0;
		for(MethodDeclaration x : n.getMethods()) {
			if(x.toString().contains("set")) {
				System.out.println("Found a MM: " + x.toString() + "\n"); 
				value++;
			}
		}
		return value;
	}//end MMVal function
	
	
	/*
	 * Finds  the number of mutator methods which may access classified attribute CAj Value
	 */
	
//	private double MCAVal(TypeDeclaration<?> n) {
//		double value = 0.0;
//		return value;
//		
//		
//	}
	
	public void run(ClassAST clasAst) {
		//if it exists
		if(this.alreadyDone(clasAst)) return;
		
		System.out.println("Current path: " + clasAst.getNode().findCompilationUnit().get().getStorage().get().getPath());
		TypeDeclaration<?> n = clasAst.getNode();
//		double CA = CAVal(n);
		double MM = MMVal(n);
//		double MCAVal = MCAVal(n);
		double resultVal = MM;
		MetricResult result = new MetricResult(this.identifier, resultVal);
		clasAst.getResults().put(this.identifier, result);
	}
		
}
