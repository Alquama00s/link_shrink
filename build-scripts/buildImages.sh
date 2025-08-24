set -eux
docker build -t alquama00s/linkshrink/db:$1 -t alquama00s/linkshrink/db:latest -f Docker/database/Dockerfile .
docker build -t alquama00s/linkshrink/frontend:$1 -t alquama00s/linkshrink/frontend:latest -f Docker/frontend/Dockerfile .
docker build -t alquama00s/linkshrink/shortner:$1 -t alquama00s/linkshrink/shortner:latest -f shortner/Dockerfile shortner
docker build -t alquama00s/linkshrink/redirector:$1 -t alquama00s/linkshrink/redirector:latest -f redirector/Dockerfile redirector
docker build -t alquama00s/linkshrink/authn:$1 -t alquama00s/linkshrink/authn:latest -f authn/Dockerfile authn
