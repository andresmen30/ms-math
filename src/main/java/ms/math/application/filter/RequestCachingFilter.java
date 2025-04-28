package ms.math.application.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ms.math.application.response.interceptor.CachedBodyHttpServletResponse;

@Component
public class RequestCachingFilter implements Filter {

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if (request instanceof HttpServletRequest httpServletRequest && response instanceof HttpServletResponse httpServletResponse) {
         final ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
         final CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse(httpServletResponse);
         chain.doFilter(wrappedRequest, wrappedResponse);
         wrappedResponse.flushBuffer();
      } else {
         chain.doFilter(request, response);
      }
   }
}
