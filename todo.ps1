# Script de ayuda para ejecutar la aplicación ToDoCLI en Windows
# Uso: .\todo.ps1 add "Tarea nueva"
#      .\todo.ps1 list

$JarPath = "target\To-DoCLI-1.0-SNAPSHOT-jar-with-dependencies.jar"
$MainClass = "com.example.messytodo.Main"

# Verificar que el JAR existe
if (-not (Test-Path $JarPath)) {
    Write-Host "Compilando el proyecto..." -ForegroundColor Blue
    
    # Intentar compilar con Maven
    mvn clean package -DskipTests
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error al compilar el proyecto" -ForegroundColor Red
        exit 1
    }
}

# Ejecutar el comando pasando todos los argumentos
java -cp $JarPath $MainClass $args
