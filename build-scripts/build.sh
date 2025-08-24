set -eux
microService=("authn" "shortner" "redirector")

for ms in "${microService[@]}"
do
echo "building $ms ---------------------------------------------"
cd $ms
if test -f build; then
  rm -r build
fi
gradle bootJar
cd ..
done

cd linkshrink-frontend && npm run build
