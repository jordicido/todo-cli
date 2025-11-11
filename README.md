# Messy ToDo CLI (Java)

Esta es una aplicación CLI de tareas en Java deliberadamente pequeña y ligeramente desordenada para practicar refactorización.

## Features

- ✅ **JSON Persistence** - Tareas se guardan automáticamente en `todos.json`
- ✅ **CRUD Operations** - Add, List, Complete, Remove
- ✅ **Simple CLI** - Fácil de usar desde la terminal

## Run Examples

### Usando el script

```bash
# Añadir tarea
./todo.sh add "Comprar leche"

# Listar todas las tareas
./todo.sh list

# Completar tarea
./todo.sh complete 1

# Eliminar tarea
./todo.sh remove 1
```

### Comandos disponibles:
- `add <texto>` (alias: `a`, `new`) - Añadir nueva tarea
- `list` (alias: `ls`, `show`) - Listar todas las tareas
- `complete <id>` (alias: `done`, `finish`) - Marcar como completada
- `remove <id>` (alias: `delete`, `rm`) - Eliminar tarea

## 🐛 Code Smells Presentes en `MessyTodoApp.java`

Este archivo contiene **intencionalmente** los siguientes problemas:

### 1. **Método Gigante (Long Method)**
   - El método `run()` tiene más de 200 líneas
   - Hace demasiadas cosas: parsing, validación, lógica de negocio, presentación

### 2. **Código Duplicado (Duplicate Code)**
   - Texto de ayuda duplicado en múltiples lugares
   - Validación de argumentos repetida en complete y remove
   - Parsing de IDs duplicado
   - Mensajes de error similares copy-pasted

### 3. **Switch Statement Gigante**
   - Podría usar Command Pattern o Strategy Pattern
   - Difícil de mantener y extender

### 4. **Variables y Métodos No Utilizados (Dead Code)**
   - `counter` - variable que no se usa
   - `lastCommand` - almacena pero nunca consulta
   - `MAGIC_NUMBER` - constante sin propósito
   - `printHelp()` - método nunca llamado
   - `getCounter()` - getter inútil
   - Import de `Parser` sin usar

### 5. **Separación de Responsabilidades (SRP Violation)**
   - Mezcla parsing de comandos
   - Mezcla validación
   - Mezcla lógica de negocio
   - Mezcla presentación/formato de salida

### 6. **Magic Numbers y Strings**
   - Números mágicos: `42`, `0`, `1`, `2`
   - Strings duplicados sin constantes
   - Caracteres especiales hardcoded: `"✓"`, `"✗"`, `"[ ]"`, `"[x]"`

### 7. **Manejo de Excepciones Pobre**
   - Catch genérico de `Exception`
   - `printStackTrace()` en código de producción
   - Mensajes de error inconsistentes
   - Try-catch duplicados

### 8. **Lógica de Presentación Mezclada**
   - `System.out.println` por todas partes
   - Formato de salida embebido en la lógica
   - Sin separación entre UI y lógica

### 9. **Validaciones Redundantes**
   - Aún hay validaciones repetidas en parsing de IDs y comprobaciones de negativos
   - (El ejemplo anterior de `txt.isEmpty() || txt.equals("") || txt.length()==0` se simplificó a `txt.isEmpty()`) 

### 10. **Nombres de Variables Poco Descriptivos**
   - `txt`, `idx`, `numericId`, `id`, `id2`
   - Variables con nombres inconsistentes

### 11. **Comentarios Inútiles**
   - Aún quedan algunos comentarios sin valor semántico
   - Se han eliminado los más obvios, pero persisten suficientes para practicar limpieza

### 13. **Artificial Complexity / Trabajo Inútil**
   - Conversión innecesaria `Integer.parseInt("42")` para comparar con `MAGIC_NUMBER`
   - `@SuppressWarnings` usados para ocultar problemas en lugar de resolverlos
   - Ejemplo de comparación sin propósito real

### 12. **Feature Envy**
   - Código que accede repetidamente a métodos de otros objetos
   - El método list() hace consultas múltiples al servicio

## 🎓 Ejercicios de Refactorización Sugeridos

### Nivel Básico:
1. Eliminar código muerto (variables y métodos no usados)
2. Extraer constantes para strings y números mágicos
3. Renombrar variables con nombres descriptivos
4. Eliminar código duplicado

### Nivel Intermedio:
5. Extraer métodos pequeños del método `run()` gigante
6. Crear clases para formateo de salida (Formatter/Printer)
7. Implementar Command Pattern para los comandos
8. Mejorar el manejo de excepciones

### Nivel Avanzado:
9. Aplicar Single Responsibility Principle
10. Crear una capa de UI separada
11. Implementar validadores reutilizables
12. Añadir tests unitarios con JUnit
13. Aplicar Dependency Injection

## 📚 Recursos Recomendados

- **Clean Code** - Robert C. Martin
- **Refactoring** - Martin Fowler
- **Code Smells Catalog** - refactoring.guru

## ✅ Buenas Prácticas Ya Implementadas

A pesar del desorden, el proyecto SÍ tiene algunas cosas bien hechas:

- ✅ Persistencia con JSON funcional
- ✅ Separación en capas (model, repo, service)
- ✅ Uso de interfaces (`TodoRepository`)
- ✅ Build automatizado con Maven
- ✅ Script helper para facilitar ejecución

---

**¡Buena suerte refactorizando! 🚀**
