/*
 * Critical Design Proportion (CDP)
 * The ratio of number of critical classes to the total number
	of classes in a design
 */

package metricAnalysis.Metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import astManager.ClassAST;
import astManager.PackageAST;
import metricAnalysis.MetricResult;
import metricAnalysis.Metrics.Visitors.CDPMetricVisitor;

//C:\Users\User\Desktop\QUT\2019sem1\IFB398\testcases

public class CDPMetric extends Metric{
	
	public CDPMetric() {
		super();
		this.levels = Arrays.asList(MetricLevel.PACKAGE);	
	}
	
	//number of critical classes
	private double CCVal(PackageAST packast) {
		
		double critical = 0.0;
		Set<CompilationUnit> cus = packast.getCus();
		
		for(CompilationUnit cu : cus) {
			if(cu.toString().contains("/*critical*/")) {
				System.out.println(cu);
				critical++;
			}
		}
		
		return critical;
	}
	
	//total number of classes
	private double CVal(PackageAST packAst) {
		
		Integer[] value = new Integer[1];
		value[0] =  0;
		ArrayList<Range> poi = new ArrayList<Range>();
		
		Set<CompilationUnit> cus = packAst.getCus();
		
		CDPMetricVisitor visitor = new CDPMetricVisitor(poi);
		for(CompilationUnit cu : cus) {
			cu.accept(visitor, value);
		}
		
		return (double) value[0];
	}
	
	@Override
	public void run(PackageAST packAst) {
		
		if(this.alreadyDone(packAst)) return;
		
		double cdp = CCVal(packAst) / CVal(packAst);
		
		MetricResult result = new MetricResult( identifier, cdp);
		packAst.getResults().put(identifier, result);
		
	}
}
