package bsadd.TodoList.model;

public class Task {
	int id;
	String  title,text;

	public Task() {

	}

	public Task(int id, String title, String text) {
		this.title = title;
		this.text = text;
		this.id = id;		
	}

	public Task(String title, String text) {
		this.title = title;		
		this.text = text;
	}

	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}

	public int getID() {
		return id;
	}	

}
