#!/bin/sh

CMD="java -Dfile.encoding=UTF-8 -Duser.timezone=UTC -XX:+UseG1GC"

if [ -n "$MARIADB_URI" ] ;      then CMD="$CMD -Dspring.datasource.url=$MARIADB_URI" ;           fi
if [ -n "$MARIADB_USERNAME" ] ; then CMD="$CMD -Dspring.datasource.username=$MARIADB_USERNAME" ; fi
if [ -n "$MARIADB_PASSWORD" ] ; then CMD="$CMD -Dspring.datasource.password=$MARIADB_PASSWORD" ; fi
if [ -n "$JWT_SECRET" ] ;       then CMD="$CMD -Dtaskbook.jwt.secret=$JWT_SECRET" ;              fi
if [ -n "$JWT_LIFETIME" ] ;     then CMD="$CMD -Dtaskbook.jwt.lifetime=$JWT_LIFETIME" ;          fi

CMD="$CMD -jar /taskbook/taskbook.jar"

exec $CMD
