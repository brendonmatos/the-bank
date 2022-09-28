package infra

import "os"

var (
	MYSQL_ROOT_PASSWORD = os.Getenv("MYSQL_ROOT_PASSWORD")
	MYSQL_ROOT_USER     = os.Getenv("MYSQL_ROOT_USER")
	MYSQL_DATABASE_HOST = os.Getenv("MYSQL_DATABASE_HOST")
	GORM_DSN            = os.Getenv("GORM_DSN")
)
