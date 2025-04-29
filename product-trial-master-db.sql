-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : mar. 29 avr. 2025 à 21:12
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `product-trial-master-db`
--

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_at` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `internal_reference` varchar(255) DEFAULT NULL,
  `inventory_status` enum('INSTOCK','LOWSTOCK','OUTOFSTOCK') DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `rating` double NOT NULL,
  `shell_id` bigint(20) DEFAULT NULL,
  `updated_at` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `product`
--

INSERT INTO `product` (`id`, `category`, `code`, `created_at`, `description`, `image`, `internal_reference`, `inventory_status`, `name`, `price`, `quantity`, `rating`, `shell_id`, `updated_at`) VALUES
(2, 'Audio', 'PROD-001', 1745876045541, 'Casque sans fil avec réduction de bruit', 'https://example.com/images/casque.png', 'INT-12345', 'INSTOCK', 'Casque Bluetooth', 149.99, 50, 4.5, 1, 1745876045541),
(3, 'Informatique', 'PROD-003', 1745953345706, 'PC portable avec Intel i7, 16Go RAM, SSD 512Go', 'https://example.com/images/laptop.png', 'INT-12347', 'INSTOCK', 'Ordinateur Portable 15\"', 999.99, 30, 4.7, 3, 1745953345706),
(4, 'Téléphonie', 'PROD-004', 1745953358478, 'Smartphone 5G avec triple caméra', 'https://example.com/images/smartphone.png', 'INT-12348', 'INSTOCK', 'Smartphone 6.5\" OLED', 699.99, 75, 4.6, 4, 1745953358478);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `firstname`, `password`, `role`, `username`) VALUES
(3, 'mustapha@filale.com', 'FILALE', '$2a$10$eOz41tqJ1FuQeRJbV3lBiOp6TZ0eh2Nx1mXV9ST5SEdShRyNV50Ia', 'USER', 'Mustapha'),
(4, 'admin@admin.com', 'Admin', '$2a$10$M6mdDooznR61S.BKPGW54eOCm7vbtFZzJIjKc.AFZH0KbcOjD7eIa', 'ADMIN', 'Admin');

-- --------------------------------------------------------

--
-- Structure de la table `user_cart`
--

CREATE TABLE `user_cart` (
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user_cart`
--

INSERT INTO `user_cart` (`user_id`, `product_id`) VALUES
(3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `user_wishlist`
--

CREATE TABLE `user_wishlist` (
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`);

--
-- Index pour la table `user_cart`
--
ALTER TABLE `user_cart`
  ADD PRIMARY KEY (`user_id`,`product_id`),
  ADD KEY `FKtntyrv65a8s1lue54vrrqbeor` (`product_id`);

--
-- Index pour la table `user_wishlist`
--
ALTER TABLE `user_wishlist`
  ADD PRIMARY KEY (`user_id`,`product_id`),
  ADD KEY `FK2oknuoi371ly7gtylfxbwlk9` (`product_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `user_cart`
--
ALTER TABLE `user_cart`
  ADD CONSTRAINT `FKsrduvvrrxxek0h3bcpsb5aij7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKtntyrv65a8s1lue54vrrqbeor` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Contraintes pour la table `user_wishlist`
--
ALTER TABLE `user_wishlist`
  ADD CONSTRAINT `FK2oknuoi371ly7gtylfxbwlk9` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKclurv83y878u3we8ya7yjgd5m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
