
DROP TABLE IF EXISTS student;

CREATE TABLE student (
  id INT AUTO_INCREMENT PRIMARY KEY,
  rut VARCHAR(10) NOT NULL,
  name VARCHAR(50) NOT NULL,
  birth DATE NOT NULL,
  gender VARCHAR(250) DEFAULT NULL
);

CREATE UNIQUE INDEX UNIQ_RUT ON student(rut);