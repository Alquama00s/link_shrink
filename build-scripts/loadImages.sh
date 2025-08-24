set -eux
minikube image load alquama00s/linkshrink/authn/db
minikube image load alquama00s/linkshrink/redirector/db
minikube image load alquama00s/linkshrink/frontend
minikube image load alquama00s/linkshrink/shortner
minikube image load alquama00s/linkshrink/redirector
minikube image load alquama00s/linkshrink/authn
