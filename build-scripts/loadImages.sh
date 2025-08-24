set -eux
minikube image load alquama00s/linkshrink/authndb:$1
minikube image load alquama00s/linkshrink/redirectordb:$1
minikube image load alquama00s/linkshrink/frontend:$1
minikube image load alquama00s/linkshrink/shortner:$1
minikube image load alquama00s/linkshrink/redirector:$1
minikube image load alquama00s/linkshrink/authn:$1
