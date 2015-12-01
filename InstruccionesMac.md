#Funcionamiento en Mac
-  [introducción](#introduccion)
-  [Tipos de variables de entorno](#tipos-de-variables-de-entorno)
-  [Poniendo las variables en MacOSx 10.7 o inferior](#poniendo-las-variables-en-macosx-107-o-inferior)
-  [Poniendo las variables en MacOSx 10.8 o superior](#poniendo-las-variables-en-macosx-108-o-superior)

##Introducción
Se considerá una buena practica el usar variables de entorno en las partes de todo software en donde se tengan que utilizar datos privados, como los de autenticación. Las variables de entorno permiten mantener los datos ocultos en repositorios publicos, de modo que solo las personas que los conozcan puedan usar sus credenciales (o cambiarlas por unas propias) para aprovechar el funcionamiento del sistema con sus propias cuentas.

El problema con el sistema MacOSx radica en que las variables deben definirse según el tipo de proceso en donde deben utilizarse, es decir que pueden crearse variables que solamente pueden ejecutarse desde el Terminal o que solo aplican para ciertos usuarios del sistema. Por lo anterior, es necesario entender los tipos de variables de entorno que pueden definirse en Mac y cuales aplican para el sistema que este siendo utilizado.

##Tipos de variables de entorno
En Mac existen distintos lugares en donde se pueden guardar las variables de entorno, en base al lugar escogido se podran usar en uno u otro proceso del ambiente de desarrollo. A continuación se explican los posibles lugares en donde deben guardarse las variables para un sistema Mac OSx.

Ubicación  | Descripción
---------- | -------------
~/.profile:   | Las variables definidas aquí se usaran en todos los procesos iniciados en Terminal.app.
~/.bashrc:    | Estas variables son invocadas desde todo shell que no requiera login.
/etc/profile: | Estas variables se cargan antes del archivo ~/.profile. Se deben definir aquí cuando las variables deben aplicarse a los programas lanzados por todos los usuarios del sistema desde el terminal.
~/.MacOSX/environment.plist: | Estas variables son cargadas por loginwindow al momento de hacer login en el sistema. Las variables aquí definidas aplican para todas las aplicaciones, incluyendo las que se ejecutan con GUI. Cuandos se asignan variables en este archivo, se debe reiniciar el sistema para que tengan efecto. Este archivo ya no se soporta a partir de MacOS X 10.8.
Las variables de launchd: | Estas variables son definidas para el usuario y aplican para Terminal, Shells y aplicaciones que usan GUI. Cuandos se asignan variables en de esta manera, se debe reiniciar el sistema para que tengan efecto.
/etc/launchd.conf: | Estas variables son leidas por launchd cuando el sistema inicia y se loggea el usuario. Afectan a cada proceso que se ejecuta en el sistema, puesto que launchd es el proceso padre de los demás.

##Poniendo las variables en MacOSx 10.7 o inferior
Si usted esta usando MacOSX 10.7 o inferior, puede proceder a modificar el archivo ~/.MacOSX/environment.plist para incluir las variables de entorno que necesitará ejecutar desde su IDE.

##Poniendo las variables en MacOSx 10.8 o superior
Por otro lado, si desea poner las variables en un sistema MacOSx 10.8 o superior deberan hacerse usando launchd. Para ello, proceda a crear un nuevo archivo, puede llamarlo "environment.plist" y debe ubicarlo en ~/Library/LaunchAgents/. Posteriormente, agregue el siguiente contenido a este archivo:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
  <key>Label</key>
  <string>my.startup</string>
  <key>ProgramArguments</key>
  <array>
    <string>sh</string>
    <string>-c</string>
    <string>
    launchctl setenv USERNAME_NAME VALUE1
    launchctl setenv USERNAME_PASS VALUE2
    launchctl setenv SECRETKEY_USER VALUE3
    launchctl setenv SECRETKEY_PASS VALUE4
    </string>

  </array>
  <key>RunAtLoad</key>
  <true/>
</dict>
</plist>
```
Al finalizar únicamente debe reemplazar los valores y las variables por las que usted desee utilizar. Tenga en cuenta que deberá reiniciar el computador para que estas variables puedan ser cargadas por el sistema.

[Volver arriba](#funcionamiento-en-mac)


