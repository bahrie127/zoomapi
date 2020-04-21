package Util;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import java.util.ArrayList;
import java.util.List;

public class RequireKeys {

//    FIXME: Might need to come back and fix
    public static boolean requireKeys(List<String> userSentValue, Object valueThatWeWant) {

        List<String> ourHardCodedString = new ArrayList<>();

        if(isString(valueThatWeWant)) {
            ourHardCodedString.add((String) valueThatWeWant);
        }

        for(int i = 0; i < ourHardCodedString.size(); i++) {
            if(ourHardCodedString.get(i).contains(userSentValue.get(i)))
                return true;
            else {
                System.out.println("Missing Keyword: " + ourHardCodedString.get(i));
            }
        }
        return false;
    }

    public static boolean isString(Object value) {
        if(value.getClass().equals(Type.String)) {
            return true;
        }
        return false;
    }
}
