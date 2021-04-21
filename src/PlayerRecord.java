import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/** Permanent data handler
 *
 */
public class PlayerRecord {
    private final String DEFAULT_NAME = "Unnamed Hero";
    private String name = DEFAULT_NAME;
    private int winBalance = -10;
    private int winStreak = 0;

    /** Loads save data file.  If not found, creates it.
     * Checks data per line while loading.  If a line doesn't match data type, uses default value.
     */
    public PlayerRecord() throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList();

        try {
            Scanner reader = new Scanner(new File(FilePaths.SAVE_DATA));
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
            reader.close();
        } catch (IOException e){}

        for (String line : lines) {
            line.trim();
        }

        try {
            name = lines.get(0);
        } catch (Exception e){}

        try {
            winBalance = Integer.valueOf(lines.get(1));
        } catch (Exception e){}

        try {
            winStreak = Integer.valueOf(lines.get(2));
        } catch (Exception e){}
    }

    /** Overwrites current save file with current data.
     *
     */
    public void save() throws IOException{
        File saveFile = new File(FilePaths.SAVE_DATA);
        saveFile.delete();

        PrintWriter writer = new PrintWriter(saveFile);

        writer.println(name);
        writer.println(winBalance);
        writer.println(winStreak);

        writer.close();
    }

    /** Counts a win to the player's record
     *
     */
    public void addWin(){
        winBalance++;
        winStreak++;
    }

    /** Counts a loss to the player's record
     *
     * @param playerDeath if the player died this run
     */
    public void addLoss(boolean playerDeath){
        winBalance--;
        winStreak = 0;

        if(playerDeath){
            name = DEFAULT_NAME;
        }
    }

    /** Checks if the current name is the same as the default name
     *
     * @return name.equals(DEFAULT_NAME);
     */
    public boolean isUnnamed(){
        return name.equals(DEFAULT_NAME);
    }

    /** pojo setName
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** pojo getName
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /** pojo getWinBalance
     *
     * @return winBalance
     */
    public int getWinBalance() {
        return winBalance;
    }

    /** pojo getWinStreak
     *
     * @return winStreak
     */
    public int getWinStreak() {
        return winStreak;
    }
}
