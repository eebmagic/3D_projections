echo "COMPILING:"
javac --module-path ./dependancies/javafx-sdk-11.0.2/lib/ --add-modules=javafx.controls Program.java
echo "\n\nRUNNING:"
java --module-path ./dependancies/javafx-sdk-11.0.2/lib/ --add-modules=javafx.controls Program
