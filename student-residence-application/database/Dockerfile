FROM mysql:5.7.24 as builder

RUN ["sed", "-i", "s/exec \"$@\"/echo \"not running $@\"/", "/usr/local/bin/docker-entrypoint.sh"]

ENV MYSQL_ROOT_PASSWORD=root
 
COPY ./appointment_db.sql /docker-entrypoint-initdb.d/
COPY ./authentication_db.sql /docker-entrypoint-initdb.d/
COPY ./contract_db.sql /docker-entrypoint-initdb.d/

RUN ["/usr/local/bin/docker-entrypoint.sh", "mysqld", "--datadir", "/initialized-db"]

FROM mysql:5.7.24

COPY --from=builder /initialized-db /var/lib/mysql
ADD mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf

EXPOSE 3306:3306