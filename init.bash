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
esac





# java *.java

# case "$1" in
#     -j) 
#         rm -f resources/JavaTranslate.java
#         rm -f resources/JavaTranslate.class
#         java Main -j
#         cd resources/

#         if [ -f "JavaTranslate.java" ]; then
#             javac JavaTranslate.java
#             java JavaTranslate
#         fi
#         ;;
#     -c)
#         rm -f resources/CTranslate.c
#         rm -f resources/CTranslate.class
#         java Main -c
#         cd resources/

#         if [ -f "CTranslate.c" ]; then
#             javac CTranslate.java
#             java CTranslate
#         fi
#         ;;
#     *)
#         echo "Opção inválida, use -j para executar o programa em Java ou -c para executar o programa em C"
#         ;;
# esac

