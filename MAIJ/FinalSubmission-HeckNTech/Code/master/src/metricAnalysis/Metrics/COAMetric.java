package metricAnalysis.Metrics;
import java.util.ArrayList;
import java.util.Arrays;

import com.github.javaparser.Range;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import astManager.ClassAST;
import javassist.compiler.ast.MethodDecl;
import metricAnalysis.MetricResult;

public class COAMetric extends Metric {
	
	private ClassAST clasAst;


	public COAMetric() {
		super();
		this.levels = Arrays.asList(MetricLevel.CLASS);
	}
	
	
	/*
	 * Finds the CM Value(number of classified methods)
	 */
	
	private double CMVal(TypeDeclaration<?> n) {
		
		double value = 0.0;
		
		for(FieldDeclaration x : n.getFields()) {
			for(MethodDeclaration y : n.getMethods()) {
				System.out.println("x string" + x.toString());
				if(x.toString().contains("/*secrecy*/")) {
					System.out.println("entered loop");
					String attr = x.getVariables().toString();
					attr = attr.replaceAll("\\[", "").replaceAll("\\]", "");
					if(y.toString().contains(attr)) {
						System.out.println("entered if loop");
						value++;
					}
				}
			}
		}
		return value;
	}//end CMVal function
	
	
	
	
	/*
	 * Find the total for NCM(number of non-private classified methods)
	 */
	private double NCMVal(TypeDeclaration<?> n) {
		
		double value = 0.0;
		
		for(FieldDeclaration x : n.getFields()) {
			for(MethodDeclaration y : n.getMethods()) {
				if(x.toString().contains("/*secrecy*/")&& x.toString().contains("public")) {
					String attr = x.getVariables().toString();
					attr = attr.replaceAll("\\[", "").replaceAll("]\\", "");
					if(y.toString().contains(attr)) {
						value++;
					}	
				}
			}
		}
		return value;
	}//end NCMVal function
	
	
	public void run(ClassAST clasAst) {
		//if it exists
		if(this.alreadyDone(clasAst)) return;
		
		System.out.println("Current path: " + clasAst.getNode().findCompilationUnit().get().getStorage().get().getPath());
		TypeDeclaration<?> n = clasAst.getNode();
		//get the values for CM and NCM using their respective functions
		double CM = CMVal(n);
		double NCM = NCMVal(n);
		//find the result via NCM over CM
		double resultVal = NCM / CM;
		//Store the result within the MetricResult class ***Redefine this comment later
		MetricResult result = new MetricResult(this.identifier, resultVal);
		//log identifier + results
		clasAst.getResults().put(this.identifier, result);
	}
}