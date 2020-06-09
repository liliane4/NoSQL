package mds.ilyane.java;

public class app {
	public static void main(String[] args) {
		System.out.println("Bienvenue sur le messenger ");
		
		FireBaseUser dbUser = new FireBaseUser();
		UserInteraction messenger = new UserInteraction();
		
		messenger.run();
	}

}
