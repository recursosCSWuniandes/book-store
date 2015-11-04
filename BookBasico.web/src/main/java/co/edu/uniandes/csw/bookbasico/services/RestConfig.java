package co.edu.uniandes.csw.bookbasico.services;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("webresources")
public class RestConfig extends ResourceConfig {

    public RestConfig() {
        packages("co.edu.uniandes.csw.bookbasico.services");
        packages("co.edu.uniandes.csw.bookbasico.providers");
        //packages("co.edu.uniandes.csw.auth.service"); //Se comenta ya que se sobrecargó
        packages("co.edu.uniandes.csw.auth.filter");
    }
}
