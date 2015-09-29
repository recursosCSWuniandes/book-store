#API Rest
-  [Introducción](#introducción)
-  [Estructuras JSON](#estructuras-JSON)
  - [Entidad Book](#entidad-book)
  - [Entidad Review](#entidad-review)
-  [Servicios para /books](#servicios-para-/books)
-  [Servicios para /books/id/authors](#servicios-para-/books/id/authors)

##Introducción
La comunicación entre cliente y servidor se realiza intercambiando objetos JSON. Para cada entidad se hace un mapeo a JSON, donde cada uno de sus atributos se transforma en una propiedad de un objeto JSON. Todos los servicios se generan en la URL /BookBasico.web/webresources/. Por defecto, todas las entidades tienen un atributo `id`, con el cual se identifica cada registro:

```javascript
{
    id: '',
    attribute_1: '',
    attribute_2: '',
    ...
    attribute_n: ''
}
```

Para los servicios de CRUD Básico, Cuando se transmite información sobre un registro específico, se realiza enviando un objeto con la estructura mencionada en la sección anterior.


##Estructuras JSON
###Objeto Json Book
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    isbn: '' /*Tipo String*/,
    image: '' /*Tipo String*/,
    description: '' /*Tipo String*/
    editorial: {id : '',
                nombre : '' } /*Tipo Object Json editorial*/
    reviews: [] /*Tipo Collection de objetos Json Review*/
}
/* Nota: en la estructura de book no hay referencia a los authors */
```
###Objeto Json Review
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
**GET**|/books|Retorna todos los objetos JSON de Book (RETRIEVE). No trae los autores de los libros. Si trae la editorial y los reviews.| ||Colección de objetos JSON Book.
**GET**|/books/id|Obtener los atributos de una instancia de Book en formato JSON(RETRIEVE). No trae la colección de autores pero si la de los reviews y la editorial.|**@PathParam id**: Identificador del registro||Objeto JSON con detalle de la instancia de Book
**POST**|/books|Crear una nueva instancia de la entidad Book (CREATE). No recibe los autores. Recibe los reviews y la referencia a la editorial.||Objeto JSON de Book a crear|Objeto JSON de Book creado
**PUT**|/books/id|Actualiza una instancia de la entidad Book (UPDATE). No recibe los autores. Recibe los reviews y la referencia a la editorial.|**@PathParam id**: Identificador del registro|Objeto JSON de Book|Objeto JSON de Book actualizado
**DELETE**|/books/id|Borra instancia de Book en el servidor (DELETE). Borra los reviews asociados con el libro.|<strong>@PathParam id</strong>: Identificador del libro.||


##Servicios para /books/id/authors
Método|URI|Acción|Parámetros|Cuerpo|Retorno
:--:|:--:|:--:|:--:|:--:|:--:
**GET**|/books/id/authors|Retorna para el libro correspondiente al id del path, la colección de autores en formato json. |**@PathParam id** Identificador del libro |Colección de objetos JSON Author.
**POST**|/books/id/authors|NO EXISTE | |
**POST**|/books/id1/authors/id2|Asocia el autor id2 en la colección de autores del book id1|**@PathParam id1** Identificador del libro y *@PathParam id2**: Identificador del autor| |
**DELETE**|/books/id1/authors/id2|ELimina la asociación del autor identificado con id2 en la colección de autores del book id1|**@PathParam id1** Identificador del libro y *@PathParam id2**: Identificador del autor|
**PUT**|/books/id/authors/|Remplaza la colección de autores del book id por una nueva colección|**@PathParam id** Identificador del libro |Colección JSON Author 


[Volver arriba](#tabla-de-contenidos)

