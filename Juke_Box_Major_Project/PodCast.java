import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.Scanner;

public class PodCast {
    static void display(int user_id) {
        Scanner s=new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from podcast_Details");
            while (rs.next())
            {
                System.out.println(rs.getInt(1) +" "+ rs.getString(2) +" "+ rs.getInt(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getInt(7) + " " + rs.getInt(8));
            }
            System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                    "1.play the Podcast" +
                    "\n 2.add to playlist ");
            int ans = s.nextInt();

            if (ans == 1) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter the postcast id to play");
                int podcastId = sc.nextInt();
                playPodcast(podcastId);
            } else if (ans == 2) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter the postcast id to play");
                int podcastId = sc.nextInt();
                playlist(user_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void playPodcast(int podcastId) {

        String path = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select location from podcast_Details where podcast_id="+podcastId);
            while (rs.next())
            {
                path = rs.getString(1);
            }
            SongPlayer.songPlayer(path);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("press \n 1.Next Podcast" +
                "\n 2.Previous podcast");
        Scanner s=new Scanner(System.in);
        int ans=s.nextInt();
        if(ans==1)
        {
            playNextPodcast(podcastId);
        }
        else if(ans==2)
        {

        }
    }

    static void podcastPlayList(int playlist_id,int user_id,int podcast_id) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Enter the postcast id that you wanted to Add to playlist");
        //int podcastId=sc.nextInt();
        String name = null, location = null, duration = null, language = null;
        int episode_id = 0, artist_id = 0, genre_id = 0,j=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rstt=st.executeQuery("select * from podcast_playlist where podcast_id="+podcast_id+" and playlist_id="+playlist_id);
            while(rstt.next())
            {
                j++;
            }
            if(j==0) {
                ResultSet rs = st.executeQuery("select * from podcast_Details where podcast_id=" + podcast_id);
                while (rs.next()) {
                    podcast_id = rs.getInt(1);
                    name = rs.getString(2);
                    episode_id = rs.getInt(3);
                    location = rs.getString(4);
                    duration = rs.getString(5);
                    language = rs.getString(6);
                    artist_id = rs.getInt(7);
                    genre_id = rs.getInt(8);
                }
                PreparedStatement pst = con.prepareStatement("insert into podcast_playlist(podcast_id,podcast_name,episode_id,location,duration,language,artist_id,genre_id,user_id,playlist_id) values(?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, podcast_id);
                pst.setString(2, name);
                pst.setInt(3, episode_id);
                pst.setString(4, location);
                pst.setString(5, duration);
                pst.setString(6, language);
                pst.setInt(7, artist_id);
                pst.setInt(8, genre_id);
                pst.setInt(9, user_id);
                pst.setInt(10,playlist_id);
                int i = pst.executeUpdate();
                if (i > 0)
                    System.out.println("podcast added to playlist succesfully");
            }
            else
            {
                System.out.println("podcast already Exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void searchPodcastById(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a podcast id to search");
        int podcast_id = s.nextInt();
        String location = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(" select * from podcast_details d   join podcast_episodes p using(episode_id) join artists using(artist_id) join genres using(genre_id) where podcast_id=" + podcast_id);
            while (rs.next()) {
                location = rs.getString(6);
                System.out.println(rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getInt(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();
        if (ans == 1) {
            SongPlayer.songPlayer(location);
        } else if (ans == 2) {
             playlist(user_id);
        }

    }

    static void searchPodcastByName(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a podcast name to search");
        String podcast_name = s.next();
        String location = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(" select * from podcast_details d   join podcast_episodes p using(episode_id) join artists using(artist_id) join genres using(genre_id) where podcast_name='"+podcast_name+"'");
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getInt(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playPodcast(podcastId);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
             playlist(user_id);
        }

    }
    static void searchPodcastByLanguage(int user_id)
    {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a podcast name to search");
        String language = s.next();
        String location = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(" select * from podcast_details d   join podcast_episodes p using(episode_id) join artists using(artist_id) join genres using(genre_id) where language='"+language+"'");
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getInt(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playPodcast(podcastId);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchPodcastByGenre(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a genre name to search");
        String genre = s.next();
        int genre_id=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst=st.executeQuery("select genre_id from genres where genre_name='"+genre+"'");
            while(rst.next())
            {
                genre_id= rst.getInt(1);
            }
            ResultSet rs = st.executeQuery(" select * from podcast_details d   join podcast_episodes p using(episode_id) join artists using(artist_id) join genres using(genre_id) where genre_id="+genre_id);
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getInt(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playPodcast(podcastId);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchPodcastByArtistName(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a genre name to search");
        String artist_name = s.next();
        int artist_id=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst=st.executeQuery("select artist_id from artists where artist_name='"+artist_name+"'");
            while(rst.next())
            {
                artist_id= rst.getInt(1);
            }
            ResultSet rs = st.executeQuery(" select * from podcast_details d   join podcast_episodes p using(episode_id) join artists using(artist_id) join genres using(genre_id) where artist_id="+artist_id);
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getInt(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playPodcast(podcastId);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchPodcastByEpisodeName(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a genre name to search");
        String episode_name = s.next();
        int episode_id=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst=st.executeQuery("select episode_id from podcast_episodes where episode_name='"+episode_name+"'");
            while(rst.next())
            {
                episode_id= rst.getInt(1);
            }
            ResultSet rs = st.executeQuery(" select * from podcast_details d   join podcast_episodes p using(episode_id) join artists using(artist_id) join genres using(genre_id) where artist_id="+episode_id);
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getInt(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playPodcast(podcastId);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchPodcastByAlbumName(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a genre name to search");
        String album_name = s.next();
        int album_id=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst=st.executeQuery("select album_id from albums where album_name='"+album_name+"'");
            while(rst.next())
            {
                album_id= rst.getInt(1);
            }
            ResultSet rs = st.executeQuery(" select * from podcast_details d   join podcast_episodes p using(episode_id) join artists using(artist_id) join genres using(genre_id) where album_id="+album_id);
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getInt(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the podcast or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
            playPodcast(podcastId);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the postcast id to play");
            int podcastId = sc.nextInt();
           playlist(user_id);
        }

    }

    static void newPodcastPlayList(int userId)
    {
        Scanner sc=new Scanner(System.in);
        int podcast_id=0;
        String name=null;
        int playlistid=0;
        System.out.println("Enter the name to be added to new playlist");
        name=sc.next();

        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            PreparedStatement pst=con.prepareStatement("insert into playlist(user_id,name) values(?,?)");
            pst.setInt(1,userId);
            pst.setString(2,name);
            int i=pst.executeUpdate();

            ResultSet rs=pst.executeQuery("select playlist_id from playlist where playlist_id=(select max(playlist_id)from playlist);");
            while(rs.next())
            {
                playlistid=rs.getInt(1);
                System.out.println("Your newly Created playlist id is "+rs.getInt(1)+" with the name"+name);
            }
            if(i>0)
            {
                System.out.println("please enter the podcast id to add to your playlist");
                podcast_id=sc.nextInt();
                podcastPlayList(playlistid,userId,podcast_id);


            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    static void playlist(int uId)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("do you want to add the podcast to existing playlist or to create new playlist");
        System.out.println("enter \n1.new playlist" +
                "\n 2.existing playlist");
        int capture=0;
        capture=sc.nextInt();
        if(capture==1)
        {
            newPodcastPlayList(uId);
        }
        else if(capture==2)
        {
            int playlist_id=0;
            try
            {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
                PreparedStatement psts=con.prepareStatement("select playlist_id,name from playlist where user_Id="+uId);
                ResultSet resultSet=psts.executeQuery();
                while(resultSet.next())
                {
                    System.out.println("Playlist Id  "+resultSet.getInt(1)+"  PlayList Name:"+resultSet.getString(2));
                }
                System.out.println("enter the playList id in which You want to add the song");
                playlist_id=sc.nextInt();

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


            System.out.println("Enter the Podcast ID that you wanted to Add to playlist");
            int podcast_id = sc.nextInt();
            podcastPlayList(playlist_id,uId,podcast_id);
        }
    }

    static void changeplaylistName(int user_id)
    {
        int j=0;
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement sst=con.createStatement();
            ResultSet result=sst.executeQuery("select name from playlist where user_id="+user_id);
            System.out.println("select a playlist which the name should be changed");
            while(result.next())
            {
                System.out.println(result.getString(1));
            }
            Scanner s=new Scanner(System.in);
            System.out.println("Enter the Name of the playlist to be changed");
            String oldName=s.next();
            System.out.println("Enter the new Name");
            String newName=s.next();
            int i=0;
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from playlist where name='"+oldName+"' and user_id="+user_id);
            while(rs.next())
            {
                i++;
                System.out.println("i");
            }
            if(i>0) {
                PreparedStatement pst = con.prepareStatement("update playlist set name=? where name=? and user_id="+user_id);
                pst.setString(1,newName );
                pst.setString(2, oldName);
                 j=pst.executeUpdate();
                if(j>0)
                {
                    System.out.println("your playlist name changed Successfully");
                }
            }
            else
            {
                System.out.println("There are no playlists available with the given name");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    static void playNextPodcast(int podcastId) {

        String path = null;

        do {
            podcastId++;
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select location from podcast_Details where podcast_id=" + podcastId);
                while (rs.next()) {
                    path = rs.getString(1);
                }
                SongPlayer.songPlayer(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }while(true);
    }
}
