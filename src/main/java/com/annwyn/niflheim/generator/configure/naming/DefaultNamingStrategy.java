package com.annwyn.niflheim.generator.configure.naming;

import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

public class DefaultNamingStrategy implements NamingStrategy, InitializingBean {

	@Resource
	private NiflheimProperties niflheimProperties;

	private final List<String> prefixes = new ArrayList<>();
	private final List<String> suffixes = new ArrayList<>();

	@Override
	public String switchClassName(String name) {
		if(!CollectionUtils.isEmpty(prefixes)) {
			name = this.removePrefix(name, prefixes);
		}
		if(!CollectionUtils.isEmpty(suffixes)) {
			name = this.removeSuffix(name, suffixes);
		}
		return this.firstToUpperCase(this.underlineToCamel(name));

	}

	@Override
	public String switchFieldName(String name) {
		return this.firstToLowerCase(this.underlineToCamel(name));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(!CollectionUtils.isEmpty(this.niflheimProperties.getRemovePrefixes())) {
			this.prefixes.addAll(this.niflheimProperties.getRemovePrefixes());
		}
		if(!CollectionUtils.isEmpty(this.niflheimProperties.getRemoveSuffixes())) {
			this.suffixes.addAll(this.niflheimProperties.getRemoveSuffixes());
		}
	}
}
