package asms;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * 自定义ClassVisitor方法
 * 修改go方法字节码
 */

public class MyClassVisitor  extends ClassVisitor {
    /**
     * 必须创建的构造器
     * @param i
     * @param classVisitor
     */
    public MyClassVisitor (int i, ClassVisitor classVisitor) {
        super(i, classVisitor);
    }

    /**
     * 重写修改方法的方法
     * @param i
     * @param s
     * @param s1
     * @param s2
     * @param strings
     * @return
     */

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        MethodVisitor mv=super.visitMethod( i,  s,  s1,  s2, strings);
        if ("go".equals(s)){
            System.out.println("[+]start insert def:{ go }");
            return new MyMethodVisitor(api,mv,i,s,s1);
        }
        return mv;
    }

}
