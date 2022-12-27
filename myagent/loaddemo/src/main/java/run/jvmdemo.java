package run;

import sun.jvmstat.monitor.*;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class jvmdemo {
    public static void main(String[] args) throws InterruptedException, URISyntaxException, MonitorException {
        /**
         * 建立连接
         */
        RuntimeMXBean jvm= ManagementFactory.getRuntimeMXBean();
        /**
         *1. 获取JVM启动名称
         */
        String jvm_name=jvm.getVmName();
        System.out.println(jvm_name);
        /**
         *2. 获取当前进程的pid
         */
        String jvm_pid=jvm.getName().split("@")[0];
        System.out.println(jvm_pid);

        /**
         * 建立一个本地连接
         */
        MonitoredHost local= MonitoredHost.getMonitoredHost("localhost");
        /**
         * 3.尝试获取全部进程的pid
         */
        Set<?> allpid_list=local.activeVms();
        System.out.println(allpid_list);
        /**
         * 4.尝试根据pid获取加载的所有类名,以及当前类名记载的pid
         */
        for (Object pid:allpid_list) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + pid)); //
            System.out.println(vm);
            String class_name = MonitoredVmUtil.mainClass(vm, true);   //mainclass提取当前pid的类名称
            System.out.println(class_name);
            Object pid_low=class_name.contains("run.jvmdemo");  //contains是判断当前字符串是否包含指定字符串
            if (pid_low.equals(true)){
                System.out.println(pid);  //最终pid
            }
        }

    }
}
