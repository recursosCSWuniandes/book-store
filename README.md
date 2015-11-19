# Status
[![Build Status](https://travis-ci.org/recursosCSWuniandes/book-store.svg)](https://travis-ci.org/recursosCSWuniandes/book-store)

#API Rest
-  [Introducción](#introducción)
-  [Estructuras JSON](#estructuras-JSON)
  - [Entidad Book](#entidad-book)
  - [Entidad Review](#entidad-review)
-  [Servicios para /books](#servicios-para-/books)
-  [Servicios para /books/id/authors](#servicios-para-/books/id/authors)

##Introducción
La comunicación entre cliente y servidor se realiza intercambiando objetos JSON. Para cada entidad se hace un mapeo a JSON, donde cada uno de sus atributos se transforma en una propiedad de un objeto JSON. Todos los servicios se generan en la URL /BookBasico.web/api/. Por defecto, todas las entidades tienen un atributo `id`, con el cual se identifica cada registro:

```javascript
{
    id: '',
    attribute_1: '',
    attribute_2: '',
    ...
    attribute_n: ''
}
```

##API de la aplicación BookBasico
###Entidad Book
####CRUD Básico
En la siguiente tabla se detalla los servicios REST generados para la entidad Book, la estructura del objeto que intercambian y sus respectivas funciones.

##Estructuras JSON
###Objeto Book
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    isbn: '' /*Tipo String*/,
    image: '' /*Tipo String*/,
    description: '' /*Tipo String*/
    editorial: {} /*Tipo Object*/
    reviews: [] /*Tipo Collection*/
    authors: [] /*Tipo Collection*/
}
```
###Objeto Review
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    source: '' /*Tipo String. Contiene la fuente donde se publicó la reseña del libro.*/,
    description: '' /*Tipo String. Contiene la reseña.*/
}
```
###Objeto Author
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
}
```
###Objeto Editorial
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
}
```

##Servicios para /books
Método|URI|Acción|Parámetros|Cuerpo|Retorno
:--:|:--:|:--:|:--:|:--:|:--:
**GET**|/books|Retorna todos los objetos JSON de Book (RETRIEVE). No trae ni los reviews ni los autores de los libros. Si trae la editorial.| ||Colección de objetos JSON Book.
**GET**|/books/id|Obtener los atributos de una instancia de Book en formato JSON(RETRIEVE). No trae la colección de autores pero si la de los reviews y la editorial.|**@PathParam id**: Identificador del registro||Objeto JSON con detalle de la instancia de Book
**POST**|/books|Crear una nueva instancia de la entidad Book (CREATE). No recibe los autores. Recibe los reviews y la referencia a la editorial.||Objeto JSON de Book a crear|Objeto JSON de Book creado
**PUT**|/books/id|Actualiza una instancia de la entidad Book (UPDATE). No recibe los autores. Recibe los reviews y la referencia a la editorial.|**@PathParam id**: Identificador del registro|Objeto JSON de Book|Objeto JSON de Book actualizado
**DELETE**|/books/id|Borra instancia de Book en el servidor (DELETE). Borra los reviews asociados con el libro.|<strong>@PathParam id</strong>: Identificador del libro.||


##Servicios para /books/id/authors
Método|URI|Acción|Parámetros|Cuerpo|Retorno
:--:|:--:|:--:|:--:|:--:|:--:
**GET**|/books/id/authors|Retorna la colección de autores en formato json del libro correspondiente al id del path. | |Colección de objetos JSON Author.
**POST**|/books/id/authors|NO EXISTE | |
**POST**|/books/id1/authors/id2|Asocia el autor id2 en la colección de autores del book id1|**@PathParam id2**: Identificador del autor|Objeto json con el id2 del author a asociar|
**DELETE**|/books/id1/authors/id2|ELimina la asociación del autor identificado con id2 en la colección de autores del book id1|**@PathParam id2**: Identificador del autor|Objeto json con el id2 del author a asociar|
**PUT**|/books/id/authors/|Remplaza la colección de autores del book id por una nueva colección| |


[Volver arriba](#tabla-de-contenidos)

