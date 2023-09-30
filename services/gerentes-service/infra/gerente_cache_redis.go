package infra

import (
	"fmt"

	"github.com/redis/go-redis/v9"
	"github.com/brendonmatos/the-bank/services/gerentes-service/service"
)

type GerenteCacheRedis struct {
	rdb *redis.Client
	ctx context.Context
}

func (r *GerenteCacheRedis) GetByCpf(cpf string) (*service.Gerente, error) {

	val, err := rdb.Get(r.ctx, "gerente:"+cpf).Result()
	if err != nil {
		return nil, err
	}

	return val.(*service.Gerente), nil
}

func (r *GerenteCacheRedis) SetByCpf(cpf string, gerente *service.Gerente) error {

	err := rdb.Set(ctx, "gerente:"+cpf, gerente, 0).Err()
	if err != nil {
		return err
	}

	return nil
}

func NewGerenteCacheRedis(
	addr string,
	password string,
) service.GerenteCache {
	
	var ctx = context.Background()

	rdb := redis.NewClient(&redis.Options{
		Addr:     addr,
		Password: password, // no password set
		DB:       0,  // use default DB
	})

	return &GerenteCacheRedis{
		ctx: ctx,
		rdb: rdb,
	}
}
