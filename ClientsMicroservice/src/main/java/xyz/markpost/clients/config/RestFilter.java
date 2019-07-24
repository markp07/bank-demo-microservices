package xyz.markpost.clients.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * Filter for the REST API.
 */
@Component
public class RestFilter implements Filter {

  private static final String ALLOWED_ORIGIN = "*";
  private static final String ALLOWED_METHODS = "POST, GET, DELETE, PATCH";
  private static final String MAX_AGE = "36000";
  private static final String ALLOWED_HEADERS = "origin, content-type, accept";

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
    response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
    response.setHeader("Access-Control-Max-Age", MAX_AGE);
    response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
    chain.doFilter(req, res);
  }

}
