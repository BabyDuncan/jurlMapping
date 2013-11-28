package com.babyduncan;

import com.babyduncan.servlet.DispatchFilter;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/28/13 14:32
 */
public class MyDispatchFilter extends DispatchFilter {

    @Override
    protected void configure() {
        forward("/index.jsp", "/index/$Username");
    }
}
