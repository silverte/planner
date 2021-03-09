ARG DOCKER_REGISTRY
FROM ${DOCKER_REGISTRY}/infra/centos:centos7.6.1810.20191115
#FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.sktelecom.swingmsa.mcatalog.context.PlannerApplication"]
ENTRYPOINT java -javaagent:`ls /infsw/whatap/whatap.agent.tracer-*.jar | sort | tail -1` -Dwhatap.name=$HOSTNAME -Dwhatap.okind=mctl-planner $JAVAOPTS -Dreactor.netty.http.server.accessLogEnabled=true -Xloggc:/applog/gclog/mctl-planner/gc_`date "+%Y%m%d-%H%M%S"`.log -cp app:app/lib/* com.sktelecom.swingmsa.mcatalog.context.PlannerApplication