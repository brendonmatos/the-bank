package main

import (
	"net/http"

	"github.com/brendonmatos/the-bank/services/gerentes-service-go/infra"
	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()

	grm := infra.NewGerenteRepositoryMysql()

	s := service.NewGerenteService(grm)

	r.GET("/", func(c *gin.Context) {
		gs, err := s.GetAllGerentes()

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"items": gs,
		})
	})
	r.Run() // listen and serve on 0.0.0.0:8080 (for windows "localhost:8080")
}
