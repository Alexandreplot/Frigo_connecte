-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Jeu 22 Février 2024 à 14:09
-- Version du serveur :  5.6.20-log
-- Version de PHP :  7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `frigo`
--

-- --------------------------------------------------------

--
-- Structure de la table `capteur`
--

CREATE TABLE IF NOT EXISTS `capteur` (
  `id_capteur` int(11) NOT NULL,
  `position_capteur` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `capteur`
--

INSERT INTO `capteur` (`id_capteur`, `position_capteur`) VALUES
(1, 'Bac à boisson'),
(2, 'Bac à légumes'),
(3, 'Etagère Milieu Gauche');

-- --------------------------------------------------------

--
-- Structure de la table `historiquefrigo`
--

CREATE TABLE IF NOT EXISTS `historiquefrigo` (
`id_historique` int(11) NOT NULL,
  `dateheure_historique` datetime NOT NULL,
  `etat_frigo_1` tinyint(1) NOT NULL,
  `etat_frigo_2` tinyint(1) NOT NULL,
  `etat_frigo_3` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `capteur`
--
ALTER TABLE `capteur`
 ADD PRIMARY KEY (`id_capteur`);

--
-- Index pour la table `historiquefrigo`
--
ALTER TABLE `historiquefrigo`
 ADD PRIMARY KEY (`id_historique`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `historiquefrigo`
--
ALTER TABLE `historiquefrigo`
MODIFY `id_historique` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
