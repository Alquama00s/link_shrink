set -eux
kubectl delete -f secrets/postgres-secret.yml
kubectl delete -f configs/postgres-config.yml
kubectl apply -f secrets/postgres-secret.yml
kubectl apply -f configs/postgres-config.yml