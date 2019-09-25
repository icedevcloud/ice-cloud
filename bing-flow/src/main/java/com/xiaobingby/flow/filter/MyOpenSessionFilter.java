package com.xiaobingby.flow.filter;


import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @author :xie
 * Email: 1487471733@qq.com
 * Date: 2019/9/21
 * Time: 21:18
 * Describe:
 */
//@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyOpenSessionFilter implements Filter {

    private  OpenSessionInViewFilter filter;

    public MyOpenSessionFilter( ) {
        filter = new OpenSessionInViewFilter();
        filter.setSessionFactoryBeanName("sessionFactory_soc");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filter.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        filter.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        filter.destroy();
    }
}
