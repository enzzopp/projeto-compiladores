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
