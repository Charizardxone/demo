package com.example.security.auth;

import cn.hutool.core.util.ReUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Slf4j
@Configuration
public class AuthIgnoreConfig implements InitializingBean {

    @Autowired
    private WebApplicationContext applicationContext;

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final String ASTERISK = "*";

//    @Getter
//    @Setter
//    private List<String> ignoreUrls = new ArrayList<>();

    @Value("${security.white-list}")
    @Getter
    @Setter
    private List<String> ignoreUrls;

    @Override
    public void afterPropertiesSet(){
        RequestMappingHandlerMapping mapping = applicationContext.getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(mappingInfo -> {
            HandlerMethod handlerMethod = map.get(mappingInfo);
//            AuthIgnore method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), AuthIgnore.class);
            if(false){
                PatternsRequestCondition patternsCondition = mappingInfo.getPatternsCondition();
                if(patternsCondition != null){
                    patternsCondition.getPatterns().stream().forEach(url ->{
                        ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK));
                    });
                }
            }
        });
    }

    public boolean isContains(String url){
        final String u = ReUtil.replaceAll(url, PATTERN, ASTERISK);
        return ignoreUrls.contains(u);
    }
}
