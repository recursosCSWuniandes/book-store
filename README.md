#Tabla de contenidos
-  [Introducción](#introducción)
-  [API](#api-de-la-aplicación-bookbasico)
  - [Entidad Book](#entidad-book)

#API Rest
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

###CRUD Básico
Para los servicios de CRUD Básico, Cuando se transmite información sobre un registro específico, se realiza enviando un objeto con la estructura mencionada en la sección anterior.
La única excepción se presenta al solicitar al servidor una lista de los registros en la base de datos, que incluye información adicional para manejar paginación de lado del servidor.

La respuesta del servidor al solicitar una colección presenta el siguiente formato:

```javascript
{
    totalRecords: 0, //cantidad de registros en la base de datos
    records: [] //collección con los datos solicitados. cada objeto tiene la estructura de la entidad.
}
```

##API de la aplicación BookBasico
###Entidad Book
####CRUD Básico
En la siguiente tabla se detalla los servicios REST generados para la entidad Book, la estructura del objeto que intercambian y sus respectivas funciones.

#####Estructura de objeto Book
```javascript
{
    id: '' /*Tipo Long*/,
    name: '' /*Tipo String*/,
    isbn: '' /*Tipo String*/,
    image: '' /*Tipo String*/,
    description: '' /*Tipo String*/
}
```
#####Servicios
Método|URI|Acción|Parámetros|Cuerpo|Retorno
:--:|:--:|:--:|:--:|:--:|:--:
**GET**|/books|Obtener todos los objetos JSON de Book (RETRIEVE)|**@QueryParam page**: página a consultar<br>**@QueryParam maxRecords**: cantidad de registros a consultar<br><br>*Si se omite alguno de estos parámetros se obtiene todos los registros en la base de datos*||Colección de objetos JSON Book y el total de registros en la base de datos en el header X-Total-Count
**GET**|/books/:id|Obtener los atributos de una instancia de Book en formato JSON(RETRIEVE)|**@PathParam id**: Identificador del registro||Objeto JSON con detalle de la instancia de Book
**POST**|/books|Crear una nueva instancia de la entidad Book (CREATE)||Objeto JSON de Book a crear|Objeto JSON de Book creado
**PUT**|/books/:id|Actualiza una instancia de la entidad Book (UPDATE)|**@PathParam id**: Identificador del registro|Objeto JSON de Book|Objeto JSON de Book actualizado
**DELETE**|/books/:id|Borra instancia de Book en el servidor (DELETE)|<strong>@PathParam id</strong>: Identificador del registro||

[Volver arriba](#tabla-de-contenidos)

