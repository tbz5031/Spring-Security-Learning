package com.tozhang.training.data.filters;
import com.tozhang.training.util.IDMResponse;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class TransactionFilter implements Filter {

    private static final Logger logger = Logger.getLogger(TransactionFilter.class);
    public static TransactionFilter transactionFilter;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info(req.getRequestURL().toString()+ " Before transaction filter");
        logger.info("Starting a transaction for req : {" + req.getMethod() + req.getRequestURI() + "}");
        chain.doFilter(request, response);
        logger.info(req.getRequestURL().toString()+ " After transaction filter");
        logger.info("Finishing a transaction for req : {" + req.getMethod() + req.getRequestURI() +"}");
    }

}
