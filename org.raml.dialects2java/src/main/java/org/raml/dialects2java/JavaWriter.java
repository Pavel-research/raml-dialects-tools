package org.raml.dialects2java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.annotation.Generated;

import org.raml.dialects.core.annotations.DomainRootElement;
import org.raml.dialects.toplevel.model.Builtins;
import org.raml.dialects.toplevel.model.Dialect;
import org.raml.dialects.toplevel.model.NodeMapping;
import org.raml.dialects.toplevel.model.PropertyMapping;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;


public class JavaWriter {
	private JCodeModel mdl = new JCodeModel();
	private JavaGenerationConfig cfg = new JavaGenerationConfig();
	protected HashMap<NodeMapping, JType> defined = new HashMap<>();
	protected INameGenerator nameGenerator = new DefaultNameGenerator("org.aml.test");
	public IPropertyNameGenerator propNameGenerator = new IPropertyNameGenerator() {

		@Override
		public String name(PropertyMapping p) {
			return escape(p.getName());
		}
	};

	public JDefinedClass defineClass(NodeMapping t, ClassType type) {
		String fullyQualifiedName = nameGenerator.fullyQualifiedName(t);
		try {
			JDefinedClass _class = mdl._class(fullyQualifiedName, type);
			{
				_class.annotate(Generated.class).param("value", JavaWriter.class.getPackage().getName()).param("date",
						new Date().toString());
			}

			// if (t.hasDirectMeta(Description.class)) {
			// String description = t.oneMeta(Description.class).value();
			// _class.javadoc().add(description);
			// }
			return _class;
		} catch (JClassAlreadyExistsException e) {
			throw new IllegalStateException(e);
		}
	}

	public JCodeModel getModel() {
		return mdl;
	}



	public void setDefaultPackageName(String string) {
		cfg.setDefaultPackageName(string);
		this.nameGenerator = new DefaultNameGenerator(string);
	}

	public void write(Dialect build) {
		build.getNodeMappings().values().forEach(v -> {
				new SimpleBeanGenerator(this).define(v);
		});
		JDefinedClass jType = (JDefinedClass) defined.get(build.getRaml().getDocument().getEncodes());
		jType.annotate(DomainRootElement.class).param("name", build.getDialect()).param("version", build.getVersion());
		System.out.println(build);
	}

	public static String escape(String x) {
		if (DefaultNameGenerator.isKeyword(x)) {
			x = "_" + x;
		}
		StringBuilder bld = new StringBuilder();
		for (int i = 0; i < x.length(); i++) {
			char c = x.charAt(i);
			if (i == 0) {
				if (!Character.isJavaIdentifierStart(c)) {
					c = '_';
				}
			} else {
				if (!Character.isJavaIdentifierPart(c)) {
					c = '_';
				}
			}
			bld.append(c);
		}
		return bld.toString();
	}

	public JavaGenerationConfig getConfig() {
		return cfg;
	}

	public JType getType(ArrayList<NodeMapping> range, boolean b, boolean c, PropertyMapping p) {
		if (range.size()==1){
			NodeMapping nodeMapping = range.get(0);
			if (nodeMapping==Builtins.BOOLEAN){
				return this.mdl._ref(boolean.class);
			}
			if (nodeMapping==Builtins.STRING){
				return this.mdl._ref(String.class);
			}
			if (nodeMapping==Builtins.INTEGER){
				return this.mdl._ref(Integer.class);
			}
			if (nodeMapping==Builtins.FLOAT){
				return this.mdl._ref(Double.class);
			}
			if (nodeMapping==Builtins.NUMBER){
				return this.mdl._ref(Double.class);
			}
			if (nodeMapping instanceof Builtins){
				return this.mdl._ref(String.class);
			}
			return getOrDefine(nodeMapping);
			
		}
		return mdl._ref(Object.class);
	}

	JType getOrDefine(NodeMapping nodeMapping) {
		if (defined.containsKey(nodeMapping)){
			return defined.get(nodeMapping);
		}
		else{
			return new SimpleBeanGenerator(this).define(nodeMapping);
		}
	}

	public JExpression toArrayInit(ArrayList<NodeMapping> range, PropertyMapping p) {
		JType type = getType(range, false, false, p);
		boolean set = true;

		if (set) {
			return JExpr._new(mdl.ref(LinkedHashSet.class).narrow(type));
		}
		return null;
	}

}
