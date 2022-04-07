
import javax.swing.JFrame;


public class TagExtractorRunner 
{
    public static void main(String[] args) 
    {
        TagExtractorFrame MyTagExtractorFrame = new TagExtractorFrame();

        MyTagExtractorFrame.SetTagExtractorFrameDisplay();

        MyTagExtractorFrame.setSize(900, 700);

        MyTagExtractorFrame.setResizable(false);

        MyTagExtractorFrame.setLocationRelativeTo(null);
        
        MyTagExtractorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyTagExtractorFrame.setVisible(true);
    }
}
