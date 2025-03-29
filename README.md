<h1>Desafío 2 - App de Registro de Estudiantes - DSM Laboratorio</h1>
# Integrantes:
Jacqueline Alexandra Chavez Ramírez, CR200574
Mauricio Alexander Pérez Salinas, PS212544


## Descripción

Este proyecto es una aplicación Android desarrollada en Kotlin que utiliza la Autentificación  correo y  y contraseña para la parte de login y la parte de  registro  asi como para gestionar registros utiliza Firebase Realtime Database  donde se gestionan los registros de alumnos como sus datos de grado y notas de materias . La aplicación permite agregar, editar y eliminar registros de estudiantes, y mostrar una lista de todas los estudiantes  en la base de datos  dicha lista se mostrara dependiendo el maestro que inicie sesión ya que se mostrarán listados de estudiantes por maestro en común.

## Funcionalidades

- **Agregar**: Permite añadir nuevos registros de alumnos con campos como nombre, apellido, grado, materia, nota final y usurID.
- **Editar**: Permite actualizar los registros existentes.
- **Eliminar**: Permite eliminar registros de alumnos.
- **Mostrar**: Muestra una lista de alumnos con sus datos.

## Tecnologías Utilizadas

- **Android SDK**: Plataforma para el desarrollo de aplicaciones Android.
- **Kotlin**: Lenguaje de programación utilizado para desarrollar la aplicación.
- **Firebase Realtime Database**: Servicio de base de datos en tiempo real proporcionado por Firebase para almacenar y sincronizar datos.
- **Firebase Autentificación**: Servicio de autentificación de usuarios para aplicaciones web y móviles.

## Estructura del Proyecto

### Clases Principales

Estas son algunas de las clases o actividades mas principales  del proyecto

1. **`Registro`**: Clase de modelo que representa a un estudiante. Contiene los campos `nombre`, `apellido`, `Grado`, `materia`, `Nota final`, `userID` y un método para convertir el objeto a un mapa para su almacenamiento en Firebase.

2. **`LoginActivity`**: Actividad principal que permite el inicio de sesión de los profesores(usuarios).

3. **`Registro_de_notas_de_estudiantesActivity`**: Actividad que permite  agregar registros de alumnos.

4. **`VerRegistros`**: Actividad que permite mostrar los registros de alumnos.

## Configuración del Proyecto

1. **Configuración de Firebase**:
    - Crea un proyecto en [Firebase Console](https://console.firebase.google.com/).
    - Añade tu aplicación Android al proyecto y descarga el archivo `google-services.json`.
    - Coloca el archivo `google-services.json` en el directorio `app/` de tu proyecto Android.
    - Habilita Firebase Realtime Database en tu consola de Firebase y configura las reglas de acceso según sea necesario.

2. **Dependencias de Gradle**:
    - Asegúrate de añadir las siguientes dependencias en tu archivo `build.gradle` (nivel de aplicación):

      ```groovy
      implementation 'com.google.firebase:firebase-database:21.0.3'
      implementation 'com.google.firebase:firebase-core:21.0.0'
      implementation 'com.google.android.material:material:1.8.0'
      implementation 'com.google.firebase:firebase-auth:22.0.0'

      ```

    - En el archivo `build.gradle` (nivel de proyecto), añade el siguiente plugin:

      ```groovy
      classpath 'com.google.gms:google-services:4.3.15'
      ```

    - Asegúrate de aplicar el plugin de Google Services en el archivo `build.gradle` (nivel de aplicación):

      ```groovy
      apply plugin: 'com.google.gms.google-services'
      ```

## Uso

1. **Ejecuta la Aplicación**: Compila y ejecuta la aplicación en un emulador o dispositivo físico Android.
2. **Inicia sesion(Login)**:Registrate si no estas registrado en la apliacion si ese es  no es el caso inicia sesion
3. **Ver alumnos(registros)**:Permite visualizar el listado de alumnos por profesor que inicie sesion
4. **Agregar un alumno**: Haz clic en el botón flotante  + para agregar un nuevo alumno. Completa los campos y guarda el registro.
5. **Editar una alumno**: Haz clic en  el icono de flecha del alumno deseado para editar el registro. Modifica los campos y guarda los cambios.
6. **Eliminar un alumno**: Haz clic en la opción eliminar estudiante que esta en la zona peligro de bajo de editar alumno para eliminar el registro deberas confirmar que deseas eliminar el registro.

## Registro
<img src="https://github.com/jacquelinechavez30/Desafio-2-DSM-2025/blob/main/img/registro.png" alt="Registro de la aplicacion" width="300" height="600">

## Login
<img src="https://github.com/jacquelinechavez30/Desafio-2-DSM-2025/blob/main/img/login.png" alt="Login de la aplicacion" width="300" height="600">

## Registro_de_notas_de_estudiantes
<img src="https://github.com/jacquelinechavez30/Desafio-2-DSM-2025/blob/main/img/registro%20estudiantes.png" alt="Registro_de_notas_de_estudiantes" width="300" height="600">

## Ver registro de estudiantes
<img src="https://github.com/jacquelinechavez30/Desafio-2-DSM-2025/blob/main/img/VerRegistros.png" alt="Ver registro de estudiantes" width="300" height="600">

## Editar estudiante o detalles estudiante
<img src="https://github.com/jacquelinechavez30/Desafio-2-DSM-2025/blob/main/img/detallesoedicion%20y%20eliminacion.png" alt="Editar estudiantes" width="300" height="600">

## Eliminar estudiante
<img src="https://github.com/jacquelinechavez30/Desafio-2-DSM-2025/blob/main/img/Eliminacion.png" alt="Eliminar estudiante" width="300" height="600">

## Confirmar eliminar estudiante 
<img src="https://github.com/jacquelinechavez30/Desafio-2-DSM-2025/blob/main/img/confirmacioneliminacion.png" alt="Confirmar eliminar estudiante " width="300" height="600">


