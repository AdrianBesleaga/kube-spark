ARG SPARK_IMAGE=gcr.io/spark-operator/spark:v2.4.5

FROM ${SPARK_IMAGE}

# Add s3a and its dependencies


ENTRYPOINT ["tail", "-f", "/dev/null"]
