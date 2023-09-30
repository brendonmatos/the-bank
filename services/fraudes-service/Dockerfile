FROM python:alpine3.18

ENV PYTHONUNBUFFERED 1

# RUN apt-get update \
#     && apt-get upgrade -y \
#     && apt-get install python3-dev default-libmysqlclient-dev build-essential

RUN apk update \
    && apk add --virtual build-deps gcc python3-dev musl-dev \
    && apk add --no-cache mariadb-dev
    
WORKDIR /app

COPY . .

RUN pip3 install -r requirements.txt