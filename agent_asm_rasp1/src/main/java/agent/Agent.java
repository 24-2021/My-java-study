package agent;

import tranfromer.mytran;
import tranfromer.mytran1;
import tranfromer.mytran2;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {
    public static void premain(String args, Instrumentation inst) throws UnmodifiableClassException {
        System.out.println("welcome premain");
        inst.addTransformer(new mytran2(), true);
    }
    public static void agentmain(String args, Instrumentation inst) throws UnmodifiableClassException {
        System.out.println("welcome agentmain");
        inst.addTransformer(new mytran2(), true);
    }
}


