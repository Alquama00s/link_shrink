# Kubernetes manifests

This folder contains Kubernetes manifests and helper scripts to deploy LinkShrink components on a Kubernetes cluster.

Structure (high level)

- k8s/backend — deployment/manifests for backend microservices (shortner, redirector, authn, gateway)
- k8s/frontend — manifests for the frontend and ingress
- k8s/database — database deployments / persistent volumes / initialization scripts

Quick deploy (example)

```bash
# make sure your image artifacts are available to the cluster (local registry or push to remote)
kubectl apply -f k8s/database
kubectl apply -f k8s/backend
kubectl apply -f k8s/frontend
```

Notes

- Some manifests expect images available by name — if using Minikube, you can use `minikube image load <image>` to load local images into the cluster.
- Review environment variables and secrets before deploying to a shared environment.
