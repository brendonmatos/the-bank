package infra

import (
	"context"
	"fmt"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
	"github.com/gin-gonic/gin"
)

func MakeHttpServer(
	port string,
	s service.Service,
) error {
	r := gin.Default()
	r.GET("/", func(c *gin.Context) {
		gs, err := s.GetAllGerentes()

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, gs)
	})

	r.GET("/:cpf", func(c *gin.Context) {
		g, err := s.GetGerenteByCpf(c.GetString("cpf"))

		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}
		c.JSON(http.StatusOK, g)
	})

	r.PUT("/:cpf", func(c *gin.Context) {
		var g service.Gerente
		if err := c.ShouldBindJSON(&g); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		g.Cpf = c.GetString("cpf")

		_, err := s.UpdateGerente(g)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, g)
	})

	r.POST("/", func(c *gin.Context) {
		var g service.Gerente
		if err := c.ShouldBindJSON(&g); err != nil {
			c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		_, err := s.AddGerente(g)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}

		c.JSON(http.StatusOK, gin.H{
			"message": "Gerente inserido com sucesso",
		})
	})

	r.GET("/health", func(c *gin.Context) {
		c.Data(http.StatusOK, "text/plain", []byte("UP"))
	})

	srv := &http.Server{
		Addr:    port,
		Handler: r,
	}

	go func() {
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			panic(err)
		}
	}()

	fmt.Println("Server started on port", port)

	// https://gin-gonic.com/docs/examples/graceful-restart-or-stop/
	quit := make(chan os.Signal)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	if err := srv.Shutdown(ctx); err != nil {
		return fmt.Errorf("server shutdown: %v", err)
	}

	return nil
}
