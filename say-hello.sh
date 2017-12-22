
for i in {1..100}
do
  http localhost:$1/api/hello/world-$i --verbose
done
