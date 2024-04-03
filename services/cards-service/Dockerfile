# FROM golang:alpine AS builder
# RUN apk update && apk add --no-cache git
# WORKDIR $GOPATH/app
# COPY . .
# RUN ls -la
# # RUN go get -d -v
# RUN go build -o /go/bin/service ./cmd/server.go
# RUN ls -la /go/bin
# FROM scratch
# COPY --from=builder /go/bin/service /go/bin/service
#
# # RUN ls -la /go/bin
# # ENTRYPOINT ["/go/bin/service"]
# CMD ["/go/bin/service"]
#
FROM golang:alpine
RUN apk update && apk add --no-cache git
WORKDIR $GOPATH/app
COPY . .
RUN go build -o /go/bin/service ./cmd/server.go
RUN ls -la /go/bin

ENTRYPOINT ["/go/bin/service"]
