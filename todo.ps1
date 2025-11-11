# Script de ayuda para ejecutar la aplicación ToDoCLI en Windows
# Uso: .\todo.ps1 add "Tarea nueva"
#      .\todo.ps1 list

$JarPath = "target\To-DoCLI-1.0-SNAPSHOT-jar-with-dependencies.jar"
$MainClass = "com.example.messytodo.Main"
$GsonJar = "lib\gson-2.10.1.jar"
$GsonUrl = "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"

# Verificar que el JAR existe
if (-not (Test-Path $JarPath)) {
    Write-Host "Compilando el proyecto..." -ForegroundColor Blue
    
    # Crear directorios necesarios
    New-Item -ItemType Directory -Force -Path "target\classes" | Out-Null
    New-Item -ItemType Directory -Force -Path "lib" | Out-Null
    
    # Descargar Gson si no existe
    if (-not (Test-Path $GsonJar)) {
        Write-Host "Descargando dependencia Gson..." -ForegroundColor Cyan
        try {
            Invoke-WebRequest -Uri $GsonUrl -OutFile $GsonJar
            Write-Host "Gson descargado correctamente" -ForegroundColor Green
        } catch {
            Write-Host "Error al descargar Gson: $_" -ForegroundColor Red
            exit 1
        }
    }
    
    # Compilar archivos Java
    Write-Host "Compilando archivos Java..." -ForegroundColor Cyan
    $sourceFiles = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName
    
    javac -d target\classes -cp $GsonJar $sourceFiles
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error al compilar el código Java" -ForegroundColor Red
        exit 1
    }
    
    # Extraer Gson dentro del directorio de clases
    Write-Host "Empaquetando dependencias..." -ForegroundColor Cyan
    Push-Location target\classes
    jar xf "..\..\$GsonJar"
    Remove-Item -Recurse -Force "META-INF" -ErrorAction SilentlyContinue
    
    # Crear JAR ejecutable
    Write-Host "Creando JAR ejecutable..." -ForegroundColor Cyan
    jar cfe "..\To-DoCLI-1.0-SNAPSHOT-jar-with-dependencies.jar" $MainClass .
    
    Pop-Location
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error al crear el JAR" -ForegroundColor Red
        exit 1
    }
    
    Write-Host "Build completado exitosamente!" -ForegroundColor Green
}

# Ejecutar el comando pasando todos los argumentos
java -cp $JarPath $MainClass $args
