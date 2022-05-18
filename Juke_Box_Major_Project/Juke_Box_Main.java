import com.mysql.cj.jdbc.exceptions.ConnectionFeatureNotAvailableException;

import java.sql.*;
import java.util.Scanner;

public class Juke_Box_Main {
    public static void main(String args[]) throws  Exception
    {
        System.out.println("Welcome to Star Juke Box");
        Scanner sc = new Scanner(System.in);
        int answer=0;
        do {
            System.out.println("1.signUp \n 2.signIn");
            int choice = sc.nextInt();
                if (choice == 1) {
                    int i = signup();

                } else if (choice == 2)
                {
                    int tell=0;

                        int enter = 0;
                        String uName = null;
                        int uId = 0;
                        System.out.println("what do you want to enter? \n enter 1 for UserName and 2 For User_id");
                        int ask = sc.nextInt();
                        if (ask == 1) {
                            System.out.println("Please Enter Your UserName");
                            uName = sc.next();
                        } else {
                            System.out.println("Please Enter Your UserId");
                            uId = sc.nextInt();
                        }
                        System.out.println("Enter Your Password");
                        String password = sc.next();
                        try {
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
                            PreparedStatement pst = con.prepareStatement("select user_id,first_name,last_name from user_details where (user_id=? || user_name=?) && user_password=?");
                            pst.setInt(1, uId);
                            pst.setString(2, uName);
                            pst.setString(3, password);
                            ResultSet rs = pst.executeQuery();
                            String uname = null;
                            while (rs.next()) {
                                uId = rs.getInt(1);
                                uname = rs.getString(2);
                                System.out.println("Hello!!!" + rs.getString(2) + " " + rs.getString(3));
                                System.out.println("You are Logged In SuccessFully");
                            }
                            if (uname == null)
                                System.out.println("Entered Credentials Are Wrong");
                            else {
                                do{
                                System.out.println("press " +
                                        "\n1.songs \n2.podcasts  \n3.View Your playLists");
                                int input = sc.nextInt();
                                if (input == 1)
                                {
                                    System.out.println("press " +
                                            "\n 1.View All Songs" +
                                            "\n 2.Play A Song" +
                                            "\n 3.Create a Song Play List" +
                                            "\n 4.Search For A Song");
                                    int ip = sc.nextInt();

                                    switch (ip) {
                                        case 1:
                                            displaySongs(uId);
                                            break;
                                        case 2:
                                            System.out.println("Enter a Song Id to play");
                                            Scanner s = new Scanner(System.in);
                                            int song_Id = s.nextInt();
                                            playASong(song_Id);
                                            break;
                                        case 3:
                                                    playlist(uId);
                                            break;
                                        case 4: {
                                            System.out.println("Enter " +
                                                    "\n 1.Search by song Id" +
                                                    "\n 2.search by song name" +
                                                    "\n 3.search by genre" +
                                                    "\n 4.search by language" +
                                                    "\n 5.search by artist name" +
                                                    "\n 6.search by album name");
                                            int ip1 = sc.nextInt();

                                            switch (ip1) {
                                                case 1:
                                                    songSearchByID(uId);
                                                    break;
                                                case 2:
                                                    searchSongByName(uId);
                                                    break;
                                                case 3:
                                                    searchSongByGenre(uId);
                                                    break;
                                                case 4:
                                                    searchSongByLanguage(uId);
                                                    break;
                                                case 5:
                                                    searchSongByArtistName(uId);
                                                    break;
                                                case 6:
                                                    searchSongByAlbumName(uId);
                                                    break;
                                            }
                                        }
                                        break;

                                    }
                                } else if (input == 2) {
                                    System.out.println("press 1.View All PodCasts" +
                                            "\n 2.Play A Podcast" +
                                            "\n 3.Create A Podcast PlayList" +
                                            "\n 4.Search for A PodCast");
                                    int ip = sc.nextInt();
                                    switch (ip) {
                                        case 1:
                                            PodCast.display(uId);
                                            break;
                                        case 2:
                                            System.out.println("Enter the postcast id to play");
                                            int podcast_id = sc.nextInt();
                                            PodCast.playPodcast(podcast_id);
                                            break;
                                        case 3:
                                             PodCast.playlist(uId);
                                            break;
                                        case 4: {
                                            System.out.println("Enter " +
                                                    "\n 1.Search by Podcast id" +
                                                    "\n 2.search by podcast name" +
                                                    "\n 3.search by genre" +
                                                    "\n 4.search by language" +
                                                    "\n 5.search by artist name" +
                                                    "\n 6.search by episode name");
                                            int ip1 = sc.nextInt();

                                            switch (ip1) {
                                                case 1:
                                                    PodCast.searchPodcastById(uId);
                                                    break;
                                                case 2:
                                                    PodCast.searchPodcastByName(uId);
                                                    break;
                                                case 3:
                                                    PodCast.searchPodcastByGenre(uId);
                                                    break;
                                                case 4:
                                                    PodCast.searchPodcastByLanguage(uId);
                                                    break;
                                                case 5:
                                                    PodCast.searchPodcastByArtistName(uId);
                                                    break;
                                                case 6:
                                                    PodCast.searchPodcastByAlbumName(uId);
                                                    break;
                                            }
                                        }
                                        break;

                                    }
                                }
                                else if(input==3)
                                {
                                    int z=0;
                                    System.out.println("press \n1.Podcast Playlist \n 2.Song Playlist \n 3.Change playlist Name");
                                    int inputs=sc.nextInt();
                                    if(inputs==1)
                                    {

                                        try {
                                            Statement st = con.createStatement();
                                            System.out.println("============Your podcast playlists are=============");
                                            ResultSet rss = st.executeQuery("select * from  playlist  join podcast_playlist using(playlist_id) where playlist.user_id=" + uId);
                                            while (rss.next()) {
                                                z++;
                                                System.out.println("Podcast_Id:" + rss.getInt(5) + "  Podcast Name:" + rss.getString(6) +""+rss.getInt(4)+ "   PlayList Name:" + rss.getString(3) + "   Language:" + rss.getString(10));
                                            }
                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if(z==0)
                                            System.out.println("No playlists created yet");
                                        if(z>0)
                                        {
                                            System.out.println("Do you want to play the podcasts from playlist or Exit");
                                            System.out.println("press 1 to play any other key to exit");
                                            int abcd = sc.nextInt();
                                            if (abcd == 1) {
                                                System.out.println("Enter the podcast id to play");
                                                int podcastid = sc.nextInt();
                                               PodCast.playPodcast(podcastid);
                                            }
                                        }
                                    }
                                    else if(inputs==2)
                                    {

                                        try {


                                            Statement st = con.createStatement();
                                            ResultSet rsst = st.executeQuery("select * from  playlist  join song_playlist using(playlist_id) where playlist.user_id="+uId);
                                            while (rsst.next()) {
                                                z++;
                                                System.out.println("Song_Id:" + rsst.getInt(5) + "  Song Name:" + rsst.getString(6) + "   Language:" + rsst.getString(9) + "   Playlist Id:" + rsst.getInt(1));
                                            }
                                            if(z==0)
                                                System.out.println("No playlists are created Yet");
                                            if(z>0) {
                                                System.out.println("============Your Song playlists are=============");
                                                System.out.println("=======================================================================");
                                                System.out.println("Do you want to play the Songs from playlist or Exit");
                                                System.out.println("press 1 to play any other key to exit");
                                                int abcd = sc.nextInt();
                                                if (abcd == 1) {
                                                    System.out.println("Enter the Song id to play");
                                                    int songid = sc.nextInt();
                                                    playASong(songid);
                                                }
                                            }
                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else if(inputs==3)
                                    {
                                            PodCast.changeplaylistName(uId);
                                    }
                                }
                                System.out.println("press 1 to continue any other key exit");
                                tell=sc.nextInt();
                            }while(tell==1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                }
                else
                {
                    System.out.println("please provide the co'RRR'ect Input");
                }
                System.out.println("press 1 to continue any other key exit");
                answer=sc.nextInt();
            }while (answer==1);

    }


   static int signup()
    {
        System.out.println("please enter your Details");
        Scanner s=new Scanner(System.in);
        System.out.println("First Name:");
        String fname=s.next();
        System.out.println("Last Name:");
        String lname=s.next();
        System.out.println("Mobile Number:");
        long  phno=s.nextLong();
        System.out.println("Email:");
        String email=s.next();
        System.out.println("Password:");
        String password=s.next();
        int i=0;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject","root","Charan@9");
            PreparedStatement pst=con.prepareStatement("insert into user_details(first_name,last_name,user_name,user_phno,user_email,user_password) values(?,?,?,?,?,?)");
            pst.setString(1,fname);
            pst.setString(2,lname);
            pst.setString(3,fname+lname+"@45");
            pst.setLong(4,phno);
            pst.setString(5,email);
            pst.setString(6,password);
            i=pst.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(i>0) {
            System.out.println("Congratulations!!!!!Account Created Successfully \n Your UserName is " + fname + lname + "@45");
            System.out.println("please sigin again");
            return 1;
        }
        else
            return 0;
    }

    static void displaySongs(int user_id)
    {
        Scanner s=new Scanner(System.in);
        String location=null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st=con.createStatement();
            ResultSet rs =st.executeQuery("select * from song_details");
            while(rs.next())
            {
                location=rs.getString(3);
                System.out.println("song_Id:"+rs.getInt(1)+"\n song_name:"+rs.getString(2)+"\n song_Location"+rs.getString(3)+"\n Duration:"+rs.getString(4)+"\n Language:"+rs.getString(5)+"\n Artist_Id:"+rs.getInt(6)+"\n album_id:"+rs.getInt(7)+"\n Genre_Id"+rs.getInt(8));
            }
            System.out.println("Do you want to listen the song or add it to playlist \n" +
                    "  1.play the song" +
                    "\n 2.add to playlist ");
            int ans=s.nextInt();
            if(ans==1)
            {
                System.out.println("enter  the song ID to play");
                int songid=s.nextInt();
                playASong(songid);

            }
            else if(ans==2)
            {
                playlist(user_id);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    static void playASong(int songId)
    {

        String filepath=null;
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from song_details where song_id="+songId);
            while(rs.next())
            {
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getInt(6));
            }
            ResultSet rst=st.executeQuery("select location from song_details where song_id="+songId);
            while(rst.next())
            {
                filepath=rst.getString(1);
            }
            SongPlayer.songPlayer(filepath);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void newSongPlayList(int userId)
    {
        Scanner sc=new Scanner(System.in);
        int songid=0;
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
//                System.out.println("enter the playlist_id to add song");
//                playlistid
                System.out.println("please enter the song id to add to your playlist");
                songid=sc.nextInt();
                songPlayList(playlistid,userId,songid);


            }

        }
        catch(Exception e)
        {

        }
    }





    static void songPlayList(int playlist_id,int user_Id,int songId)
    {

        String name = null,location=null,duration=null,language=null;
        int album_id=0,artist_id=0,genre_id=0,j=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst = st.executeQuery("select * from song_playlist where song_id="+ songId+" and playlist_id="+playlist_id);
            while (rst.next()) {
                j++;
            }
            if (j == 0) {
                ResultSet rs = st.executeQuery("select * from song_Details where song_id=" + songId);
                while (rs.next()) {
                    songId = rs.getInt(1);
                    name = rs.getString(2);
                    location = rs.getString(3);
                    duration = rs.getString(4);
                    language = rs.getString(5);
                    artist_id = rs.getInt(6);
                    album_id = rs.getInt(7);
                    genre_id = rs.getInt(8);
                }
                PreparedStatement pst = con.prepareStatement("insert into song_playlist(song_id,name,location,duration,language,artist_id,album_id,genre_id,user_id,playlist_id) values(?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, songId);
                pst.setString(2, name);
                pst.setString(3, location);
                pst.setString(4, duration);
                pst.setString(5, language);
                pst.setInt(6, artist_id);
                pst.setInt(7, album_id);
                pst.setInt(8, genre_id);
                pst.setInt(9, user_Id);
                pst.setInt(10,playlist_id);
                int i = pst.executeUpdate();
                System.out.println("===============================================================================");
                if (i > 0)
                    System.out.println("song added to playlist succesfully");
            }
            else {
                System.out.println("===============================================================================");
                System.out.println("slected song already exists in the playlist");
            }
            }
             catch(Exception e)
            {
                e.printStackTrace();
            }

    }

    static void songSearchByID(int user_id)
    {
        Scanner s=new Scanner(System.in);
        System.out.println("Enter a song id to search");
        int song_id=s.nextInt();
        String location=null;
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject","root","Charan@9");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from song_details where song_id="+song_id);
            while(rs.next())
            {
                location=rs.getString(3);
                System.out.println("song_Id:"+rs.getInt(1)+"\n song_name:"+rs.getString(2)+"\n song_Location"+rs.getString(3)+"\n Duration:"+rs.getString(4)+"\n Language:"+rs.getString(5)+"\n Artist_Id:"+rs.getInt(6)+"\n album_id:"+rs.getInt(7)+"\n Genre_Id"+rs.getInt(8));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the song or add it to playlist \n" +
                "1.play the song" +
                "\n 2.add to playlist ");
        int ans=s.nextInt();
        if(ans==1)
        {
            SongPlayer.songPlayer(location);
        }
        else if(ans==2)
        {
             playlist(user_id);
        }

    }
    static void searchSongByName(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a podcast name to search");
        String song_name = s.next();
        String location = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(" select * from song_details  join albums p using(album_id) join artists using(artist_id) join genres using(genre_id) where name='"+song_name+"'");
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12)+" "+rs.getString(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the song or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to play");
            int song_id= sc.nextInt();
            playASong(song_id);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to play");
            int songid = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchSongByGenre(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a song genre to search");
        String genre = s.next();
        int genre_id=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst=st.executeQuery("select genre_id from genres where genre_name='"+genre+"'");
            while(rst.next())
            {
                genre_id=rst.getInt(1);
            }
            ResultSet rs = st.executeQuery(" select * from song_details  join albums p using(album_id) join artists using(artist_id) join genres using(genre_id) where genre_id='"+genre_id+"'");
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12)+" "+rs.getString(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the song or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to play");
            int song_id= sc.nextInt();
            playASong(song_id);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to add it to your play List");
            int songid = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchSongByLanguage(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a podcast name to search");
        String language = s.next();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(" select * from song_details  join albums p using(album_id) join artists using(artist_id) join genres using(genre_id) where language='"+language+"'");
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12)+" "+rs.getString(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the song or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to play");
            int song_id= sc.nextInt();
            playASong(song_id);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to play");
            int songid = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchSongByArtistName(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a song genre to search");
        String name= s.next();
        int artist_id=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst=st.executeQuery("select artist_id from artists where artist_name='"+name+"'");
            while(rst.next())
            {
                artist_id=rst.getInt(1);
            }
            ResultSet rs = st.executeQuery(" select * from song_details  join albums p using(album_id) join artists using(artist_id) join genres using(genre_id) where genre_id='"+artist_id+"'");
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12)+" "+rs.getString(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the song or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to play");
            int song_id= sc.nextInt();
            playASong(song_id);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to add it to your play List");
            int songid = sc.nextInt();
            playlist(user_id);
        }

    }
    static void searchSongByAlbumName(int user_id) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a song genre to search");
        String name= s.next();
        int album_id=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBoxMajorProject", "root", "Charan@9");
            Statement st = con.createStatement();
            ResultSet rst=st.executeQuery("select album_id from albums where album_name='"+name+"'");
            while(rst.next())
            {
                album_id=rst.getInt(1);
            }
            ResultSet rs = st.executeQuery(" select * from song_details  join albums p using(album_id) join artists using(artist_id) join genres using(genre_id) where genre_id='"+album_id+"'");
            while (rs.next()) {

                System.out.println(rs.getInt(4)+" "+rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12)+" "+rs.getString(13));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Do you want to listen the song or add it to playlist \n" +
                "1.play the Podcast" +
                "\n 2.add to playlist ");
        int ans = s.nextInt();

        if (ans == 1)
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to play");
            int song_id= sc.nextInt();
            playASong(song_id);
        } else if (ans == 2) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the song id to add it to your play List");
            int songid= sc.nextInt();
            playlist(user_id);
        }

    }

    static void playlist(int uId)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("do you want to add the songs to existing playlist or to create new playlist");
        System.out.println("enter \n1.new playlist" +
                "\n 2.existing playlist");
        int capture=0;
        capture=sc.nextInt();
        if(capture==1)
        {
            newSongPlayList(uId);
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


            System.out.println("Enter the Song ID that you wanted to Add to playlist");
            int songId = sc.nextInt();
            songPlayList(playlist_id,uId, songId);
        }
    }
}

