FROM mysql:8.0.33
WORKDIR /app
# COPY truncate-all.sh .
# COPY init-logs.sh .

RUN mkdir -p /var/log/mysql
RUN touch /var/log/mysql/all.log
RUN chmod 777 /var/log/mysql/all.log

# RUN ./init-logs.sh
