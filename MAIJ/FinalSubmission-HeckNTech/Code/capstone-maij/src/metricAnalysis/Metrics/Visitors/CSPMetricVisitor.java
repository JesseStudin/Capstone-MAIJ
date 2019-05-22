package metricAnalysis.Metrics.Visitors;

import java.util.ArrayList;
import java.util.HashMap;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

@interface critical {
	
}

public class CSPMetricVisitor extends VoidVisitorAdapter<HashMap<String, ArrayList<String>>> {

	ArrayList<String> list;
	
	public CSPMetricVisitor() {

	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, HashMap<String, ArrayList<String>> arg) {
		super.visit(n, arg);
		
		if(n.isAnnotationPresent(critical.class)) {

			if (n.getExtendedTypes().isEmpty()) {
				list = new ArrayList<String>(arg.get("undefine"));
				list.add(n.getNameAsString());
				arg.put("undefine", list);
			}
			else {
				list = new ArrayList<String>(arg.get("subclass"));
				list.add(n.getNameAsString());
				arg.put("subclass", list);
			}
		}
		
		if (n.getExtendedTypes().isNonEmpty()) {
			String s = n.getExtendedTypes().toString();
			s = s.substring(1, s.length()-1);
			
			if (!(list.contains(s))) {
				list = new ArrayList<String>(arg.get("superclass"));
				list.add(s);
				arg.put("superclass", list);
			}
		}

		//System.out.println("class:"+n.getName());
        //System.out.println("extends:"+n.getExtendedTypes());
        //System.out.println("implements:"+n.getImplementedTypes());
	}
}
