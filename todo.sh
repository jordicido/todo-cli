#!/bin/bash
# Script de ayuda para ejecutar la aplicación ToDoCLI

# Colores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # Sin color

JAR_PATH="target/To-DoCLI-1.0-SNAPSHOT-jar-with-dependencies.jar"
MAIN_CLASS="com.example.messytodo.Main"

# Verificar que el JAR existe
if [ ! -f "$JAR_PATH" ]; then
    echo -e "${BLUE}Compilando el proyecto...${NC}"
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo "Error al compilar el proyecto"
        exit 1
    fi
fi

# Ejecutar el comando
java -cp "$JAR_PATH" "$MAIN_CLASS" "$@"
