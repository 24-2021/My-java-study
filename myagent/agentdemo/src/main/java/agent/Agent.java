package agent;

import tranfromer.mytran;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {
    public static void premain(String args, Instrumentation inst){
        System.out.println("welcome premain");
        inst.addTransformer(new mytran());
        try {
            inst.retransformClasses(mytran.class);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }
    public static void agentmain(String args, Instrumentation inst){
        System.out.println("welcome agentmain");
        inst.addTransformer(new mytran());
        try {
            inst.retransformClasses(mytran.class);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }
}
