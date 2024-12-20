

        // Handling user choice
        switch (choice) {
            case 1:
                // Code for Who was the publisher?
                System.out.print("Enter the ID of the novel: ");
                int novelId = Integer.parseInt(System.console().readLine());
                try {
                    PreparedStatement pstmt = c.prepareStatement("SELECT Publisher.PublisherName FROM Publisher " +
                                                                 "INNER JOIN PublishedBy ON Publisher.PublisherID = PublishedBy.PublisherID " +
                                                                 "WHERE PublishedBy.NovelID = ?;");
                    pstmt.setInt(1, novelId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("Publisher: " + rs.getString("PublisherName"));
                    } else {
                        System.out.println("No publisher found for the provided novel ID.");
                    }
                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                // Code for Who was the scenario writer?
                System.out.print("Enter the ID of the novel: ");
                novelId = Integer.parseInt(System.console().readLine());
                try {
                    PreparedStatement pstmt = c.prepareStatement("SELECT Author.AuthorName FROM Author " +
                                                                 "INNER JOIN WrittenBy ON Author.AuthorID = WrittenBy.AuthorID " +
                                                                 "WHERE WrittenBy.NovelID = ?;");
                    pstmt.setInt(1, novelId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("Scenario Writer: " + rs.getString("AuthorName"));
                    } else {
                        System.out.println("No scenario writer found for the provided novel ID.");
                    }
                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                // Code for What is the name of the leading male voice actor of the visual novel?
                System.out.print("Enter the ID of the novel: ");
                novelId = Integer.parseInt(System.console().readLine());
                try {
                    PreparedStatement pstmt = c.prepareStatement("SELECT VoiceActor.VoiceName FROM VoiceActor " +
                                                                 "INNER JOIN VoicedBy ON VoiceActor.VoiceActorID = VoicedBy.VoiceActorID " +
                                                                 "WHERE VoicedBy.NovelID = ? AND VoiceActor.Gender = 'M';");
                    pstmt.setInt(1, novelId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("Leading Male Voice Actor: " + rs.getString("VoiceName"));
                    } else {
                        System.out.println("No male voice actor found for the provided novel ID.");
                    }
                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                // Code for What is the name of the leading female voice actress of the visual novel?
                System.out.print("Enter the ID of the novel: ");
                novelId = Integer.parseInt(System.console().readLine());
                try {
                    PreparedStatement pstmt = c.prepareStatement("SELECT VoiceActor.VoiceName FROM VoiceActor " +
                                                                 "INNER JOIN VoicedBy ON VoiceActor.VoiceActorID = VoicedBy.VoiceActorID " +
                                                                 "WHERE VoicedBy.NovelID = ? AND VoiceActor.Gender = 'F';");
                    pstmt.setInt(1, novelId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("Leading Female Voice Actress: " + rs.getString("VoiceName"));
                    } else {
                        System.out.println("No female voice actress found for the provided novel ID.");
                    }
                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                // Code for What is the given age rating for the reading?
                System.out.print("Enter the ID of the novel: ");
                novelId = Integer.parseInt(System.console().readLine());
                try {
                    PreparedStatement pstmt = c.prepareStatement("SELECT AgeRating FROM Novel WHERE NovelID = ?;");
                    pstmt.setInt(1, novelId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("Age Rating: " + rs.getString("AgeRating"));
                    } else {
                        System.out.println("No novel found for the provided ID.");
                    }
                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                // Code for What was the highest rating I gave to a visual novel?
                try {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT MAX(Rating) AS MaxRating FROM Novel;");
                    if (rs.next()) {
                        System.out.println("Highest Rating: " + rs.getFloat("MaxRating"));
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                // Code for What was the lowest rating I gave a visual novel?
                try {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT MIN(Rating) AS MinRating FROM Novel;");
                    if (rs.next()) {
System.out.println("Lowest Rating: " + rs.getFloat("MinRating"));
}
rs.close();
stmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
case 8:
// Code for What genres were most common?
try {
Statement stmt = c.createStatement();
ResultSet rs = stmt.executeQuery("SELECT Genre.GenreName, COUNT(*) AS Count FROM Genre " +
"INNER JOIN BelongsToGenre ON Genre.GenreID = BelongsToGenre.GenreID " +
"GROUP BY Genre.GenreName ORDER BY Count DESC LIMIT 3;");
System.out.println("Most Common Genres:");
while (rs.next()) {
System.out.println(rs.getString("GenreName") + ": " + rs.getInt("Count"));
}
rs.close();
stmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
case 9:
// Code for Which platforms is the visual novel available on?
System.out.print("Enter the ID of the novel: ");
novelId = Integer.parseInt(System.console().readLine());
try {
PreparedStatement pstmt = c.prepareStatement("SELECT Platform.PlatformName FROM Platform " +
"INNER JOIN AvailableOn ON Platform.PlatformID = AvailableOn.PlatformID " +
"WHERE AvailableOn.NovelID = ?;");
pstmt.setInt(1, novelId);
ResultSet rs = pstmt.executeQuery();
System.out.println("Available Platforms:");
while (rs.next()) {
System.out.println(rs.getString("PlatformName"));
}
rs.close();
pstmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
case 10:
// Code for When was the visual novel released?
System.out.print("Enter the ID of the novel: ");
novelId = Integer.parseInt(System.console().readLine());
try {
PreparedStatement pstmt = c.prepareStatement("SELECT ReleaseDate FROM Novel WHERE NovelID = ?;");
pstmt.setInt(1, novelId);
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
System.out.println("Release Date: " + rs.getString("ReleaseDate"));
} else {
System.out.println("No novel found for the provided ID.");
}
rs.close();
pstmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
case 11:
// Code for Have I read the novel, am I currently reading it, or is it on my wish list (or various such statuses)?
System.out.print("Enter the ID of the novel: ");
novelId = Integer.parseInt(System.console().readLine());
try {
PreparedStatement pstmt = c.prepareStatement("SELECT ReadingStatus FROM Novel WHERE NovelID = ?;");
pstmt.setInt(1, novelId);
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
System.out.println("Reading Status: " + rs.getString("ReadingStatus"));
} else {
System.out.println("No novel found for the provided ID.");
}
rs.close();
pstmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
case 12:
// Code for Who was the main music composer of the visual novel?
System.out.print("Enter the ID of the novel: ");
novelId = Integer.parseInt(System.console().readLine());
try {
PreparedStatement pstmt = c.prepareStatement("SELECT Composer.ComposerName FROM Composer " +
"INNER JOIN ComposedBy ON Composer.ComposerID = ComposedBy.ComposerID " +
"WHERE ComposedBy.NovelID = ?;");
pstmt.setInt(1, novelId);
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
System.out.println("Main Music Composer: " + rs.getString("ComposerName"));
} else {
System.out.println("No composer found for the provided novel ID.");
}
rs.close();
pstmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
case 13:
// Code for What is the title of the novel?
System.out.print("Enter the ID of the novel: ");
novelId = Integer.parseInt(System.console().readLine());
try {
PreparedStatement pstmt = c.prepareStatement("SELECT Title FROM Novel WHERE NovelID = ?;");
pstmt.setInt(1, novelId);
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
System.out.println("Title: " + rs.getString("Title"));
} else {
System.out.println("No novel found for the provided ID.");
}
rs.close();
pstmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
case 14:
// Code for How long does the visual novel take to complete, on average?
try {
Statement stmt = c.createStatement();
ResultSet rs = stmt.executeQuery("SELECT AVG(TimeToFinish) AS AvgTime FROM Novel;");
if (rs.next()) {
System.out.println("Average Time to Complete: " + rs.getFloat("AvgTime"));
}
rs.close();
stmt.close();
} catch (SQLException e) {
e.printStackTrace();
}
break;
default:
System.out.println("Invalid choice. Please enter a number between 1 and 14.");
}
    // Closing the database connection
    try {
        c.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }