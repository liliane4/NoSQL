package mds.ilyane.java;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInteraction {
	
	private Scanner scanner;
	private FireBaseUser dbUser;

	public UserInteraction(FireBaseUser dbUser) {
		this.scanner = new Scanner(System.in);
		this.dbUser = dbUser;
	}
	
	public void run() {
		while(true) {
			readCommand();
		}
		
	}
	
	private void readCommand() {
		String input = this.scanner.nextLine();
		
		try {
			switch(input.split(" ")[0]) {
			case "/log":
				this.connectUser(input);
				break;
			case "/show_users":
				this.showUsers();
				break;
				
			}
		}
		
	}
	
	private void connectUser(String input) {
		String[] args = input.split(" ");
		if(args.length < 2) {
			throw new IllegalArgumentException();
		}
		
		if(this.currentUser != null) {
			System.out.prinln("déjà connecté en " + this.currentUser);
			return;
		}
		
		String username = args[1];
		
		this.currentUser = new User(username);
		
		String newkey = this.dbUser.addUser(this.currentUser);
		this.currentUser.setKey(newkey);
		
		System.out.prinln("hello " + this.currentUser);
		
		}
	
	private void showUsers() {
		ArrayList<User> users = this.dbUser.getUsers();
		
		if(users.isEmpty()) {
			System.out.println("aucun utilisateur");
		}
		else {
			System.out.println("liste des utilisateurs");
			for(User user : users) {
				System.out.println(user);
			}
				
		}
	}
	
	

}
