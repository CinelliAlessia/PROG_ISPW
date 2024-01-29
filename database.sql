-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema user
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `user` ;

-- -----------------------------------------------------
-- Schema user
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `user` DEFAULT CHARACTER SET utf8mb3 ;
USE `user` ;

-- -----------------------------------------------------
-- Table `user`.`all_playlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`.`all_playlist` ;

CREATE TABLE IF NOT EXISTS `user`.`all_playlist` (
  `nome_playlist` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`nome_playlist`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `user`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`.`user` ;

CREATE TABLE IF NOT EXISTS `user`.`user` (
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `superUser` TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (`email`),
  INDEX `idx_email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `user`.`generi_musicali`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`.`generi_musicali` ;

CREATE TABLE IF NOT EXISTS `user`.`generi_musicali` (
  `Pop` TINYINT NULL DEFAULT NULL,
  `Indie` TINYINT NULL DEFAULT NULL,
  `Classic` TINYINT NULL DEFAULT NULL,
  `Rock` TINYINT NULL DEFAULT NULL,
  `Electronic` TINYINT NULL DEFAULT NULL,
  `House` TINYINT NULL DEFAULT NULL,
  `HipHop` TINYINT NULL DEFAULT NULL,
  `Jazz` TINYINT NULL DEFAULT NULL,
  `Acoustic` TINYINT NULL DEFAULT NULL,
  `REB` TINYINT NULL DEFAULT NULL,
  `Country` TINYINT NULL DEFAULT NULL,
  `Alternative` TINYINT NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_generi_musicali_user1`
    FOREIGN KEY (`email`)
    REFERENCES `user`.`user` (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `user`.`playlist_utente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`.`playlist_utente` ;

CREATE TABLE IF NOT EXISTS `user`.`playlist_utente` (
  `email` VARCHAR(45) NOT NULL,
  `nome_utente` VARCHAR(45) NOT NULL,
  `nome_playlist` VARCHAR(45) NOT NULL,
  `link` VARCHAR(45) NOT NULL,
  INDEX `fk_Playlist_Utente_user1_idx` (`email` ASC) VISIBLE,
  PRIMARY KEY (`email`, `nome_playlist`),
  INDEX `fk_playlist_utente_all_playlist1_idx` (`nome_playlist` ASC) VISIBLE,
  CONSTRAINT `fk_playlist_utente_all_playlist1`
    FOREIGN KEY (`nome_playlist`)
    REFERENCES `user`.`all_playlist` (`nome_playlist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_playlist_utente_user1`
    FOREIGN KEY (`email`)
    REFERENCES `user`.`user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_playlist_utente_generi_musicali1`
    FOREIGN KEY (`email`)
    REFERENCES `user`.`generi_musicali` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
