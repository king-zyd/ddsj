package org.ddsj.web.interceptor;

import com.zyd.core.platform.web.site.layout.ModelBuilder;

import java.util.Map;

/**
 * @author zyd
 */
public class MasterTemplateModelBuilder implements ModelBuilder {
    @Override
    public void build(Map<String, Object> model) {
        model.put("master_name", "master_name_value");
    }
}
