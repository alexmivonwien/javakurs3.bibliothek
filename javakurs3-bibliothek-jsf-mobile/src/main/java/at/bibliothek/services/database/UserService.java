package at.bibliothek.services.database;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import at.bibliothek.model.Role;
import at.bibliothek.model.User;
import at.bibliothek.model.UserRole;
import at.bibliothek.model.enums.RoleType;



/**
 * a JPA Implementation
 * 
 * @author Baby
 *
 */

@Stateless(mappedName = "UserService")
public class UserService {

	@PersistenceContext
	private EntityManager em;

	
	public User createAndRegisterUser(String username, String email, String password) throws UserAlreadyExistsException {
		
		Set<RoleType> roleTypes = new HashSet<RoleType>();

		User newUser = new User();

		newUser.setPassword(password);
		newUser.setUsername(username);
		newUser.setEmail(email);

		roleTypes.add(RoleType.general);


		registerUser(newUser, roleTypes);
		
		return newUser;
	
	}


	//@Override
	public void registerUser(User user, Set<RoleType> roles) throws UserAlreadyExistsException{
		
		if (findByUsernameOrEmail (user.getUsername(), user.getEmail())!=null){
			throw new UserAlreadyExistsException();
		}
		
		this.em.persist(user);
		
		Set<Role> userRoles = findRoles(roles);
		
		for (Role role : userRoles){
			UserRole userRole = new UserRole();
			userRole.setUsername(user.getUsername());
			userRole.setUser_id(user.getId());
			
			userRole.setRole(role.getName());
			userRole.setRole_id(role.getId());
			
			this.em.persist(userRole);
		}
		
	};

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	//@Override
	public Set<Role> findRoles(Set<RoleType> roleTypes) {

		if (roleTypes == null || roleTypes.size() == 0) {
			return null;
		}

		final String DELIM = ", ";
		StringBuffer inRoles = new StringBuffer("(");

		for (RoleType roleType : roleTypes) {
			inRoles.append("'" + roleType.toString() +"'" + DELIM);
		}

		inRoles.delete(inRoles.lastIndexOf(DELIM), inRoles.length());
		inRoles.append(")");
		
		Query query = this.em.createQuery("from Role r where name in " + inRoles);
		
		List <Role> userRolesList = query.getResultList();
		Set<Role> result = new HashSet<Role>();
		
		for (Role userRole : userRolesList){
			result.add(userRole);
		}
		
		return result;
	}

	//@Override
	public User findByUsernameOrEmail (String username, String email)
	{
		
		String queryText = ( email == null 
							? " from User u where username =:NNAME" 
							: " from User u where username =:NNAME OR email =:EMAIL");
		
		Query query = this.em.createQuery(queryText);
		query.setParameter("NNAME", username);
		if (email!=null){
			query.setParameter("EMAIL", email);
		}
		
		List<User> users = query.getResultList();
		
		return users.size() == 0? null: users.get(0);
	};

	
	public User getUserById (String userId){
		
		return this.em.find(User.class, new Integer(userId));
	}

}

