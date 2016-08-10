package worms.model;

import java.util.ArrayList;

import worms.model.exceptions.IllegalNameException;
/**
 * A class that implements the teams of the game Worms
 * @date 11march2014
 * 
 * @author Matthijs Van keirsbilck, Bob Vanhoof
 * @version 1.2
 * 
 * @invar 	the team name is valid
 * 			| isValidName()
 * @invar	the worms in a team are valid
 * 			| isValidTeamMember()
 *
 */
public class Team {
	
	/** Create a team object that has a name and 0 worms in it
	 * 
	 * @param 	teamName
	 * 			the name to be initialized for this team (string)
	 * @post	the new team is initialized with 0 worms
	 * 			| new.getAllTeamMembers() == {}
	 * @post 	the new team is initialized with the given name
	 * 			| new.getName() == name
	 * @throws	IllegalNameException
	 * 			if the name is not valid
	 * 			| ! isValidName(name)
	 */
	public Team(String teamName) throws IllegalNameException {
		if (! isValidName(teamName))
			throw new IllegalNameException(teamName);
		this.name = teamName;
		this.wormList = new ArrayList<>();
	}
	
	// --- Name
	
	/**
	 * Variable stores the team name
	 */
	private final String name;
	
	/**
	 * Returns the name of this team
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Checks whether the team is a valid team
	 * @param 	name
	 * 			name of the team to be checked (String)
	 * @return	| result == (name.length()>2) && (name.charAt(0).matches("[A-Z]+")) && (name.matches("[A-Za-z]+"))
	 */
	public boolean isValidName(String name){
		boolean validLength = name.length() >= 2;
		if (! validLength)
			return false;
		String firstLetter= Character.toString(name.charAt(0));
		return (firstLetter.matches("[A-Z]+")) && name.matches("[A-Za-z]+");
	}
	
	// TEAM MEMBERS: CONNECTION: SLAVE
	
	/**
	 * Variable stores the all the Worms that are in this team
	 */
	private ArrayList<Worm> wormList; //all team objects are stored in this list
	
	/**
	 * Add a new member (worm) to this team
	 * @param 	worm
	 * 			Worm to be added to this team (Worm)
	 * @effect	| new.getAllTeamMembers().contains(worm)
	 */
	public void addTeamMember(Worm worm){
		if (!worm.isValidTeamMember())
			this.wormList.add(worm);
	}
	
	/**
	 * Remove a member (worm) from this team
	 * @param 	worm
	 * 			Worm to be removed from this team (Worm)
	 * @effect  | ! new.getAllTeamMebers().contains(worm)
	 */
	public void removeTeamMember(Worm worm){
		int index= this.wormList.indexOf(worm); //indexOf() and remove(index) instead of remove(object), because remove(object) doesn't shift the others and we get a null pointer exc.
		if (index >=0 ) { //wormlist contains this worm
			this.wormList.remove(index);
		}
	}
	
	/**
	 * Returns all the members of this Team
	 */
	public ArrayList<Worm> getAllTeamMembers(){
		return wormList;
	}
	
	// --- Terminated
	
	/**
	 * Variable holds whether or not this Team is terminated
	 */
	private boolean isTerminated = false;
	
	/**
	 * Returns whether or not this Team is terminated
	 */
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Terminates this Team
	 * @effect 	| new.isTerminated() == True
	 */
	public void terminate(){
		this.isTerminated = true;
	}
	
}
