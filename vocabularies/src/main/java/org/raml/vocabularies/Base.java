package org.raml.vocabularies;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class Base {
	
	public Base() {
		
	}
	
	public Base(Object dto) {
		copyFrom(dto,this);
	}

	public static void copyFrom(Object dto,Object target) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(dto.getClass());
			BeanInfo targetInfo = Introspector.getBeanInfo(target.getClass());			
			HashMap<String,PropertyDescriptor>ts=new HashMap<String, PropertyDescriptor>();
			for (PropertyDescriptor p:targetInfo.getPropertyDescriptors()) {
				ts.put(p.getName(), p);
			}
			for (PropertyDescriptor d:beanInfo.getPropertyDescriptors()) {
				if (!ts.containsKey(d.getName())) {
					throw new IllegalStateException("Can not map property:"+d.getName());
				}
				PropertyDescriptor pd=ts.get(d.getName());
				if(pd.getPropertyType()==Map.class) {
					continue;
				}
				try {
					if (pd.getWriteMethod()==null) {
						if (d.getWriteMethod()!=null) {
						throw new IllegalStateException("Can not map property:"+d.getName());
						}
						else {
							continue;
						}
					}
					pd.getWriteMethod().invoke(target, d.getReadMethod().invoke(dto));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
