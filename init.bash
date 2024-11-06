javac *.java

case "$1" in
    -j)
        java Main -j
        cd resources/
        javac JavaTranslate.java
        java JavaTranslate
        ;;
    -c)
        java Main -c
        
        ;;
    *)
        echo "Opção inválida, use -j para executar o programa em Java ou -c para executar o programa em C"
        ;;
esac
