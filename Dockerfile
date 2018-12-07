FROM quay.io/ukhomeofficedigital/openjdk11


ENV USER user_hocs_info_service
ENV USER_ID 1000
ENV GROUP group_hocs_info_service
ENV NAME hocs-info-service
ENV JAR_PATH build/libs

RUN yum update -y glibc && \
    yum update -y nss && \
    yum update -y bind-license

WORKDIR /app

RUN groupadd -r ${GROUP} && \
    useradd -r -u ${USER_ID} -g ${GROUP} ${USER} -d /app && \
    mkdir -p /app && \
    chown -R ${USER}:${GROUP} /app

COPY ${JAR_PATH}/${NAME}*.jar /app

ADD scripts /app/scripts

RUN chmod a+x /app/scripts/*

EXPOSE 8000

USER ${USER_ID}

CMD /app/scripts/run.sh
