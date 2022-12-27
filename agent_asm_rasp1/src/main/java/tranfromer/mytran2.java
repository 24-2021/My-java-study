package tranfromer;


import asms.MyClassVisitor;
import org.objectweb.asm.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;


public class mytran2 implements ClassFileTransformer, Opcodes {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!"agent/run".equals(className)) return classfileBuffer; //判断加载进来的类
            ClassReader classReader=new ClassReader(classfileBuffer);  //读取器
            ClassWriter classWriter=new ClassWriter(classReader,ClassWriter.COMPUTE_FRAMES); //写入器
            ClassVisitor visitor=new MyClassVisitor(ASM7,classWriter);  //构造器
            classReader.accept(visitor,ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);  //启动器
            return classWriter.toByteArray();  //返回字节流
    }

    }

