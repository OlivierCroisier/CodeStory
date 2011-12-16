package net.thecodersbreakfast.codestory.foobarqix;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.File;

/**
 * @author olivier
 */
public class Setup {

    public static void main(String[] args) {
        verifyArgs(args);

        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            cl = pool.get("java.lang.Integer");
            CtMethod toStringMethod = cl.getDeclaredMethod("toString", new CtClass[]{CtClass.intType});
            toStringMethod.setBody(getCode());
            cl.writeFile(args[0]);
        } catch (Exception e) {
            System.err.println("Could not instrument : " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
    }

    private static void verifyArgs(String[] args) {
        if (args.length==0) {
            System.err.println("Usage : Setup <dir>");
            System.err.println("Where <dir> is some directory. Remember it, you'll need it to launch the FooBarQix main class !");
            System.exit(0);
        }
        File dir = new File(args[0]);
        if (!dir.exists()) {
            System.err.println("Directory "+dir.getPath()+" does not exist.");
            System.exit(0);
        }
    }

    private static String getCode() {
        return
        "{" +
        "if ($1 == Integer.MIN_VALUE) return \"-2147483648\";\n" +
        "int size = ( $1 < 0) ? stringSize(0 - $1 ) + 1 : stringSize( $1 );\n" +
        "char[] buf = new char[size];\n" +
        "getChars( $1, size, buf);\n" +
        "StringBuilder fbqBuffer = new StringBuilder();\n" +
        "if ( $1 % 3 == 0) fbqBuffer.append(\"Foo\");\n" +
        "if ( $1 % 5 == 0) fbqBuffer.append(\"Bar\");\n" +
        "if ( $1 % 7 == 0) fbqBuffer.append(\"Qix\");\n" +
        "for (int idx=0; idx<buf.length; idx++) {\n" +
        "    char c = buf[idx];\n" +
        "    if (c=='3') fbqBuffer.append(\"Foo\");\n" +
        "    if (c=='5') fbqBuffer.append(\"Bar\");\n" +
        "    if (c=='7') fbqBuffer.append(\"Qix\");\n" +
        "}\n" +
        "String result = fbqBuffer.toString();\n" +
        "return result.isEmpty() ? new String(0, size, buf) : result;" +
        "}";
    }

}
