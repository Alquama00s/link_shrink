set -eux
kubectl apply -f volumes/volumes/postgres-pv.yml
kubectl apply -f volumes/linkshrink-db-pvc.yml 
