package plexrenamerfx;

import java.io.File;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author javif89
 */
public class FileRenamer {
    public void renameInDirectory(File directory, String season, String series, TextArea log, ProgressBar pbar)
    {
        pbar.setProgress(0);
        boolean allFilesRenamed = true;
        File[] files = directory.listFiles();
        int count = 0;
        RenameUtils ru = new RenameUtils();
        for(File f : files)
        {
            String episode = ru.extractEpisode(f.getName());
            if (!episode.equals("")) {
                String extension = FilenameUtils.getExtension(f.getPath());
                String newName = series+" - "+"s"+season+"e"+episode+"."+extension;
                String original = f.getName();
                log.appendText(original+" renamed to: "+newName+"\n");
                f.renameTo(new File(f.getParent()+"\\"+newName));
            }
            else
            {
                allFilesRenamed = false;
            }
            count = count + 1;
            pbar.setProgress((double)(count/files.length));
        }
        
        if(!allFilesRenamed)
        {
            JOptionPane.showMessageDialog(null,"Sorry, some files were not renamed because we could not extract the episode from the file name", "Not all files renamed",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void renameSeries(File directory, String series, TextArea log, ProgressBar pbar)
    {
        boolean allSeasonsRenamed = true;
        File[] seasons = directory.listFiles();
        RenameUtils ru = new RenameUtils();
        for(File dir : seasons)
        {
            log.appendText("RENAMING: "+dir.getName()+"\n");
            String season = ru.extractSeason(dir.getName());
            if(!season.equals(""))
            {
                String folderName = "Season "+Integer.toString(Integer.parseInt(season));
                renameInDirectory(dir,season,series,log,pbar);
                dir.renameTo(new File(dir.getParent()+"\\"+folderName));
            }
            else
            {
                allSeasonsRenamed = false;
            }
        }
        
        if(!allSeasonsRenamed)
        {
            JOptionPane.showMessageDialog(null,"Sorry, some seasons were not renamed because we could not extract the season from the folder name", "Not all files renamed",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
