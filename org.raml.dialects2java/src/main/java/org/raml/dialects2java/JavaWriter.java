package org.raml.dialects2java;

import org.raml.dialects.toplevel.model.Dialect;

import com.sun.codemodel.JCodeModel;

public class JavaWriter {
	private JCodeModel mdl = new JCodeModel();
	private JavaGenerationConfig cfg=new JavaGenerationConfig();
	public JCodeModel getModel() {
		return mdl;
	}

	public void setDefaultPackageName(String string) {
		cfg.setDefaultPackageName(string);
	}

	public void write(Dialect build) {
		build.getNodeMappings().values().forEach(v->{
			
		});
		System.out.println(build);
	}
}
