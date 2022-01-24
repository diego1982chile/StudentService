package cl.dsoto.StudentService.filters;

import cl.dsoto.StudentService.configuration.ApplicationProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.template.QueryTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by root on 24-01-22.
 */
@Component
public class ApiKeyInterceptor implements RequestInterceptor {

    @Autowired
    private ApplicationProperties applicationProperties;


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("apikey", applicationProperties.getKey());
    }
}
