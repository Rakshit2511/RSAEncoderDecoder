package hackathon;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
public class genFile {
 
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        File file = new File("java.pdf");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try
        {
            for (int num; (num = fis.read(buf)) != -1;)
            {
                bos.write(buf, 0, num);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(genFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bytes = bos.toByteArray();


        String s = new String(bytes);
        try
        {
            BufferedWriter out=new BufferedWriter(new FileWriter("bytedata.txt"));
            out.write(s);
            out.flush();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        System.out.println("DONE");
    }
}