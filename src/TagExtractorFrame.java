
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TagExtractorFrame extends JFrame 
{
    private JPanel mainPanel;
    private JPanel fileSelectionPanel;
    private JPanel tagsDisplayPanel;
    private JPanel controlPanel;
    
    private JTextField tagsExtractionFilePath;
    private JTextField stopWordFilePath;
    
    private JButton tagsFileSelectionButton;
    private JButton stopWordFileSelectionButton;
    
    private JTextArea tagsDisplayArea;
    private JScrollPane tagsDisplayScrollPane;
    
    private JButton tagsExtractionStartButton;
    private JButton saveTagsFrequencyButton;
    private JButton quitButton;
    
    private Path tagsFilesPath;
    private Path stopFilePath;
    
    private ArrayList<String> wordsInTagFile;
    private Map<String, Integer> tagFrequencyMap;
    
    public TagExtractorFrame()
    {
        setTitle("Tag/Keyword Extractor");
        wordsInTagFile = new ArrayList<>();
        tagFrequencyMap = new TreeMap<String, Integer>();
    }
    
    public void SetTagExtractorFrameDisplay()
    {
        mainPanel = new JPanel();

        createFileSelectionPanel();
        createTagsDisplayPanel();
        createControlPanel();

        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(fileSelectionPanel, BorderLayout.NORTH);
        mainPanel.add(tagsDisplayPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
    
    private void createFileSelectionPanel()
    {
        fileSelectionPanel = new JPanel();
        
        fileSelectionPanel.setLayout(new BorderLayout(5, 5));
        
        JPanel tagFileSelectPanel = new JPanel();

        Font tagFilePanelFont  = new Font(Font.SANS_SERIF, Font.BOLD, 14);
               
        GridBagLayout tagFileGridBagLayout = new GridBagLayout();

        tagFileSelectPanel.setLayout(tagFileGridBagLayout);
                
        GridBagConstraints bagConstraints1 = new GridBagConstraints();
                
        tagsExtractionFilePath = new JTextField(46);
//        tagsExtractionFilePath.setEditable(false);
        
        bagConstraints1.fill = GridBagConstraints.HORIZONTAL;
                
        bagConstraints1.gridwidth = 3;
        bagConstraints1.gridx = 0;
        bagConstraints1.gridy = 1;
    
        tagFileSelectPanel.add(tagsExtractionFilePath, bagConstraints1);
                
        tagsFileSelectionButton = new JButton("Select Tag Extraction File");
        
        bagConstraints1.gridx = 3;
        bagConstraints1.gridy = 1;

        tagFileSelectPanel.add(tagsFileSelectionButton, bagConstraints1);
        
        tagsExtractionFilePath.setFont(tagFilePanelFont);
        tagsFileSelectionButton.setFont(tagFilePanelFont);
        
        tagsFileSelectionButton.addActionListener(ae -> tagsFileSelectionButtonClicked());
                
        tagFileSelectPanel.add(tagsExtractionFilePath);
        tagFileSelectPanel.add(tagsFileSelectionButton);
        
        JPanel stopFileSelectPanel = new JPanel();
        
        GridBagLayout stopFileGridBagLayout = new GridBagLayout();

        GridBagConstraints bagConstraints2 = new GridBagConstraints();
        
        stopFileSelectPanel.setLayout(stopFileGridBagLayout);
        
        Font stopFilePanelFont  = new Font(Font.SANS_SERIF, Font.BOLD, 14);
                
        stopWordFilePath = new JTextField(48);
        //stopWordFilePath.setEditable(false);
        
        bagConstraints2.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints2.gridwidth = 3;
        bagConstraints2.gridx = 0;
        bagConstraints2.gridy = 0;
    
        stopFileSelectPanel.add(stopWordFilePath, bagConstraints2);
                
        stopWordFileSelectionButton = new JButton("Select Stop Word File");

        stopWordFileSelectionButton.addActionListener(ae -> stopFileSelectionButtonClicked());
                
        bagConstraints2.gridx = 3;
        bagConstraints2.gridy = 0;

        stopFileSelectPanel.add(stopWordFileSelectionButton, bagConstraints2);
        
        stopWordFilePath.setFont(tagFilePanelFont);
        stopWordFileSelectionButton.setFont(tagFilePanelFont);
        
        fileSelectionPanel.add(tagFileSelectPanel, BorderLayout.NORTH);
        fileSelectionPanel.add(stopFileSelectPanel, BorderLayout.SOUTH);
    }
    
    private void createTagsDisplayPanel()
    {
        tagsDisplayPanel = new JPanel();

        Font tagsDisplayAreaFont  = new Font(Font.SERIF,  Font.BOLD, 18);
        tagsDisplayArea = new JTextArea(26, 58);
        tagsDisplayArea.setFont(tagsDisplayAreaFont);
        tagsDisplayArea.setEditable(false);
        tagsDisplayScrollPane = new JScrollPane(tagsDisplayArea);
        tagsDisplayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tagsDisplayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        tagsDisplayPanel.add(tagsDisplayScrollPane);
    }
    
    private void createControlPanel()
    {
        controlPanel = new JPanel();
        
        GridLayout controlGridLayout = new GridLayout(1, 3);
        controlGridLayout.setHgap(10);
        controlGridLayout.setVgap(10);

        controlPanel.setLayout(controlGridLayout);
        
        Font controlPanelFont  = new Font(Font.DIALOG,  Font.BOLD, 22);
        
        tagsExtractionStartButton = new JButton("Start Tag Extraction");
        saveTagsFrequencyButton = new JButton("Save Tags to File");
        quitButton = new JButton("Quit");
        
        tagsExtractionStartButton.setFont(controlPanelFont);
        saveTagsFrequencyButton.setFont(controlPanelFont);
        quitButton.setFont(controlPanelFont);
        
        tagsExtractionStartButton.addActionListener(ae -> tagsExtractionStartButtonClicked());
        saveTagsFrequencyButton.addActionListener(ae -> saveTagsFrequencyButtonClicked());
        quitButton.addActionListener(ae -> {
            System.exit(0);
        });
                
        controlPanel.add(tagsExtractionStartButton);
        controlPanel.add(saveTagsFrequencyButton);
        controlPanel.add(quitButton);
    }
    
    private void SelectTagExtractionFile()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Tag Extraction Files", "txt"));
            chooser.setCurrentDirectory(workingDirectory);
            chooser.setAcceptAllFileFilterUsed(true);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                tagsFilesPath = selectedFile.toPath();
                
                tagsExtractionFilePath.setText(selectedFile.getAbsolutePath());
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Something went wrong reading Tag Extractaction File");
        }
    }
    
    private void tagsFileSelectionButtonClicked()
    {
        SelectTagExtractionFile();
    }
    
    private void ReadTagsExtractionFile()
    {
        String tagsFileLine = "";
        wordsInTagFile.clear();
        
        try
        {
            InputStream in = new BufferedInputStream(Files.newInputStream(tagsFilesPath, CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while(reader.ready())
            {
                tagsFileLine = reader.readLine();

                String [] tagWords = tagsFileLine.split("\\W+");

                for(String tagWord : tagWords)
                {
                    if(!tagWord.isEmpty()) 
                        tagWord = tagWord.replaceAll("[^A-Za-z_]", "");
                    
                    if(tagWord.length() > 1) wordsInTagFile.add(tagWord.toLowerCase());
                }
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

    private void SelectStopWordFile()
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Stop Word Files", "txt"));
            chooser.setCurrentDirectory(workingDirectory);
            chooser.setAcceptAllFileFilterUsed(true);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                stopFilePath = selectedFile.toPath();
                
                stopWordFilePath.setText(selectedFile.getAbsolutePath());
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Something went wrong reading Stop Word File");
        }
    }
     
    private void stopFileSelectionButtonClicked() 
    {
        SelectStopWordFile();
    }

    private void tagsExtractionStartButtonClicked() 
    {
        if(tagsExtractionFilePath.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please Select File for Tag Extraction");
        }
        else
        {
            if(stopWordFilePath.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Please Select Stop Word File");
            }
            else
            {
                ReadTagsExtractionFile();
                
                Filter myStopWordFilter = new StopWordFilter();
                
                StopWordFilter.setStopWordFilePath(stopFilePath);
                StopWordFilter.ReadAndCreateStopWordSet();
                
                for(String wordTag : wordsInTagFile)
                {
                    boolean isTagStopWord = myStopWordFilter.reject(wordTag);
                    
                    if(!isTagStopWord)
                    {
                        if(tagFrequencyMap.containsKey(wordTag))
                        {
                            int frequency = tagFrequencyMap.get(wordTag).intValue();
                            tagFrequencyMap.put(wordTag, (frequency + 1));
                        }
                        else
                        {
                            tagFrequencyMap.put(wordTag, 1);
                        }
                    }
                }
                
                for(Map.Entry<String, Integer> entry : tagFrequencyMap.entrySet())
                {    
                    String tagKeyword = entry.getKey();  
                    Integer tagFrequency = entry.getValue();  
                    
                    tagsDisplayArea.append("<" + tagKeyword + "><" + tagFrequency + ">\n");
                }  
            }
        }
    }

    private void SaveTagKeysWithFrequeciesToFile()
    {
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "/src/TagFrequeciesFile.txt");

        try
        {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            String lnSeperator = System.getProperty("line.separator");
            String tagsAndFrequencyText = tagsDisplayArea.getText() ;
            String tagsfrequencyText = tagsAndFrequencyText.replaceAll("\n", lnSeperator);
            writer.write(tagsfrequencyText.toString(), 0, tagsfrequencyText.length());
            writer.close();
            
            JOptionPane.showMessageDialog(null, "Tag Frequency File generated Successfully");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Something went wrong writing Tag Frequency File");
        }
    }
    
    private void saveTagsFrequencyButtonClicked() 
    {
        SaveTagKeysWithFrequeciesToFile();
    }
}
