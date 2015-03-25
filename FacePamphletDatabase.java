/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the dataMap.
	 */
	public FacePamphletDatabase() {
		dataMap = new HashMap<String, FacePamphletProfile>();
	}
	
	
	/** 
	 * This method adds the given profile to the dataMap.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the dataMap, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		dataMap.put(profile.getName(), profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the dataMap.  If there is no profile in the dataMap with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		if(dataMap.containsKey(name))
				return dataMap.get(name);
		else
			return null;
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the dataMap.  It also updates the list of friends of all
	 * other profiles in the dataMap to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the dataMap with the given name, then
	 * the dataMap is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if(dataMap.containsKey(name))
			{
				Iterator<String> it = dataMap.get(name).getFriends();
				while (it.hasNext())
				{
					dataMap.get(it.next()).removeFriend(name);
				}
				dataMap.remove(name);
			}
		
	}

	
	/** 
	 * This method returns true if there is a profile in the dataMap 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if(dataMap.containsKey(name))
			return true;
		else
			return false;
	}

	private HashMap<String, FacePamphletProfile> dataMap;
}
