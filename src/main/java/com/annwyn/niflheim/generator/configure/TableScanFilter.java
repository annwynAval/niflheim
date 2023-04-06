package com.annwyn.niflheim.generator.configure;

import com.annwyn.niflheim.configuration.properties.NiflheimProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;

@Component
public class TableScanFilter {

	@Resource
	private NiflheimProperties niflheimProperties;

	private final PathMatcher pathMatcher = new AntPathMatcher();

	public boolean include(String tableName) {
		if(CollectionUtils.isEmpty(this.niflheimProperties.getIncludeTables())) {
			return true; // 如果没有配置包含的表信息, 表示全部允许
		}

		for (String pattern : this.niflheimProperties.getIncludeTables()) {
			if(this.pathMatcher.match(pattern, tableName)) {
				return true;
			}
		}

		return false;
	}

}
