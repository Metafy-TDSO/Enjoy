-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema metafy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema metafy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `metafy` DEFAULT CHARACTER SET utf8 ;
USE `metafy` ;

-- -----------------------------------------------------
-- Table `metafy`.`tb_bebida`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `metafy`.`tb_bebida` ;

CREATE TABLE IF NOT EXISTS `metafy`.`tb_bebida` (
  `id_bebida` INT(11) NOT NULL AUTO_INCREMENT,
  `tp_tipo` VARCHAR(45) NOT NULL,
  `tp_estilo` VARCHAR(45) NOT NULL,
  `vl_bebida` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id_bebida`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `idx_bebida` ON `metafy`.`tb_bebida` (`tp_estilo` ASC, `tp_tipo` ASC);


-- -----------------------------------------------------
-- Table `metafy`.`tb_consumidor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `metafy`.`tb_consumidor` ;

CREATE TABLE IF NOT EXISTS `metafy`.`tb_consumidor` (
  `id_consumidor` INT(11) NOT NULL AUTO_INCREMENT,
  `nr_telefone` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`id_consumidor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `un_nr_telefone` ON `metafy`.`tb_consumidor` (`nr_telefone` ASC);


-- -----------------------------------------------------
-- Table `metafy`.`tb_comanda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `metafy`.`tb_comanda` ;

CREATE TABLE IF NOT EXISTS `metafy`.`tb_comanda` (
  `id_comanda` INT(11) NOT NULL AUTO_INCREMENT,
  `id_consumidor` INT(11) NOT NULL,
  `dt_entrada` DATETIME NOT NULL,
  `vl_valor` DECIMAL(10,2) NULL DEFAULT NULL,
  PRIMARY KEY (`id_comanda`, `id_consumidor`),
  CONSTRAINT `fk_pedido_consumidor`
    FOREIGN KEY (`id_consumidor`)
    REFERENCES `metafy`.`tb_consumidor` (`id_consumidor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `idx_pedido_consumidor` ON `metafy`.`tb_comanda` (`id_consumidor` ASC);

CREATE INDEX `idx_dt_entrada` ON `metafy`.`tb_comanda` (`dt_entrada` DESC);


-- -----------------------------------------------------
-- Table `metafy`.`tb_pedido`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `metafy`.`tb_pedido` ;

CREATE TABLE IF NOT EXISTS `metafy`.`tb_pedido` (
  `id_pedido` INT(11) NOT NULL AUTO_INCREMENT,
  `id_comanda` INT(11) NOT NULL,
  `id_bebida` INT(11) NOT NULL,
  `id_consumidor` INT(11) NOT NULL,
  `vl_pedido` DECIMAL(10,2) NOT NULL,
  `vl_quantidade` INT(11) NOT NULL,
  PRIMARY KEY (`id_pedido`, `id_comanda`, `id_bebida`, `id_consumidor`),
  CONSTRAINT `fk_bebida_pedido`
    FOREIGN KEY (`id_bebida`)
    REFERENCES `metafy`.`tb_bebida` (`id_bebida`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comanda_pedido`
    FOREIGN KEY (`id_comanda` , `id_consumidor`)
    REFERENCES `metafy`.`tb_comanda` (`id_comanda` , `id_consumidor`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `idx_bebida_pedido` ON `metafy`.`tb_pedido` (`id_bebida` ASC);

CREATE INDEX `idx_comanda_consumidor_pedido` ON `metafy`.`tb_pedido` (`id_comanda` ASC, `id_consumidor` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
