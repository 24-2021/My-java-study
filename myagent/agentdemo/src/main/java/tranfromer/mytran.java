package tranfromer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class mytran implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String classname = className.replaceAll("/",".");
        if (classname.equals("run.info")){   //注意大小写
            System.out.println("My load class:"+classname);
            ClassPool classPool=ClassPool.getDefault();
            CtClass ctClass=null;
            CtMethod ctMethod=null;
            try {
                ctClass=classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                ctMethod=ctClass.getDeclaredMethod("go");
                ctMethod.insertBefore("{ System.out.println(\"[+]mamba\"); }");
                ctMethod.insertAfter("{ System.out.println(\"[+]world\"); }");
                return ctClass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}
