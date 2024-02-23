

<?php

require_once __DIR__ . '/vendor/autoload.php';
use PhpAmqpLib\Connection\AMQPStreamConnection;
use PhpAmqpLib\Message\AMQPMessage;

        $monPdo = null;
        $monPdoFrigo = null;

        // Le constructeur
         
        $dsn = 'mysql:host=localhost;dbname=frigo';
        $username = 'root';
        $password = 'root';

        try {
            $monPdo = new PDO($dsn, $username, $password);
           
        } catch(PDOException $e) {
            die("La connexion a échoué: " . $e->getMessage());
        }

        $monPdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $monPdo->exec("SET CHARACTER SET utf8");

        $connection = new AMQPStreamConnection('localhost', 5672, 'guest', 'guest');
            $channel = $connection->channel();

            $channel->exchange_declare('logs', 'fanout', false, false, false);

            list($queue_name, ,) = $channel->queue_declare("", false, false, true, false);

            $channel->queue_bind($queue_name, 'logs');

            echo " [*] Waiting for logs. To exit press CTRL+C\n";

            $callback = function ($msg) {
                echo ' [x] Received ', $msg->body, "\n";
                // Insérer le traitement du message ici
                $jsonFile = $msg->body;
                $resultat = decode_insert($jsonFile, $monPdo);
                if ($resultat) {
                    echo "Message inséré dans la base de données avec succès.\n" + $resultat;
                } else {
                    echo "Erreur lors de l'insertion du message dans la base de données.\n" + $resultat;
                }
            };

            $channel->basic_consume($queue_name, '', false, true, false, false, $callback);

            while ($channel->is_consuming()) {
                $channel->wait();
            }

            $channel->close();
            $connection->close();

        // LES METHODES POUR VOIR LES INFORMATIONS DANS LA BASE DE DONNÉES
 /*        function getLesPositionsCapteurs(){
            $laRequete = "SELECT position_capteur FROM capteur";
            $leResultat = $monPdo->query($laRequete);
            $laPositionDesCapteurs = $leResultat->fetchAll();

            return $laPositionDesCapteurs;
        }

         function getLesDatesHeuresDansHistoriques(){
            $laRequete = "SELECT dateheure_historique FROM historiquefrigo";
            $leResultat = $monPdo->query($laRequete);
            $lesDatesHeures = $leResultat->fetchAll();

            return $lesDatesHeures;
        }

         function getLesEtatsDesCapteurs(){
            $laRequete = "SELECT etat_frigo_1, etat_frigo_2, etat_frigo_3 FROM historiquefrigo";
            $leResultat = $monPdo->query($laRequete);
            $lesEtatsDesCapteurs = $leResultat->fetchAll();

            return $lesEtatsDesCapteurs;
        }

         function getLesEtatsEnFonctionDate($date){
            $laRequete = "SELECT etat_frigo_1, etat_frigo_2, etat_frigo_3 FROM historiquefrigo WHERE dateheure_historique BETWEEN '". $date ." 00:00:00' AND '" . $date ." 23:59:59'";
            $leResultat = self::$monPdo->query($laRequete);
            $lesEtatsDesCapteurs = $leResultat->fetchAll();

            return $lesEtatsDesCapteurs;
        }
*/

        // LES METHODES POUR ENTRER LES INFORMATIONS DANS LA BASE DE DONNÉES
         function insererUneDonneeDansHistorique($dateHeure, $etat1, $etat2, $etat3, $pdo){
            $laRequete = "INSERT INTO historiquefrigo(dateheure_historique, etat_frigo_1, etat_frigo_2, etat_frigo_3) VALUES('". $dateHeure."',". $etat1 ."," . $etat2 ."," . $etat3 .")";
            $leResultat = $pdo->exec($laRequete);
            return $leResultat;
        }
        

        // DECODAGE JSON ET INSERTION BDD
         function decode_insert($jsonFile, $pdo){
            $leFichierJSON = json_decode($jsonFile, true);

            if ($leFichierJSON === null) {
                return false;
            }

            // Vérifier si les clés nécessaires existent dans le JSON
            if (!isset($leFichierJSON['dateheure_historique']) || !isset($leFichierJSON['etat_frigo_1']) || !isset($leFichierJSON['etat_frigo_2']) || !isset($leFichierJSON['etat_frigo_3'])) {
                return false;
            }

            $dateHeure = $leFichierJSON['dateheure_historique'];
            $etat1 = $leFichierJSON['etat_frigo_1'];
            $etat2 = $leFichierJSON['etat_frigo_2'];
            $etat3 = $leFichierJSON['etat_frigo_3'];

            $resultat = insererUneDonneeDansHistorique($dateHeure, $etat1, $etat2, $etat3, $pdo);

            return $resultat;
        }

        /*
        // CONSOMMER LE MESSAGE 
         function subcribeMessage(){
            
            $connection = new AMQPStreamConnection('localhost', 5672, 'guest', 'guest');
            $channel = $connection->channel();

            $channel->exchange_declare('logs', 'fanout', false, false, false);

            list($queue_name, ,) = $channel->queue_declare("", false, false, true, false);

            $channel->queue_bind($queue_name, 'logs');

            echo " [*] Waiting for logs. To exit press CTRL+C\n";

            $callback = function ($msg) {
                echo ' [x] Received ', $msg->body, "\n";
                // Insérer le traitement du message ici
                $jsonFile = $msg->body;
                $resultat = $this->decode_insert($jsonFile);
                if ($resultat) {
                    echo "Message inséré dans la base de données avec succès.\n";
                } else {
                    echo "Erreur lors de l'insertion du message dans la base de données.\n";
                }
            };

            $channel->basic_consume($queue_name, '', false, true, false, false, $callback);

            while ($channel->is_consuming()) {
                $channel->wait();
            }

            $channel->close();
            $connection->close();
        }*/





?>