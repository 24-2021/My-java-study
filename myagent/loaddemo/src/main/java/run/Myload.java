package run;

import com.sun.tools.attach.VirtualMachine;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Set;

public class Myload {
//    private static final String path="F:\\java-8\\agent_one\\agentdemo1\\myagent\\agentdemo\\target\\agentdemo-1.0-SNAPSHOT.jar";
    private static final String path="G:\\A孝道\\第一周、第二周\\agent完成的部分\\模块地址\\myagent\\agentdemo\\target\\agentdemo-1.0-SNAPSHOT.jar";
    public static void main(String[] args) throws Exception {

        String load_pid=getpidlow().toString();

        loadjar(load_pid,path);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        info.go();
    }


    /**
     * 获取当前Myload类的pid
     * @return
     */
    public  static String getpid(){
        RuntimeMXBean jvm= ManagementFactory.getRuntimeMXBean();
        String pid=jvm.getName().split("@")[0];
        return pid;
    }

    /**
     * 获取全部pid、并且获取pid对应的类名、获取当前pid
     * @return
     */
    public  static Object getpidlow() throws Exception {
        MonitoredHost local= MonitoredHost.getMonitoredHost("localhost");
        Set<?> allpid_list=local.activeVms();  //获取到全部pid,放到allpid_list列表
        /**
         * 尝试根据pid获取加载的所有类名,以及当前类名记载的pid
         */
        for (Object pid:allpid_list) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + pid)); //
            String class_name = MonitoredVmUtil.mainClass(vm, true);   //mainclass提取当前pid的类名称
            Object pid_low=class_name.contains("run.Myload");  //contains是判断当前字符串是否包含指定字符串
            if (pid_low.equals(true)){
                return pid;
            }
        }
        return null;
    }
    public static void loadjar(String pid,String path){
        String tmp;  //用来装
        if (pid==null||pid.equals("")){
            tmp=getpid();
        }else{
            tmp=pid;
        }
        try {
            VirtualMachine virtualMachine = VirtualMachine.attach(tmp);
            virtualMachine.loadAgent(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
