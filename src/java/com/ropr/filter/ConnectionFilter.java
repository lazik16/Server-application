package com.ropr.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class ConnectionFilter implements Filter {

 @Override
 public void destroy() {

 }

 @Resource
 private UserTransaction utx;

 @Override
 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
  try {
   utx.begin();
   chain.doFilter(request, response);
   utx.commit();
  } catch (NotSupportedException | SystemException | IOException | ServletException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
      e.printStackTrace();
  }

 }

 @Override
 public void init(FilterConfig arg0) throws ServletException {

 }
}