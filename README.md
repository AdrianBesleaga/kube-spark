Apache Spark with Kubernetes
============================

Code snippets and notes on running Apache Spark with Kubernetes.
Focus on running Spark Core and Spark SQL application with Kubernetes on S3 object storage.

## Spark SQL
Hive Metastore is required to manage table schema and query data in S3 via Spark SQl.

### Deploy Postgresql
We use Postgresql for Hive Metastore backend database. Follow [this Postgres Operator](https://github.com/zalando/postgres-operator)'s instruction to deploy a Postgresql service on Kubernetes.

Deploy a Postgresql with the Postgres Operator.
```
kubectl create -f reddot-cluster.yaml
```
This will create a Postgresql service, along with a `metastore` database, `hive` user and its secret.

[Decode the secret](https://kubernetes.io/docs/concepts/configuration/secret/#decoding-a-secret) to restrieve hive user's password.

At this point, we should be able to connect to the `metastore` database.
```
psql -h <Postgres_pod_IP> -U hive -W metastore
```

### Deploy Hive Metastore
Initialize metastore schema and deploy the Hive Metastore service.


Credits:
Some code are based on [Joshua Robison](https://github.com/joshuarobinson)'s awesome work.
