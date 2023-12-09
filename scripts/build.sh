javac -g -d tmp/ src/*/*.java
jar cfm ./routing.jar src/MANIFEST.MF -C tmp/ . README LICENSE
rm -rf tmp/
java -jar routing.jar
