package sqlin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashSet;
import java.util.Set;

public class MysqlVistor extends ClassVisitor {
    /**
     * 执行恶意查询的关键字
     */
    private static final Set<String> methodName = new HashSet<String>() {{
        add("executeQuery");
        add("execute");
        add("executeUpdate");
        add("addBatch");
    }};

    /**
     * 执行恶意查询调用的方法
     */
    private static final Set<String> descName = new HashSet<String>() {{
        add("(Ljava/lang/String;I)I");   //
        add("(Ljava/lang/String;[Ljava/lang/String;)I");
        add("(Ljava/lang/String;[I)I");
        add("(Ljava/lang/String;)Ljava/sql/ResultSet;");
        add("(Ljava/lang/String;)Z");
        add("(Ljava/lang/String;I)Z");
        add("(Ljava/lang/String;[Ljava/lang/String;)Z");
        add("(Ljava/lang/String;[I)Z");
        add("(Ljava/lang/String;[I)J");
        add("(Ljava/lang/String;[Ljava/lang/String;)J");
        add("(Ljava/lang/String;I)J");
        add("(Ljava/lang/String;)J");
        add("(Ljava/lang/String;)I");
    }};
    public String className;

    /**
     * 构造器
     * @param cv
     * @param className
     */
    public MysqlVistor (int i, ClassVisitor classVisitor) {
        super(i, classVisitor);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv=super.visitMethod(access, name, descriptor, signature, exceptions);
        /**
         *  如果列表里面有加载的恶意类，以及调用恶意api，则执行下列操作：
         *  调用：MySQLVisitorAdapter方法
         *  作用：再指定处进行插入相关字节码，用来阻止恶意操作
         *  符合条件则直接跳转到Label处代码
         */
//        if (methodName.contains(name)&& descName.contains(descriptor)){
            mv=new MysqlVisitorAdapter(mv,access,name,descriptor);
//        }
        return mv;
    }
}
