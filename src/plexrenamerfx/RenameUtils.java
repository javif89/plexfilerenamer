package plexrenamerfx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author javif89
 */
public class RenameUtils {
    public String extractSeason(String arg)
    {
        String season = "";
        Pattern p = Pattern.compile(" [0-9]{1,3} ?");
        Matcher m = p.matcher(arg);
        if(m.find())
        {
            season = zeroPad(m.group().trim());
        }
        return season;
    }
    
    public String extractEpisode(String arg)
    {
        String episode = "";
        Pattern p = Pattern.compile(" [0-9]{1,3} ?");
        Matcher m = p.matcher(arg.replaceAll("_", " "));
        if(m.find())
        {
            episode = zeroPad(m.group().trim());
        }
        return episode;
    }
    
    public String zeroPad(String num)
    {
        String result = "";
        int numint = Integer.parseInt(num);
        if (numint < 10) {
            result = "0"+numint;
        }
        else
        {
            result = Integer.toString(Integer.parseInt(num));
        }
        return result;
    }
}
