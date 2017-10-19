package org.raml.dialects2java;

import java.util.HashSet;

import org.raml.dialects.toplevel.model.NodeMapping;

public class DefaultNameGenerator implements INameGenerator{

	protected String defaultPackageName;
	protected HashSet<String>used=new HashSet<>();
	
	static String[] keywords=new String[]{"package","class","interface","private","public","protected","volatile","for","while","do","break","continue","if","synhronized","trasient","implements","extends","enum","goto","static","boolean","int","double","float","long","short","null","byte","char"};

	
	static HashSet<String>ks=new HashSet<>();
	static{
	for (String s:keywords){
		ks.add(s);
	}
	}
	
	public static boolean isKeyword(String v){
		return ks.contains(v);
	}
	
	public DefaultNameGenerator(String string) {
		this.defaultPackageName=string;
		
	}


	@Override
	public String fullyQualifiedName(NodeMapping t) {
		String name = t.getName();
		name=JavaWriter.escape(name);
		
		
		for (String s:keywords){
			if (name.equals(s)){
				name=Character.toUpperCase(name.charAt(0))+name.substring(1);
			}
		}
		String defaultPackageName2 = defaultPackageName;
		
		String string = defaultPackageName2+"."+name;
		
		if (!used.add(string)){
			for (int i=2;i<Integer.MAX_VALUE;i++){
				string = defaultPackageName2+"."+name+i;
				if (used.add(string)){
					return string;
				}
			}
		}
		return string;
	}
}
