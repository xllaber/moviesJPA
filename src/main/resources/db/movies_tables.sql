/*!40101 SET NAMES utf8 */;

-- Volcando estructura de base de datos para cine
DROP DATABASE IF EXISTS movies;
CREATE DATABASE movies /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci */;
USE movies;

-- directors definition
CREATE TABLE directors (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  birthYear int(4) DEFAULT NULL,
  deathYear int(4) DEFAULT NULL,
  PRIMARY KEY (id)
) AUTO_INCREMENT=1;

-- imdb.actors definition
CREATE TABLE actors (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  birthYear int(4) DEFAULT NULL,
  deathYear int(4) DEFAULT NULL,
  PRIMARY KEY (id)
) AUTO_INCREMENT=1;

-- movies definition
CREATE TABLE movies (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(57) DEFAULT NULL,
  year int(4) DEFAULT NULL,
  image varchar(161) DEFAULT NULL,
  runtime int(4) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  director_id int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT movies_FK FOREIGN KEY (director_id) REFERENCES directors (id) ON DELETE SET NULL ON UPDATE CASCADE
) AUTO_INCREMENT=1;

-- imdb.actors_movies definition
CREATE TABLE actors_movies (
  id int(11) NOT NULL AUTO_INCREMENT,
  movie_id int(11) NOT NULL,
  actor_id int(11) NOT NULL,
  characters varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT actors_movies_FK FOREIGN KEY (actor_id) REFERENCES actors (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT actors_movies_FK2 FOREIGN KEY (movie_id) REFERENCES movies (id) ON DELETE CASCADE ON UPDATE CASCADE
)AUTO_INCREMENT=1;
