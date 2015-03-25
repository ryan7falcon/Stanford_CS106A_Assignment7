/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4254940513482494387L;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		message = new GLabel("");
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		if(this.getElementAt(getWidth()/2,  getHeight() - BOTTOM_MESSAGE_MARGIN) == message)
			remove(message);
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message,getWidth()/2 - message.getWidth()/2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile!=null)
		{
			displayName(profile);
			displayImage(profile);
			displayStatus(profile);
			displayFriends(profile);
		}
	}
	
	private GLabel name;
	private GCompound image;
	private GLabel status;
	private GLabel friendLabel;
	private ArrayList<GLabel> friends;
	private GLabel message;
	private void displayName(FacePamphletProfile profile){
		name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getAscent());
	}
	private void displayImage(FacePamphletProfile profile){
		image = new GCompound();
		GRect rect = new GRect(0,0,IMAGE_WIDTH,IMAGE_HEIGHT);
		image.add(rect);
		GLabel imageLabel = new GLabel("No image");
		imageLabel.setFont(PROFILE_IMAGE_FONT);
		image.add(imageLabel, IMAGE_WIDTH/2 - imageLabel.getWidth() / 2, IMAGE_HEIGHT / 2 + imageLabel.getAscent()/2);
		GImage im = profile.getImage();
		if (im!=null)
		{
			im.setSize(IMAGE_WIDTH - 1, IMAGE_HEIGHT - 1);
			image.add(im,1,1);
		}
		add(image,LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
	}
	private void displayStatus(FacePamphletProfile profile){
		if(profile.getStatus()!=null)
		{
			status = new GLabel(profile.getName() + " is " + profile.getStatus());
		}
		else
			status = new GLabel("No current status");
		status.setFont(PROFILE_STATUS_FONT);
		add(status,LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());			
	}
	private void displayFriends(FacePamphletProfile profile)
	{
		friendLabel = new GLabel("Friends:");
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendLabel, getWidth()/2, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
		
		friends = new ArrayList<GLabel>();
		Iterator<String> it = profile.getFriends();
		
		int i = 0;
		while(it.hasNext())
		{
			GLabel friend = new GLabel(it.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			friends.add(friend);
			add(friend, 6 * LEFT_MARGIN + IMAGE_WIDTH, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + 3 * friendLabel.getAscent()/2 + i * friend.getAscent());
			i++;
		}
	}
}
