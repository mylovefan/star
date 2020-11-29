FROM 192.168.1.138:5000/anapsix/alpine-java:latest
RUN mkdir -p /application
RUN mkdir -p /application/bin
RUN mkdir -p /application/conf

WORKDIR /application

EXPOSE 18011

ARG INPUT_JAR_NAME
ARG INPUT_SB_ENV
ARG INPUT_PARAM
ENV JAR_NAME=${INPUT_JAR_NAME}
ENV SB_ENV=${INPUT_SB_ENV}
ENV SB_PARAM=${INPUT_PARAM}

ADD target/${JAR_NAME} /application/

ADD src/main/resources/* /application/conf/

ADD src/main/bin/* /application/bin/

RUN sed -i "s/\r//" bin/config.sh

RUN sed -i "s/\r//" bin/server.sh

RUN chmod +x bin/config.sh

RUN chmod +x bin/server.sh

ENTRYPOINT cd /application && ./bin/server.sh start ${JAR_NAME} ${SB_ENV} "${SB_PARAM}"
