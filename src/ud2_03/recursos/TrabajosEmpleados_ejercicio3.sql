--
-- Base de datos: `TrabajoEmpleados`
--
CREATE DATABASE IF NOT EXISTS TrabajosEmpleados;
USE TrabajosEmpleados;
--
-- Estructura de tabla para la tabla `empleados`
--
DROP TABLE IF EXISTS empleados;
CREATE TABLE `empleados` (
  `ID` int(4) AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `apellidos` varchar(70) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `sueldo` double NOT NULL,
  PRIMARY KEY (`ID`), UNIQUE(`DNI`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- DATOS TABLA empleados
--
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp1', 'ApeEmp1', '12345678A', 2000);
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp2', 'ApeEmp2', '12345678B', 1850);
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp3', 'ApeEmp3', '12345678C', 2300);
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp4', 'ApeEmp4', '12345678D', 2500);


--
-- ESCTRUCTURA TABLA trabajos
--
DROP TABLE IF EXISTS trabajos;
CREATE TABLE `trabajos` (
  `ID` int(4) AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`) 
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- DATOS TABLA trabajos
--
INSERT INTO `trabajos` (`nombre`, `descripcion`) VALUES ('T1', 'Ayuntamiento');
INSERT INTO `trabajos` (`nombre`, `descripcion`) VALUES ('T2', 'Banco');
INSERT INTO `trabajos` (`nombre`, `descripcion`) VALUES ('T13', 'EmpresaPrivada');



-- tabla de ejercicio2 `trabajosempleados`
DROP TABLE IF EXISTS trabajosempleados;
CREATE TABLE `trabajosempleados` (
  `ID` int(4) AUTO_INCREMENT,
  `IDtrabajo` int(4) NOT NULL,
  `IDempleado` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
FOREIGN KEY (`IDtrabajo`) REFERENCES trabajos (`ID`) ON DELETE CASCADE,
FOREIGN KEY (`IDempleado`) REFERENCES empleados (`ID`) ON DELETE CASCADE,
UNIQUE (`IDtrabajo`,`IDempleado`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
INSERT INTO `trabajosempleados` (`IDtrabajo`, `IDempleado`) VALUES (1, 4);
INSERT INTO `trabajosempleados` (`IDtrabajo`, `IDempleado`) VALUES (2, 1);
INSERT INTO `trabajosempleados` (`IDtrabajo`, `IDempleado`) VALUES (2, 2);
INSERT INTO `trabajosempleados` (`IDtrabajo`, `IDempleado`) VALUES (3, 1);
INSERT INTO `trabajosempleados` (`IDtrabajo`, `IDempleado`) VALUES (3, 2);
INSERT INTO `trabajosempleados` (`IDtrabajo`, `IDempleado`) VALUES (3, 3);
