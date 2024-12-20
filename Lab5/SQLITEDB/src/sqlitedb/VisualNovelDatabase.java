import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.DataInputStream;

/**
 *
 * @author sqlitetutorial.net
 */
public class VisualNovelDatabase {

    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/vndb.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void getTheBooksByPublisher(String publishername) { //case 1
        String sql = "SELECT substr(novel.title||'          ',1,10) title, IFNULL(novel.rating,0.0) rating, novel.agerating agerating, novel.releasedate releasedate FROM publisher " +  
                     "INNER JOIN PublishedBy ON Publisher.PublisherID = PublishedBy.PublisherID " +
                     "inner join novel on PublishedBy.novelid = novel.novelid " +
                     "where publisher.publishername like ? COLLATE NOCASE";
           
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            
            // set the value
            pstmt.setString(1,publishername);
            //
            ResultSet rs  = pstmt.executeQuery();
            
            System.out.println("Title          " + "\t\t\t" + "Rating" + "\t\t\t" + "Agerating" + "\t\t" + "Releasedate" );
            System.out.println("------------------------------------------------------------------------------------------" );
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("title") + "\t\t\t" +
                                   rs.getString("rating") + "\t\t\t" +
                                   rs.getString("agerating") + "\t\t\t" +
                                   rs.getString("releasedate") );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
  
   

    public void getTheBooksByAuthor(String authorname) { //case 2
        String sql = "SELECT substr(novel.title||'          ',1,10) title, IFNULL(novel.rating,0.0) rating, novel.agerating agerating, novel.releasedate releasedate "
                   + "FROM author " +  
                     "INNER JOIN WrittenBy ON author.AuthorId = writtenby.AuthorId " +
                     "inner join novel on WrittenBy.novelid = novel.novelid " +
                     "where author.authorname like ? COLLATE NOCASE";
           
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
            
            // set the value
            pstmt.setString(1,authorname);
            //
            ResultSet rs  = pstmt.executeQuery();
            
            System.out.println("Title          " + "\t\t\t" + "Rating" + "\t\t\t" + "Agerating" + "\t\t" + "Releasedate" );
            System.out.println("------------------------------------------------------------------------------------------" );
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("title") + "\t\t\t" +
                                   rs.getString("rating") + "\t\t\t" +
                                   rs.getString("agerating") + "\t\t\t" +
                                   rs.getString("releasedate") );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void getLeadingMaleVoiceActor(String novelTitle) { //case 3
    String sql = "SELECT VoiceActor.VoiceName " +
                 "FROM VoiceActor " +
                 "INNER JOIN VoicedBy ON VoiceActor.VoiceActorID = VoicedBy.VoiceActorID " +
                 "INNER JOIN Novel ON VoicedBy.NovelID = Novel.NovelID " +
                 "WHERE Novel.Title = ? AND VoiceActor.Gender = 'M' " +
                 "LIMIT 1";

    try (Connection conn = this.connect();
         PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        
        // set the value
        pstmt.setString(1, novelTitle);
        
        ResultSet rs  = pstmt.executeQuery();
        
        // Check if there is a result
        if (rs.next()) {
            System.out.println("The leading male voice actor of the visual novel \"" + novelTitle + "\" is: " +
                               rs.getString("VoiceName"));
        } else {
            System.out.println("No information found for the leading male voice actor of the visual novel \"" + novelTitle + "\".");
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void getLeadingFemaleVoiceActress(String novelTitle) { //case 4
    String sql = "SELECT VoiceActor.VoiceName " +
                 "FROM VoiceActor " +
                 "INNER JOIN VoicedBy ON VoiceActor.VoiceActorID = VoicedBy.VoiceActorID " +
                 "INNER JOIN Novel ON VoicedBy.NovelID = Novel.NovelID " +
                 "WHERE Novel.Title = ? AND VoiceActor.Gender = 'F' " +
                 "LIMIT 1";

    try (Connection conn = this.connect();
         PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        
        // set the value
        pstmt.setString(1, novelTitle);
        
        ResultSet rs  = pstmt.executeQuery();
        
        // Check if there is a result
        if (rs.next()) {
            System.out.println("The leading female voice actress of the visual novel \"" + novelTitle + "\" is: " +
                               rs.getString("VoiceName"));
        } else {
            System.out.println("No information found for the leading female voice actress of the visual novel \"" + novelTitle + "\".");
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void getAgeRatingForNovel(String novelTitle) { //case 5
    String sql = "SELECT AgeRating " +
                 "FROM Novel " +
                 "WHERE Title = ?";

    try (Connection conn = this.connect();
         PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        
        // set the value
        pstmt.setString(1, novelTitle);
        
        ResultSet rs  = pstmt.executeQuery();
        
        // Check if there is a result
        if (rs.next()) {
            System.out.println("The age rating for the reading of the visual novel \"" + novelTitle + "\" is: " +
                               rs.getString("AgeRating"));
        } else {
            System.out.println("No information found for the age rating of the visual novel \"" + novelTitle + "\".");
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void getLowestRatingNovel() {
    String sql = "SELECT Novel.Title, MIN(Novel.Rating) AS LowestRating " +
                 "FROM Novel " +
                 "WHERE Novel.Rating IS NOT NULL";

    try (Connection conn = this.connect();
         Statement stmt  = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        // Check if there is a result
        if (rs.next()) {
            String title = rs.getString("Title");
            double lowestRating = rs.getDouble("LowestRating");
            System.out.println("The visual novel with the lowest rating is: " + title + " with a rating of " + lowestRating);
        } else {
            System.out.println("No information found for the visual novel with the lowest rating.");
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}


public void getMostCommonGenres() { //case  7
    String sql = "SELECT Genre.GenreName, COUNT(*) AS NovelCount, GROUP_CONCAT(Novel.Title, ', ') AS Titles " +
                 "FROM Genre " +
                 "INNER JOIN BelongsToGenre ON Genre.GenreID = BelongsToGenre.GenreID " +
                 "INNER JOIN Novel ON BelongsToGenre.NovelID = Novel.NovelID " +
                 "GROUP BY Genre.GenreName " +
                 "ORDER BY NovelCount DESC";

    try (Connection conn = this.connect();
         Statement stmt  = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        System.out.println("Most common genres:");
        System.out.println("Genre Name   \tNovel Count\t\t\tTitles");

        // Loop through the result set
        while (rs.next()) {
            String genreName = rs.getString("GenreName");
            int novelCount = rs.getInt("NovelCount");
            String titles = rs.getString("Titles");
            System.out.println(genreName + "\t\t" + novelCount + "\t\t\t\t" + titles);
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}


public void getReleaseDate(String novelTitle) { //case 8
    String sql = "SELECT ReleaseDate " +
                 "FROM Novel " +
                 "WHERE Title LIKE ? COLLATE NOCASE";

    try (Connection conn = this.connect();
         PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        
        // Set the value for the prepared statement
        pstmt.setString(1, novelTitle);

        ResultSet rs  = pstmt.executeQuery();
        
        // Check if there is a result
        if (rs.next()) {
            String releaseDate = rs.getString("ReleaseDate");
            System.out.println("The visual novel '" + novelTitle + "' was released on: " + releaseDate);
        } else {
            System.out.println("No information found for the visual novel '" + novelTitle + "'.");
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void getReadingStatus(String novelTitle) { //case 9
    String sql = "SELECT ReadingStatus " +
                 "FROM Novel " +
                 "WHERE Title LIKE ? COLLATE NOCASE";

    try (Connection conn = this.connect();
         PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        
        // Set the value for the prepared statement
        pstmt.setString(1, novelTitle);

        ResultSet rs  = pstmt.executeQuery();
        
        // Check if there is a result
        if (rs.next()) {
            String readingStatus = rs.getString("ReadingStatus");
            System.out.println("The reading status of the novel '" + novelTitle + "' is: " + readingStatus);
        } else {
            System.out.println("No information found for the novel '" + novelTitle + "'.");
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void getMainComposer(String novelTitle) { //case 10
    String sql = "SELECT Composer.ComposerName " +
                 "FROM Composer " +
                 "INNER JOIN ComposedBy ON Composer.ComposerID = ComposedBy.ComposerID " +
                 "INNER JOIN Novel ON ComposedBy.NovelID = Novel.NovelID " +
                 "WHERE Novel.Title LIKE ? COLLATE NOCASE";

    try (Connection conn = this.connect();
         PreparedStatement pstmt  = conn.prepareStatement(sql)) {
        
        // Set the value for the prepared statement
        pstmt.setString(1, novelTitle);

        ResultSet rs  = pstmt.executeQuery();
        
        // Check if there is a result
        if (rs.next()) {
            String mainComposer = rs.getString("ComposerName");
            System.out.println("The main music composer of the novel '" + novelTitle + "' is: " + mainComposer);
        } else {
            System.out.println("No information found for the novel '" + novelTitle + "'.");
        }

        rs.close();
        pstmt.close();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void getAverageCompletionTime() { //case 11
    String sql = "SELECT AVG(TimeToFinish) AS AvgCompletionTime " +
                 "FROM Novel " +
                 "WHERE ReadingStatus = 'Completed'";

    try (Connection conn = this.connect();
         Statement stmt  = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        // Check if there is a result
        if (rs.next()) {
            double avgCompletionTime = rs.getDouble("AvgCompletionTime");
            System.out.println("The average completion time of visual novels is: " + avgCompletionTime + " hours");
        } else {
            System.out.println("No information found for the average completion time of visual novels.");
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    
    public static void main(String[] args) {
        System.out.println("***************************************************************************************************************");
        System.out.println("* Welcome to the visual novel database application                                                            *");
        System.out.println("*                                                                                                             *");
        System.out.println("* 01. Books by publisher                                                                                      *");
        System.out.println("* 02. Books by writer (first and last name)                                                                   *");
        System.out.println("* 03. What is the name of the leading male voice actor of the visual novel?                                   *");
        System.out.println("* 04. What is the name of the leading female voice actress of the visual novel?                               *");
        System.out.println("* 05. What is the given age rating for the reading?                                                           *");
        System.out.println("* 06. What was the lowest rating I gave a visual novel?                                                       *");
        System.out.println("* 07. What genres were most common?                                                                           *");
        System.out.println("* 08. When was the visual novel released?                                                                     *");
        System.out.println("* 09. Have I read the novel, am I currently reading it, or is it on my wish list (or various such statuses)?  *");
        System.out.println("* 10. Who was the main music composer of the visual novel?                                                    *");
        System.out.println("* 11. How long does the visual novel take to complete, on average?                                            *");
        System.out.println("***************************************************************************************************************");
        
        Scanner choice = new Scanner(System.in); 
        System.out.print("Enter your choice: ");
        int n = choice.nextInt();

        switch (n) {
        case 1:
        VisualNovelDatabase app1 = new VisualNovelDatabase();

        InputStreamReader inStream = new InputStreamReader(System.in);
        BufferedReader stdind = new BufferedReader(inStream);

        String str[] = new String[1];

        try {
            System.out.print("Enter publisher: ");
            str[0] = stdind.readLine();
            //System.out.println("Enter it:");
            //str[1] = stdind.readLine();    
            } catch (IOException ioe) { ioe.printStackTrace(); 
                }
        //choice.close();
        System.out.println("***************************************************************************************************************");
        System.out.println();
        app1.getTheBooksByPublisher("%"+str[0]+"%");
        System.out.println();
        System.out.println();
        //app1.getThePublisher("Nemlei");
        break;
        
        case 2:
        VisualNovelDatabase app2 = new VisualNovelDatabase();

        InputStreamReader inStream2 = new InputStreamReader(System.in);
        BufferedReader stdind2 = new BufferedReader(inStream2);

        String str2[] = new String[1];

        try {
            System.out.print("Enter author: ");
            str2[0] = stdind2.readLine();
            //System.out.println("Enter it:");
            //str[1] = stdind.readLine();    
            } catch (IOException ioe) { ioe.printStackTrace(); 
                }
        
        System.out.println("***************************************************************************************************************");
        System.out.println();
        app2.getTheBooksByAuthor("%"+str2[0]+"%");
        System.out.println();
        System.out.println();
        break;
        
        case 3:
        VisualNovelDatabase app3 = new VisualNovelDatabase();

        InputStreamReader inStream3 = new InputStreamReader(System.in);
        BufferedReader stdind3 = new BufferedReader(inStream3);

        String novelTitle = "";

        try {
            System.out.print("Enter the title of the visual novel: ");
            novelTitle = stdind3.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println("***************************************************************************************************************");
        System.out.println();
        app3.getLeadingMaleVoiceActor(novelTitle);
        System.out.println();
        System.out.println();
        break;
        
        case 4:
        VisualNovelDatabase app4 = new VisualNovelDatabase();

        InputStreamReader inStream4 = new InputStreamReader(System.in);
        BufferedReader stdind4 = new BufferedReader(inStream4);

        String novelTitle4 = "";

        try {
            System.out.print("Enter the title of the visual novel: ");
            novelTitle4 = stdind4.readLine();
        } catch (IOException ioe) {
        ioe.printStackTrace();
        }

        System.out.println("***************************************************************************************************************");
        System.out.println();
        app4.getLeadingFemaleVoiceActress(novelTitle4);
        System.out.println();
        System.out.println();
        break;

        case 5:
        VisualNovelDatabase app5 = new VisualNovelDatabase();

        InputStreamReader inStream5 = new InputStreamReader(System.in);
        BufferedReader stdind5 = new BufferedReader(inStream5);

        String novelTitle5 = "";

        try {
            System.out.print("Enter the title of the visual novel: ");
            novelTitle5 = stdind5.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println("***************************************************************************************************************");
        System.out.println();
        app5.getAgeRatingForNovel(novelTitle5);
        System.out.println();
        System.out.println();
        break;

        case 6:
        VisualNovelDatabase app6 = new VisualNovelDatabase();

        System.out.println("***************************************************************************************************************");
        System.out.println();
        app6.getLowestRatingNovel();
        System.out.println();
        System.out.println();
        break;


        case 7:
        VisualNovelDatabase app7 = new VisualNovelDatabase();

        System.out.println("***************************************************************************************************************");
        System.out.println();
        app7.getMostCommonGenres();
        System.out.println();
        System.out.println();
        break;

        case 8:
        VisualNovelDatabase app8 = new VisualNovelDatabase();

        InputStreamReader inStream8 = new InputStreamReader(System.in);
        BufferedReader stdind8 = new BufferedReader(inStream8);

        try {
            System.out.print("Enter the title of the visual novel: ");
            String inputTitle = stdind8.readLine();

            System.out.println("***************************************************************************************************************");
            System.out.println();
            app8.getReleaseDate(inputTitle);
            System.out.println();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case 9:
        VisualNovelDatabase app9 = new VisualNovelDatabase();

        InputStreamReader inStream9 = new InputStreamReader(System.in);
        BufferedReader stdind9 = new BufferedReader(inStream9);

        try {
            System.out.print("Enter the title of the novel: ");
            String inputTitle = stdind9.readLine();

            System.out.println("***************************************************************************************************************");
            System.out.println();
            app9.getReadingStatus(inputTitle);
            System.out.println();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case 10:
        VisualNovelDatabase app10 = new VisualNovelDatabase();

        InputStreamReader inStream10 = new InputStreamReader(System.in);
        BufferedReader stdind10 = new BufferedReader(inStream10);

        try {
            System.out.print("Enter the title of the novel: ");
            String inputTitle = stdind10.readLine();

            System.out.println("***************************************************************************************************************");
            System.out.println();
            app10.getMainComposer(inputTitle);
            System.out.println();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        break;

        case 11:
        VisualNovelDatabase app11 = new VisualNovelDatabase();

        System.out.println("***************************************************************************************************************");
        System.out.println();
        app11.getAverageCompletionTime();
        System.out.println();
        System.out.println();
        break;
        
        default:
        System.out.println("Invalid choice. Please enter a number between 1 and 12.");
         break;
        
        }  // end switch statement
        choice.close();
  }

}
