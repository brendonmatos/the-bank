# create databases
CREATE DATABASE IF NOT EXISTS `clientes`;
CREATE DATABASE IF NOT EXISTS `auth`;
CREATE DATABASE IF NOT EXISTS `gerentes`;
CREATE DATABASE IF NOT EXISTS `contas`;
-- # create root user and grant rights
-- CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
-- GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root'; flush privileges;
SET global log_output = 'FILE'; 
SET global general_log_file='/var/log/mysql/all.log'; 
SET global general_log = 1;