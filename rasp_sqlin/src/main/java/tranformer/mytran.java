package tranformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import sqlin.MysqlVistor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class mytran implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!"test/info1".equals(className)) return classfileBuffer; //判断加载进来的类
        ClassReader classReader=new ClassReader(classfileBuffer);  //读取器
        ClassWriter classWriter=new ClassWriter(classReader,ClassWriter.COMPUTE_FRAMES); //写入器
        ClassVisitor visitor=new MysqlVistor(Opcodes.ASM5,classWriter);  //构造器
        classReader.accept(visitor,ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);  //启动器
        return classWriter.toByteArray();  //返回字节流
    }
    }

