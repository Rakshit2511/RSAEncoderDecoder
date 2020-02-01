import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
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
        File file = new File("java.jpg");
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
		
        RSA rsa = new RSA();
        byte[] encrypted = rsa.encrypt(s.getBytes());
        byte[] decrypted = rsa.decrypt(encrypted);
		String s1 = new String(decrypted);
		if(!s1.equals(s))
			System.out.println(s.charAt(s.length()-1)+"  "+s1.charAt(s1.length()-1));
        /*byte[] encrypted = rsa.encrypt(bytes);
        byte[] decrypted = rsa.decrypt(encrypted);
		File someFile = new File("java2.jpg");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(decrypted);
        fos.flush();
        fos.close();*/
		
        System.out.println("DONE");
    }
}
class RSA
{
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int bitlength = 1024;
    private Random r;
 
    public RSA()
    {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
    }
 
    public RSA(BigInteger e, BigInteger d, BigInteger N)
    {
        this.e = e;
        this.d = d;
        this.N = N;
    }
 
    private static String bytesToString(byte[] encrypted)
    {
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
    }
    public byte[] encrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }
    public byte[] decrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
}