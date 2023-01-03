-- MySQL Script generated by MySQL Workbench
-- Tue Jan  3 00:07:54 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `store` DEFAULT CHARACTER SET utf8 ;
USE `store` ;

-- -----------------------------------------------------
-- Table `store`.`gift_certificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`gift_certificates` (
  `id_certificates` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `description` VARCHAR(120) NOT NULL,
  `price` DECIMAL(7,2) NOT NULL,
  `duration` INT(11) NOT NULL,
  `create_date` TIMESTAMP NOT NULL,
  `last_update_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id_certificates`))
ENGINE = InnoDB
AUTO_INCREMENT = 5936
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`statuses` (
  `id_status` INT(11) NOT NULL,
  `name` VARCHAR(120) NULL DEFAULT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `id_statuses` INT(11) NOT NULL,
  PRIMARY KEY (`id_status`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`users` (
  `id_users` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(120) NOT NULL,
  `last_name` VARCHAR(120) NOT NULL,
  `email` VARCHAR(120) NOT NULL,
  `password` VARCHAR(120) NOT NULL,
  `id_status` INT(11) NULL DEFAULT NULL,
  `create_date` TIMESTAMP NULL DEFAULT NULL,
  `update_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id_users`),
  INDEX `user_status_forenkey` (`id_status` ASC),
  CONSTRAINT `user_status_forenkey`
    FOREIGN KEY (`id_status`)
    REFERENCES `store`.`statuses` (`id_status`))
ENGINE = InnoDB
AUTO_INCREMENT = 2039
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`orders` (
  `id_orders` INT(11) NOT NULL AUTO_INCREMENT,
  `cost` DECIMAL(7,2) NOT NULL,
  `purchase_time` TIMESTAMP NOT NULL,
  `id_users` INT(11) NOT NULL,
  PRIMARY KEY (`id_orders`, `id_users`),
  INDEX `fk_orders_users1_idx` (`id_users` ASC),
  CONSTRAINT `FKhur7mikdwaqa7j9rnu62s4bp`
    FOREIGN KEY (`id_users`)
    REFERENCES `store`.`users` (`id_users`),
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`id_users`)
    REFERENCES `store`.`users` (`id_users`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 2014
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`order_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`order_details` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(7,2) NOT NULL,
  `id_orders` INT(11) NOT NULL,
  `id_certificates` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_details_orders1_idx` (`id_orders` ASC),
  INDEX `fk_order_details_gift_certificates1_idx` (`id_certificates` ASC),
  CONSTRAINT `FK67264ld62thvmbv3v1pm77utn`
    FOREIGN KEY (`id_orders`)
    REFERENCES `store`.`orders` (`id_orders`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKjuiymspv7ij76r8ralym73np6`
    FOREIGN KEY (`id_certificates`)
    REFERENCES `store`.`gift_certificates` (`id_certificates`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_details_gift_certificates1`
    FOREIGN KEY (`id_certificates`)
    REFERENCES `store`.`gift_certificates` (`id_certificates`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_details_orders1`
    FOREIGN KEY (`id_orders`)
    REFERENCES `store`.`orders` (`id_orders`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5921
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`roles` (
  `id_roles` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_roles`),
  UNIQUE INDEX `UKofx66keruapi6vyqpv6f2or37` (`name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`tags` (
  `id_tags` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id_tags`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  UNIQUE INDEX `UKt48xdq560gs3gap9g7jg36kgc` (`name` ASC),
  UNIQUE INDEX `UK_t48xdq560gs3gap9g7jg36kgc` (`name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 42
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`tags_gift_certificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`tags_gift_certificates` (
  `id_tags` INT(11) NOT NULL,
  `id_certificates` INT(11) NOT NULL,
  PRIMARY KEY (`id_tags`, `id_certificates`),
  INDEX `fk_tags_has_gift_certificate_gift_certificate1_idx` (`id_certificates` ASC),
  INDEX `fk_tags_has_gift_certificate_tags_idx` (`id_tags` ASC),
  CONSTRAINT `FKbobo2fux6f2jn0nyecgsk02g0`
    FOREIGN KEY (`id_certificates`)
    REFERENCES `store`.`gift_certificates` (`id_certificates`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FKnrob4spcd9p5yohce0tlhb7yc`
    FOREIGN KEY (`id_tags`)
    REFERENCES `store`.`tags` (`id_tags`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tags_has_gift_certificate_gift_certificate1`
    FOREIGN KEY (`id_certificates`)
    REFERENCES `store`.`gift_certificates` (`id_certificates`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tags_has_gift_certificate_tags`
    FOREIGN KEY (`id_tags`)
    REFERENCES `store`.`tags` (`id_tags`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `store`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`users_roles` (
  `id_roles` INT(11) NOT NULL,
  `id_users` INT(11) NOT NULL,
  PRIMARY KEY (`id_roles`, `id_users`),
  INDEX `fk_roles_has_users_users1_idx` (`id_users` ASC),
  INDEX `fk_roles_has_users_roles1_idx` (`id_roles` ASC),
  CONSTRAINT `FKb7c3ykbm2y176wujc2k5ci6l7`
    FOREIGN KEY (`id_roles`)
    REFERENCES `store`.`roles` (`id_roles`),
  CONSTRAINT `FKq0tw71wffd6bfr4fofq7iiqqi`
    FOREIGN KEY (`id_users`)
    REFERENCES `store`.`users` (`id_users`),
  CONSTRAINT `fk_roles_has_users_roles1`
    FOREIGN KEY (`id_roles`)
    REFERENCES `store`.`roles` (`id_roles`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_roles_has_users_users1`
    FOREIGN KEY (`id_users`)
    REFERENCES `store`.`users` (`id_users`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;