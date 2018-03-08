package com.zhou.test.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author eli
 * @date 2017/11/20 16:04
 */
class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
