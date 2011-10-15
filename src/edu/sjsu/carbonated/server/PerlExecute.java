import java.io.*;

public class PerlExecute{

public static void runperl(String cmdstr) {

Process process1,process;

try
{
/*process1 = Runtime.getRuntime().exec("cd /Users/Admin/Desktop");*/
process = Runtime.getRuntime().exec(cmdstr);
process.waitFor();
if(process.exitValue() == 0)
{
System.out.println("Command Successful");
}
else
{
System.out.println("Command Failure");
}
}
catch(Exception e)
{
System.out.println("Exception: "+ e.toString());
}
}
}


