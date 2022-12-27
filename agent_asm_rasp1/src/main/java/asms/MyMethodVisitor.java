package asms;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * 自定义MethodVisitor方法
 * 修改字节码
 */
public class MyMethodVisitor extends AdviceAdapter{
    /**
     * 必须创建的构造器
     * @param i
     * @param methodVisitor
     * @param i1
     * @param s
     * @param s1
     */
    public MyMethodVisitor(int i, MethodVisitor methodVisitor, int i1, String s, String s1) {
        super(i, methodVisitor, i1, s, s1);
    }

    /**
     * 重写onMethodEnter方法
     * 作用：在“方法进入”的时候，添加一些代码逻辑。
     * ARETURN代表返回有返回得方法
     * RETURN代表没有返回的void方法
     */
    @Override
    protected void onMethodEnter() {
//        mv.visitLdcInsn("[+]start insert asm");  //添加常量池中，并且打印
//        mv.visitInsn(ARETURN);
        mv.visitFieldInsn(GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        mv.visitLdcInsn("[+]start insert asm");
        mv.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
        mv.visitInsn(RETURN);


    }
}
