# Readme

En este documento se indicarán motivos de las decisiones tomadas en la arquitectura y diseño del proyecto, asi como alguna de las librerías que se han utilizado.


## Patrones de diseño

El patrón de diseño que he optado a seguir es el Model View ViewModel, debido a que es el recomendado por el propio Google para el desarollo de aplicaciones en entornos Android, además del más usado a nivel profesional por la mayoria de las empresas a día de hoy.

## Librerías importantes
Para el desarrollo de la app he utilizado muchas de las librerias de androidx, tales como constraints, viewpager, navigation, material etc.. 
Para el tratamiento de red he usado Retrofit
En lo referente a inyección de dependencias, he optado por usar Koin, debido a su simpleza y fácil lectura, además de estar muy bien integrado con Kotlin y el modelo MVVM.
Para las imagenes obtenidas de internet, he usado la conocida librería de "Picasso"

## Estructura de carpetas

El proyecto se compone principalmente de tres paquetes: setup, features y models.

 1. Setup: En esta carpeta, subdividada a su vez en otras (di, extensions, network) engloba todas las clases que no estad adheridas directamente a ninguna característica/vista concreta de la app. Dentro de la carpeta "di" encontramos todos los archivos dedicados a los modulos para la inyección de dependencias. En la carpeta extensions, algunas extensiones utiles, y por ultimo en network, toda la capa de red.
 2. Features: En esta carpeta tenemos las diferentes caracteristicas/vistas de la app, separadas por funcionalidad, en el caso de este proyecto tenemos: favorites, beerStylesList, beerList y beerDetail. Dentro de cada una de estas subcarpetas, encontrariamos las clases referentes a dicha "feature", los fragments, viewModels o adapter que se usen.
 3. Models: Como su nombre indica, aquí estarían todas las clases "modelo" de la app.

Esta estructura de proyecto permite tener localizadas todas las vistas de una app por su funcionalidad, y encontrar con facilidad las clases usadas en la misma, permitiendo una intuitiva navegación por el proyecto.
