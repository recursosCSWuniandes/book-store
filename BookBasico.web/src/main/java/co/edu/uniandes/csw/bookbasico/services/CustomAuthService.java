package co.edu.uniandes.csw.bookbasico.services;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.service.AuthService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupList;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("users")
public class CustomAuthService extends AuthService {

    @Path("register")
    @POST
    @Override
    public Response register(UserDTO user) {
        Account acct = getClient().instantiate(Account.class);

        acct.setUsername(user.getUserName());
        acct.setPassword(user.getPassword());
        acct.setEmail(user.getEmail());
        acct.setGivenName(user.getName());
        acct.setSurname(user.getName());
        acct.setStatus(AccountStatus.ENABLED);

        Application application = getClient().getResource(getRealm().getApplicationRestUrl(), Application.class);
        GroupList groups = application.getGroups();
        for (Group grp : groups) {
            if (grp.getName().equals(user.getRole())) {
                acct = application.createAccount(acct);
                acct.addGroup(grp);
                break;
            }
        }
        return Response.ok().build();
    }

    @Override
    @POST
    @Path("login")
    public Response login(UserDTO user) {
        user.setRememberMe(true);
        return super.login(user);
    }

}
