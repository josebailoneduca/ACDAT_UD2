--
-- Base de datos: `TrabajoEmpleados`
--
Create database IF NOT EXIST TrabajosEmpleados;
USE TrabajosEmpleados;
--
-- Estructura de tabla para la tabla `empleados`
--
CREATE TABLE `empleados` (
  `ID` int(4) AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `apellidos` varchar(70) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `sueldo` double NOT NULL,
  PRIMARY KEY (`ID`), UNIQUE(`DNI`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- DATOS
--
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp1', 'ApeEmp1', '12345678A', 2000);
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp2', 'ApeEmp2', '12345678B', 1850);
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp3', 'ApeEmp3', '12345678C', 2300);
INSERT INTO `empleados` (`nombre`, `apellidos`, `DNI`, `sueldo`) VALUES
('NomEmp4', 'ApeEmp4', '12345678D', 2500);


