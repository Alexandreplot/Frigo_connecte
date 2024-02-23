package main.java;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoToBDD {
    private final static String QUEUE_NAME = "hello";

    private static final String EXCHANGE_NAME = "logs";

	private static final String BROKER_HOST = System.getenv("broker_host");
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(BROKER_HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(EXCHANGE_NAME, false, false, false, null);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF_8");
                System.out.println(" [x] Received '" + message + "'");
                //insertIntoDatabase(message);
                recupJson(message);
            };
            channel.basicConsume(EXCHANGE_NAME, true, deliverCallback, consumerTag -> {
            });
        }
    }

    private static void insertIntoDatabase(String message) {
        String url = "jdbc:mysql://localhost:3306/frigo";
        String username = "root";
        String password = "root";

        try (java.sql.Connection dbConnection = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO historiquefrigo (dateheure_historique, etat_frigo_1, etat_frigo_2, etat_frigo_3) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(sql);
    

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    private static void recupJson(String message){
        try {
            // Convert JSON string to JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            // Retrieve values from JsonNode
            String dateheure_historique = jsonNode.get("dateheure_historique").asText();
            boolean etat_frigo_1 = jsonNode.get("etat_frigo_1").asBoolean();
            boolean etat_frigo_2 = jsonNode.get("etat_frigo_2").asBoolean();
            boolean etat_frigo_3 = jsonNode.get("etat_frigo_3").asBoolean();

            // Print values
            System.out.println("dateheure_historique: " + dateheure_historique);
            System.out.println("etat_frigo_1: " + etat_frigo_1);
            System.out.println("etat_frigo_2: " + etat_frigo_2);
            System.out.println("etat_frigo_3: " + etat_frigo_3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
