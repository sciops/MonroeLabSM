/*
 * The MIT License
 *
 * Copyright 2016 Stephen R. Williams.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mycompany.monroelabsm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import spark.servlet.SparkFilter;

/**
 *
 * @author Stephen R. Williams
 * 
 * https://github.com/perwendel/spark/issues/373
 */
public class ApplicationFilter extends SparkFilter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String requestUrl = ((HttpServletRequest) request).getRequestURI().toString();

        Map<String, String> mimeMapping = new HashMap<>();
        mimeMapping.put("/","text/html");
        mimeMapping.put(".css","text/css");
        mimeMapping.put(".js","text/javascript");
        mimeMapping.put(".html","text/html");
        mimeMapping.put(".htm","text/html");

        for(Map.Entry<String,String> entry : mimeMapping.entrySet())
        {
            if(requestUrl.endsWith(entry.getKey()))
            {
                ((HttpServletResponse) response).setHeader("Content-Type",entry.getValue());
            }
        }
        super.doFilter(request, response, chain);
    }
}
