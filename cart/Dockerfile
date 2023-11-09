FROM amazoncorretto:17-alpine as corretto-jdk
LABEL maintainer="tristan.hersent@epita.fr"

RUN apk add --no-cache binutils

RUN jlink \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /jre

FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

ENV APP_DEBUG_PORT=12355
ENV JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:${APP_DEBUG_PORT}"


COPY --from=corretto-jdk /jre $JAVA_HOME

ENV APPLICATION=cart \
    USER=tristan \
    RUN_SH=run.sh

RUN adduser -s /bin/bash -h /opt/${APPLICATION} -D ${USER} && \
    mkdir -p /opt/${APPLICATION} && \
    chown ${USER}:${USER} /opt/${APPLICATION} && \
    apk add -u --no-cache bash

COPY --chown=${USER}:${USER} src/main/resources/docker/${RUN_SH} /opt/${APPLICATION}/${RUN_SH}

RUN dos2unix /opt/${APPLICATION}/${RUN_SH}

COPY --chown=${USER}:${USER} target/${APPLICATION}.jar /opt/${APPLICATION}/app.jar

USER ${USER}

WORKDIR /opt/${APPLICATION}

CMD /bin/bash /opt/${APPLICATION}/${RUN_SH}