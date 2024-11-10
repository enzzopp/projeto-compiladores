find $pwd -type f -name "*.class" -exec rm -f {} \;
find $pwd -type f -name CppTranslate -exec rm -f {} \;
find $pwd -type f -name JavaTranslate.java -exec rm -f {} \;
find $pwd -type f -name CppTranslate.cpp -exec rm -f {} \;

javac *.java

# verificar se as duas flags foram passadas
if [ "$#" -ne 2 ]; then
    echo "Número de argumentos inválido, use -j para executar o programa em Java ou -c para executar o programa em C++, no segundo argumento passe o arquivo de entrada"
    echo "Exemplo: ./compile.bash -j resources/input.txt"
    exit 1
fi



case "$1" in
    -j)
        java Main $1 $2
        cd resources/
        javac JavaTranslate.java
        java JavaTranslate
        ;;
    -c)
        java Main $1 $2
        cd resources/
        g++ CppTranslate.cpp -o CppTranslate
        ./CppTranslate
        ;;
    *)
        echo "Opção inválida, use -j para executar o programa em Java ou -c para executar o programa em C++"
        ;;
esac