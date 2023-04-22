package infra

type Bus interface {
	SendEvent(eventName string, payload interface{}) error
	Subscribe(eventName string, str interface{}, handler func(payload interface{}) error) error
}
