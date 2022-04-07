
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;

public class StopWordFilter implements Filter
{
    private static Set<String> stopWordSet;
    private static Path stopWordFilePath;
    
    public StopWordFilter()
    {
        stopWordSet = new TreeSet<>();
    }
    
    @Override
    public boolean reject(Object x) 
    {
        if(stopWordSet.contains(x.toString()))
            return true;
        
        return false;
    }
    
    public static void ReadAndCreateStopWordSet()
    {
        String stopWordLine = "";

        try
        {
            InputStream in = new BufferedInputStream(Files.newInputStream(stopWordFilePath, CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while(reader.ready())
            {
                stopWordLine = reader.readLine();

                stopWordSet.add(stopWordLine);
            }

            reader.close(); // must close the file to seal it and flush buffer
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "File not found!!!");
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "IO Exception occured reading Tag Extraction File");
        }
    }

    public static void setStopWordFilePath(Path stopFilePath) 
    {
        stopWordFilePath = stopFilePath;
    }
}
