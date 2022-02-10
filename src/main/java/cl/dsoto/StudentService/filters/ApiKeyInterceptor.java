package cl.dsoto.StudentService.filters;

import cl.dsoto.StudentService.configuration.AppPropsConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by root on 24-01-22.
 */
@Component
public class ApiKeyInterceptor implements RequestInterceptor {

    @Autowired
    private AppPropsConfig appPropsConfig;


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("apikey", appPropsConfig.getKey());
    }
}
