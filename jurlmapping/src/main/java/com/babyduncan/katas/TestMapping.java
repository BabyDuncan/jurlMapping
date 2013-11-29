package com.babyduncan.katas;

import com.babyduncan.pattern.Element;
import com.babyduncan.pattern.PathInput;
import com.babyduncan.pattern.PathPattern;
import com.babyduncan.pattern.StringElement;
import com.babyduncan.servlet.ServletAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: guohaozhao (guohaozhao116008@sohu-inc.com)
 * Date: 11/28/13 14:48
 */
public class TestMapping {

    /**
     * 匹配模式
     * /profile/$Username/$Age -> profile.jsp
     * /profile/aaa/bbb  ->  /profile.jsp?Username=aaa&Age=bbb
     *
     * @param args
     */
    public static void main(String... args) {
        Map<String, List<PathPattern<ServletAction>>> map = new HashMap<String, List<PathPattern<ServletAction>>>();

        //init one servlet action
        ServletAction servletAction = new ServletAction("profile.jsp", ServletAction.Type.REDIRECT);
        PathPattern<ServletAction> pattern = new PathPattern<ServletAction>(servletAction, "/profile/$Username/$Age", true, 3);
        String key = pattern.getKey();
        List<PathPattern<ServletAction>> list = new ArrayList<PathPattern<ServletAction>>();
        map.put(key, list);
        list.add(pattern);
        // init map over .

        PathInput input = new PathInput("/profile/aaa/bbb", 1);

        String key___ = input.value();
        List<PathPattern<ServletAction>> list___ = map.get(key___);

        for (PathPattern<ServletAction> pattern___ : list___) {
            if (pattern___.match(input)) {
                System.out.println(pattern___.getTarget().target);
                Map<String, String> params = new HashMap<String, String>();
                List<String> paramsKeys = new ArrayList<String>();
                List<String> paramsValues = new ArrayList<String>();
                for (String s___ : input.items) {
                    paramsKeys.add(s___);
                }
                for (Element e : pattern___.elements) {
                    if (e instanceof StringElement) {
                        paramsValues.add(((StringElement) e).getProperties());
                    }
                }
                for (int i = 0; i < paramsValues.size(); i++) {
                    params.put(paramsKeys.get(i + 1), paramsValues.get(i));
                }

                System.out.println(params);

            }
        }
    }

}
