package ruz0074;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    public void insertScore(int score) {
        String sql = "INSERT INTO scores(score) VALUES(?)";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> getTop10Scores() {
        ArrayList<Integer> top10 = new ArrayList<>();
        String sql = "SELECT score FROM scores ORDER BY score DESC LIMIT 10";
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                top10.add(resultSet.getInt("score"));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return top10;
    }

    public int getBestScore() {
        int bestScore = 0;

        String sql = "SELECT MAX(score)as max FROM scores ";
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                bestScore = resultSet.getInt("max");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bestScore;
    }

    public void dropAllRecords() {
        String sql = "DELETE FROM scores";
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        String url = "jdbc:sqlite:C:/Users/rruzi/Desktop/emptygame/src/database.sqlite";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
