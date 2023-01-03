package sqlin;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class MysqlVisitorAdapter extends AdviceAdapter {
    /**
     * 重写的有参构造器
     * @param methodVisitor
     * @param access
     * @param name
     * @param descriptor
     */
    protected MysqlVisitorAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, name, descriptor);
    }
    /**
     * 重写onMethodEnter：代码进入时执行
     */
    @Override
    protected void onMethodEnter() {

        /**
         * 如果没有恶意代码的话，所有执行的字节码都会执行，1段
         */
        //第一段
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");  //启动打印函数得方式
        mv.visitLdcInsn("[+]Starting~~~");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);

        //第二段：如果在MySQLVistor判断(if)条件走过来的，则执行以下操作
        Label label = new Label();
        mv.visitJumpInsn(IFNE, label);  //IFNE:判断语句
        mv.visitTypeInsn(NEW, "java/sql/SQLException");
        mv.visitInsn(DUP);   //DUP:复制流
        mv.visitLdcInsn("The sql statement you entered is invalid!!!");
        mv.visitMethodInsn(INVOKESPECIAL, "java/sql/SQLException", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(label);  //最后label代码会放到这里执行
    }
}
