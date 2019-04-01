package at.bibliothek.web;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import at.bibliothek.model.User;
import at.bibliothek.services.database.UserService;

@ManagedBean(name="loginBean")
@ViewScoped
public class LoginBean {
	
	@Inject
	private UserService userService;

	private String login;

	private String password;
	
	public LoginBean(){}
	
	
	public String login() {

		FacesContext context = FacesContext.getCurrentInstance();
		
		if (login == null || login.length() <= 2) {
			context.addMessage(null, new FacesMessage(
					"Your login must be at least two characters long"));

			return null;
		}else if (login.startsWith(":")){
			context.addMessage(null, new FacesMessage(
					"Your login cannot start with :"));

			return null;
		}

		User user = userService.findByUsernameOrEmail(this.getLogin(),
				this.getLogin());

		if (user != null) {
			ExternalContext extCtx = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest)extCtx.getRequest();

			try {
				request.login(this.getLogin(), this.getPassword());
				
			} catch (ServletException e) {
				try {
					request.logout();
				} catch (ServletException e1) {
					e1.printStackTrace();
				}
				context.addMessage(
						null,
						new FacesMessage("Login failed."
								+ e.getLocalizedMessage()));
				return null;
			}

			return "index"; // we return the default "index.xhtml" view here - could we instead redirect 
							// the user to the page they initially requested?

		} else {
			context.addMessage(null, new FacesMessage(" No user found for "
					+ this.getLogin()
					+ ". Please provide an existing username or email"));

			return null;
		}
	}

	public String logoff() {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		try {
			request.logout();
			request.getSession().invalidate();

		} catch (ServletException se ){
			context.addMessage(null, new FacesMessage("Logout failed."));
		}

		return "index.xhtml";
	}
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	
}

