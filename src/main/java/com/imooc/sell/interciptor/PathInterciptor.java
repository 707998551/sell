package com.imooc.sell.interciptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PathInterciptor implements HandlerInterceptor {

    private boolean redirect  = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String path = request.getServletPath();
//        String servletPath =path.substring(0, path.lastIndexOf("/"));
        String url = request.getRequestURL().toString();

//        if (request.getAttribute("redirect") == null) {
//            request.setAttribute("redirect", true);
//        }
//        Boolean redirect = (Boolean) request.getAttribute("redirect");
        if(path.equalsIgnoreCase("/pay/create") && redirect ){
            path = path + "/redirect";
            redirect = false;
            request.getRequestDispatcher(path).forward(request,response);
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
