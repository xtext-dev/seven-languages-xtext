/**
 * Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.xtext.httprouting.tests;

import com.google.inject.Inject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.testing.CompilationTestHelper;
import org.eclipse.xtext.xbase.testing.TemporaryFolder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@InjectWith(RouteInjectorProvider.class)
@RunWith(XtextRunner.class)
@SuppressWarnings("all")
public class ParserTest {
  @Inject
  @Extension
  private CompilationTestHelper _compilationTestHelper;

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void testFirstRuleDoesNotMatch() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("GET /client/foo/:id/:name*  when id==\"43\" do response.addHeader(id, null)");
      _builder.newLine();
      _builder.append("GET /client/foo/:id/:name*  when id==\"42\" do response.addHeader(name + \"/\" + id, null)");
      _builder.newLine();
      final IAcceptor<CompilationTestHelper.Result> _function = (CompilationTestHelper.Result it) -> {
        try {
          Object _newInstance = it.getCompiledClass().getDeclaredConstructor().newInstance();
          final HttpServlet servlet = ((HttpServlet) _newInstance);
          HttpServletResponse _response = this.response();
          final Procedure1<HttpServletResponse> _function_1 = (HttpServletResponse it_1) -> {
            try {
              servlet.service(this.request("/client/foo/42/rest/of"), it_1);
              Assert.assertTrue("containsHeader(\'rest/of/42\')", it_1.containsHeader("rest/of/42"));
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          };
          ObjectExtensions.<HttpServletResponse>operator_doubleArrow(_response, _function_1);
          HttpServletResponse _response_1 = this.response();
          final Procedure1<HttpServletResponse> _function_2 = (HttpServletResponse it_1) -> {
            try {
              servlet.service(this.request("/client/foo/43/rest/of"), it_1);
              Assert.assertTrue("containsHeader(\'43\')", it_1.containsHeader("43"));
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          };
          ObjectExtensions.<HttpServletResponse>operator_doubleArrow(_response_1, _function_2);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      this._compilationTestHelper.compile(_builder, _function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * creates a HttpServletRequest proxy
   */
  public HttpServletRequest request(final String url) {
    final InvocationHandler _function = (Object proxy, Method method, Object[] args) -> {
      Object _switchResult = null;
      String _name = method.getName();
      if (_name != null) {
        switch (_name) {
          case "getRequestURL":
            _switchResult = new StringBuffer(url);
            break;
          case "getMethod":
            _switchResult = "GET";
            break;
        }
      }
      return _switchResult;
    };
    return this.<HttpServletRequest>newProxy(HttpServletRequest.class, _function);
  }

  /**
   * creates a HttpServletResponse proxy
   */
  public HttpServletResponse response() {
    HttpServletResponse _xblockexpression = null;
    {
      final HashSet<String> header = CollectionLiterals.<String>newHashSet();
      final InvocationHandler _function = (Object proxy, Method method, Object[] args) -> {
        boolean _switchResult = false;
        String _name = method.getName();
        if (_name != null) {
          switch (_name) {
            case "addHeader":
              Object _get = args[0];
              _switchResult = header.add(((String) _get));
              break;
            case "containsHeader":
              _switchResult = header.contains(args[0]);
              break;
            default:
              _switchResult = false;
              break;
          }
        } else {
          _switchResult = false;
        }
        return Boolean.valueOf(_switchResult);
      };
      _xblockexpression = this.<HttpServletResponse>newProxy(HttpServletResponse.class, _function);
    }
    return _xblockexpression;
  }

  /**
   * utility to create a proxy for a class and an invocation handler
   */
  private <T extends Object> T newProxy(final Class<T> clazz, final InvocationHandler handler) {
    Object _newProxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), ((Class<?>[])Conversions.unwrapArray(CollectionLiterals.<Class<?>>newArrayList(clazz), Class.class)), handler);
    return ((T) _newProxyInstance);
  }
}
