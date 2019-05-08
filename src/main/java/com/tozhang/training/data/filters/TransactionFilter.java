package com.tozhang.training.data.filters;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
@Order(1)
public class TransactionFilter implements Filter {

    private static final Logger logger = Logger.getLogger(TransactionFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Starting a transaction for req : {" + req.getMethod() + req.getRequestURI() + "}");

        chain.doFilter(request, response);
        logger.info("Finishing a transaction for req : {" + req.getMethod() + req.getRequestURI() +"}");
    }

}
