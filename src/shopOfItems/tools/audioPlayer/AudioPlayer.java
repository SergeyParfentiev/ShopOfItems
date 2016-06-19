package shopOfItems.tools.audioPlayer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.sql.*;

public class AudioPlayer {

    private boolean play = true;

    private final String volumeDB = "jdbc:derby:TollsForShopDB";

    private String basePath = "/shopOfItems/tools/ringtones/";
    private String chooseYourDestiny = "ChooseYourDestiny.wav";
    private String excellent = "Excellent.wav";
    private String haha = "Haha.wav";

    private final String GET_ALL = "SELECT * FROM sound";

    private Clip clip;
    private AudioInputStream ais;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public AudioPlayer() {
        try {
            clip = AudioSystem.getClip();
            try {
                connection = DriverManager.getConnection(volumeDB);
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(boolean volume) {
        try {
            statement.execute("UPDATE sound SET sound = " + volume + " WHERE id = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean getVolume() throws SQLException {
        preparedStatement = connection.prepareStatement(GET_ALL);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            play = resultSet.getBoolean("sound");
        }
        return play;
    }

    public void chooseYourDestiny() {
        try {
            if (getVolume()) {
                clip.stop();
                ais = AudioSystem.getAudioInputStream(getClass().getResource(basePath + chooseYourDestiny));

                clip = AudioSystem.getClip();
                clip.open(ais);
                clip.setFramePosition(0);
                clip.start();
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void excellent() {
        try {
            if (getVolume()) {
                clip.stop();

                ais = AudioSystem.getAudioInputStream(getClass().getResource(basePath + excellent));

                clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void haha() {
        try {
            if (getVolume()) {
                clip.stop();
                ais = AudioSystem.getAudioInputStream(getClass().getResource(basePath + haha));

                clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | SQLException exc) {
            exc.printStackTrace();
        }
    }
}
