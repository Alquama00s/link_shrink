set -eux
kubectl apply -f database/
kubectl apply -f backend/
kubectl apply -f frontend/