set -eux
kind load docker-image alquama00s/linkshrink/db --name $1
kind load docker-image alquama00s/linkshrink/frontend --name $1
kind load docker-image alquama00s/linkshrink/shortner --name $1
kind load docker-image alquama00s/linkshrink/redirector --name $1
kind load docker-image alquama00s/linkshrink/authn --name $1
