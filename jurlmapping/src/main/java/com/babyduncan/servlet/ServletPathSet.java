/*
 *  jurlmap - RESTful URLs for Java.
 *  Copyright (C) 2009, 2010 Manuel Tomis manuel@codegremlins.com
 *
 *  This library is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.babyduncan.servlet;

import com.babyduncan.Deploy;
import com.babyduncan.pattern.AbstractPathSet;
import com.babyduncan.pattern.HttpMethods;
import com.babyduncan.pattern.PathInput;
import com.babyduncan.pattern.PathPattern;

@SuppressWarnings("unchecked")
class ServletPathSet extends AbstractPathSet<Object, ServletAction> {
    private int defaultHttpMethods = HttpMethods.GET | HttpMethods.POST;

    public void setDefaultHttpMethods(int defaultHttpMethods) {
        this.defaultHttpMethods = defaultHttpMethods;
    }

    public void forward(String target, String path) {
        addPath(path, new ServletAction(target, ServletAction.Type.FORWARD), true, defaultHttpMethods);
    }

    public void redirect(String target, String path) {
        addPath(path, new ServletAction(target, ServletAction.Type.REDIRECT), true, defaultHttpMethods);
    }

    public void relocate(String target, String path) {
        addPath(path, new ServletAction(target, ServletAction.Type.RELOCATE), true, defaultHttpMethods);
    }

    public void deploy(Class clazz, String[] mpath) {
        if (mpath != null) {
            for (String path : mpath) {
                addPath(path, new ServletAction(clazz), true, defaultHttpMethods);
            }
        }

        Deploy deploy = (Deploy) clazz.getAnnotation(Deploy.class);
        if (deploy != null) {
            for (String path : deploy.value()) {
                addPath(path, new ServletAction(clazz), true, defaultHttpMethods);
            }
        }
    }

    public void deploy(Class clazz) {
        deploy(clazz, null);
    }

    protected Object match(PathPattern<ServletAction> pattern, PathInput input, Object self) {
        return pattern.getTarget().match(pattern, input, (MapHttpServletRequest) self);
    }
}