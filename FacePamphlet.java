/* 
 * File: FacePamphlet.java

 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;


import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8287664203731178712L;

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public static void main(String[] args)
	{
		new FacePamphlet().start(args);
	}
	
	public void init() {
		
		setControls();
		database = new FacePamphletDatabase();
		 canvas = new FacePamphletCanvas();
		 add(canvas);
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
	public void actionPerformed(ActionEvent e) {
		//Creating, deleting and loading profiles
		
		if (e.getActionCommand().equals("Add")&&!nameField.getText().equals(""))
		{
			String name = nameField.getText();
			add(name);
		}
		if (e.getActionCommand().equals("Delete")&&!nameField.getText().equals(""))
		{
			String name = nameField.getText();
			delete(name);
		}
		if (e.getActionCommand().equals("Lookup")&&!nameField.getText().equals(""))
		{	
			String name = nameField.getText();
			lookup(name);
		}
				
		//Operations with concrete profile
		
		if (e.getActionCommand().equals("Change Status")&&!changeStatusField.getText().equals(""))
		{
			String name = changeStatusField.getText();
			changeStatus(name);
		}

		if (e.getActionCommand().equals("Change Picture")&&!changePictureField.getText().equals(""))
		{
			String name = changePictureField.getText();
			changePicture(name);
		}

		if (e.getActionCommand().equals("Add Friend")&&!friendField.getText().equals(""))
		{
			String name = friendField.getText();
			addFriend(name);
		}
		
		if (e.getActionCommand().equals("Remove Friend")&&!friendField.getText().equals(""))
		{
			String name = friendField.getText();
			removeFriend(name);
		}
		
	}
		
	
    private void setControls()
    {
    	//WEST control
		changeStatusField = new JTextField (TEXT_FIELD_SIZE);
		changeStatusField.addActionListener(this);
		changeStatusField.setActionCommand("Change Status");
		add(changeStatusField, WEST);
		add(new JButton("Change Status"), WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		changePictureField = new JTextField (TEXT_FIELD_SIZE);
		changePictureField.addActionListener(this);
		changePictureField.setActionCommand("Change Picture");
		add(changePictureField, WEST);
		add(new JButton("Change Picture"), WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		friendField = new JTextField (TEXT_FIELD_SIZE);
		add(friendField, WEST);
		add(new JButton("Add Friend"), WEST);
		add(new JButton("Remove Friend"), WEST);

		
		//NORTH control
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add(nameField, NORTH);
		
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		
		addActionListeners();

    }
    private JTextField changeStatusField;
    private JTextField friendField;
    private JTextField changePictureField;
    private JTextField nameField;
    private FacePamphletProfile profile;
    private FacePamphletDatabase database;
    private FacePamphletCanvas canvas;
    
    private void printCurrent()
    {
    	canvas.displayProfile(profile);
    }
    private void add(String name)
    {
    	
    	if(database.containsProfile(name))
		{
    		profile = database.getProfile(name);
    		printCurrent();
			canvas.showMessage("Profile with the name " + name + " already exists");
			
		}
		else
		{	
			database.addProfile(new FacePamphletProfile(name));
			profile = database.getProfile(name);
			printCurrent();
			canvas.showMessage("New profile created");
		}
		profile = database.getProfile(name);
    }
    private void delete(String name)
    {
    	if (database.containsProfile(name))
		{
			database.deleteProfile(name);
			profile = null;
			printCurrent();
			canvas.showMessage("Delete: profile of " +  name + " deleted");
		}
		else 
		{
			profile = null;
			printCurrent();
			canvas.showMessage("Delete: Profile with the name " + name + " does not exists");
		}
		
    }
    private void lookup(String name)
    {
    	if (database.containsProfile(name))
		{
			profile = database.getProfile(name);
			printCurrent();
			canvas.showMessage("Displaying " + profile.getName());
		}
		else 
		{
			profile = null;
			printCurrent();
			canvas.showMessage("Profile with name " + name + " does not exists");
		}
    }
    private void changeStatus(String name)
    {
    	if (profile!=null)
		{
			profile.setStatus(name);
			printCurrent();
			canvas.showMessage("Status updated to " + profile.getStatus());
		}
		else
			canvas.showMessage("No profile selected. Add new profile or look up for existing one");
    }
    private void changePicture(String name)
    {
    	if (profile!=null)
		{
			try
			{
				GImage im = new GImage(name);
				profile.setImage(im);
				printCurrent();
				canvas.showMessage("Picture updated");
			}
			catch (Exception ex)
			{
				canvas.showMessage("Unable to open image file: " + name);
			}
		}
		else
		{
			
			canvas.showMessage("No profile selected. Add new profile or lookup existing one");
		}
    }
    private void addFriend(String name)
    {
    	if (profile!=null)
		{
			if (database.containsProfile(name)&&!name.equals(profile.getName()))
			{
				
				if(profile.addFriend(name))
				{
					database.getProfile(name).addFriend(profile.getName());
					printCurrent();
					canvas.showMessage(name + " added as a friend");
				}
				else
					canvas.showMessage(name + " is already a friend of " + profile.getName());
			}
			else
				canvas.showMessage(name + " does not exist");
		}
		else
			canvas.showMessage("No profile selected. Add new profile or lookup existing one");
    }
    private void removeFriend(String name)
    {
    	if (profile!=null)
		{
			
			if (database.containsProfile(name)&&!name.equals(profile.getName()))
			{
				if(database.getProfile(name).removeFriend(profile.getName()))
				{
					profile.removeFriend(name);
					printCurrent();
					canvas.showMessage(name + " removed from friends");
				}
				else
					canvas.showMessage(name + " is not a friend of " + profile.getName());
			}
			else
				canvas.showMessage(name + " does not exist");
			}
		else
			canvas.showMessage("No profile selected. Add new profile or lookup existing one");
    }
    
}
