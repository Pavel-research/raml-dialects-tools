package org.raml.dialects2java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.raml.dialects.core.annotations.AlsoMappedTo;
import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.Hash;
import org.raml.dialects.core.annotations.PropertyTerm;
import org.raml.dialects.toplevel.model.NodeMapping;
import org.raml.dialects.toplevel.model.PropertyMapping;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JArray;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JFormatter;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JStatement;
import com.sun.codemodel.JType;

public class SimpleBeanGenerator {

	private JavaWriter writer;
	private boolean booleanAsIs = false;

	public SimpleBeanGenerator(JavaWriter wr) {
		this.writer = wr;

	}

	public JType define(NodeMapping t) {
		JDefinedClass defineClass = writer.defineClass(t, ClassType.CLASS);
		writer.defined.put(t, defineClass);
		t.getMappings().values().forEach(v -> generateProperty(defineClass, v));
		defineClass.annotate(ClassTerm.class).param("value",t.getClassTerm());
		return defineClass;
	}

	private void generateProperty(JDefinedClass defineClass, PropertyMapping p) {
		String oname = writer.propNameGenerator.name(p);
		ArrayList<NodeMapping> rr = p.getRange();

		String name = oname;
		JType propType = writer.getType(p.getRange(), false, false, p);
		JExpression initExpr = null;

		if (p.isAsMap()) {
			JType oType = propType;
			propType = ((JClass) writer.getModel()._ref(Map.class)).narrow(String.class).narrow(propType);
			initExpr = JExpr
					._new(((JClass) writer.getModel()._ref(LinkedHashMap.class)).narrow(String.class).narrow(oType));
		}

		if (p.isAllowMultiple()) {
			initExpr = writer.toArrayInit(p.getRange(), p);
			propType = ((JClass) writer.getModel()._ref(LinkedHashSet.class)).narrow(propType);
		} else {
			// Default dv = p.getRange().oneMeta(Default.class);
			// if (dv != null) {
			// if (p.getRange().isScalar()) {
			// if (p.getRange().isString()) {
			// initExpr = JExpr.lit("" + dv.value());
			// }
			// if (p.getRange().isBoolean()) {
			// initExpr = JExpr.lit(Boolean.parseBoolean("" + dv.value()));
			// }
			//
			// else if (p.getRange().isNumber()) {
			// String name2 = propType.name().toLowerCase();
			// if (name2.equals("float")) {
			// initExpr = JExpr.lit(Float.parseFloat((("" + dv.value()))));
			// } else if (name2.equals("double")) {
			// initExpr = JExpr.lit(Double.parseDouble((("" + dv.value()))));
			// } else if (name2.equals("short")) {
			// initExpr = JExpr.lit(Double.parseDouble((("" + dv.value()))));
			// } else if (name2.equals("long")) {
			// initExpr = JExpr.lit(Long.parseLong((("" + dv.value()))));
			// } else if (name2.equals("byte")) {
			// initExpr = JExpr.lit(Byte.parseByte((("" + dv.value()))));
			// } else {
			// initExpr = JExpr.lit(Double.parseDouble((("" + dv.value()))));
			// }
			// }
			// }
			// }
		}
		JClass _extends = defineClass._extends();
		boolean needField = true;
		JType ts = null;
		if (_extends != null && _extends instanceof JDefinedClass) {
			JDefinedClass ee = (JDefinedClass) _extends;
			JFieldVar jFieldVar = ee.fields().get(name);
			if (jFieldVar != null) {
				needField = false;
				jFieldVar.mods().setProtected();
				ts = jFieldVar.type();
			}

		}
		JFieldVar field = null;
		if (needField) {
			field = defineClass.field(JMod.PROTECTED, propType, name);
			field.annotate(PropertyTerm.class).param("value", p.getPropertyTerm());
			if (rr.size()>1){
				JAnnotationArrayMember paramArray = field.annotate(AlsoMappedTo.class).paramArray("value");
				for (NodeMapping m:rr){
					paramArray.param(JExpr.dotclass((JClass) writer.getOrDefine(m)));
				}
			}
			if (p.getHash()!=null){
				field.annotate(Hash.class).param("value", p.getHash());
			}
			if (initExpr != null) {
				field.init(initExpr);
			}
		}
		JMethod get = defineClass.method(JMod.PUBLIC, propType,
				"get" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
		
		JExpression ref = JExpr.ref(name);
		if (ts != null && !ts.equals(propType)) {
			ref = JExpr.cast(propType, ref);
		}

		get.body()._return(ref);

		JMethod set = defineClass.method(JMod.PUBLIC, writer.getModel()._ref(void.class),
				((false && this.booleanAsIs) ? "is" : "set") + Character.toUpperCase(name.charAt(0))
						+ name.substring(1));
		set.param(propType, "value");
		set.body().add(new JStatement() {

			@Override
			public void state(JFormatter f) {
				f.p("this." + name + "=value;");
				f.nl();
			}
		});
		if (writer.getConfig().isGenerateBuilderMethods()) {
			JMethod builder = defineClass.method(JMod.PUBLIC, defineClass,
					"with" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
			builder.param(propType, "value");
			builder.body().add(new JStatement() {

				@Override
				public void state(JFormatter f) {
					f.p("this." + name + "=value;");
					f.nl();
					f.p("return this;");
					f.nl();
				}
			});
		}
	}

}
