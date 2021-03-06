package com.zhou.test.other;

/**
 * @author eli
 * @date 2017/11/20 16:05
 */
public class GoodWindowsExec
{
    public static void main(String args[])
    {
        //args = new String[]{"type  E:\\Test\\test.txt"};
        if (args.length < 1)
        {
            System.out.println("USAGE: java GoodWindowsExec <cmd></cmd>");
            System.exit(1);
        }
        try
        {
            String osName = System.getProperty("os.name" );
            System.out.println("osName = " + osName);
            String[] cmd = new String[3];
            if( osName.equals( "Windows 95" ) )
            {
                cmd[0] = "command.com" ;
                cmd[1] = "/C" ;
                cmd[2] = args[0];
            }else{
                cmd[0] = "cmd.exe" ;
                cmd[1] = "/C" ;
                cmd[2] = args[0];
            }
            Runtime rt = Runtime.getRuntime();
            System.out.println("Execing " + cmd[0] + " " + cmd[1]
                    + " " + cmd[2]);
            Process proc = rt.exec(cmd);
// any error message?
            StreamGobbler errorGobbler = new
                    StreamGobbler(proc.getErrorStream(), "ERROR");
// any output?
            StreamGobbler outputGobbler = new
                    StreamGobbler(proc.getInputStream(), "OUTPUT");
// kick them off
            errorGobbler.start();
            outputGobbler.start();
// any error???
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);
        } catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
