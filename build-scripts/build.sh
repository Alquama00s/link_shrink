set -eux
microService=("authn" "shortner" "redirector")

for ms in "${microService[@]}"
do
echo "building $ms ---------------------------------------------"
cd $ms
rm -r build 
gradle bootJar
cd ..
done

cd linkshrink-frontend && npm run build
