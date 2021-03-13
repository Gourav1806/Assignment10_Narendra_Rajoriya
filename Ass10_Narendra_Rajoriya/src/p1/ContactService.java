package p1;
import java.io.*;
import java.util.*;

class Contact{
	private int  contactId;
	private String contactName;
	private String email;
	private List<String> contactNumber;
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(List<String> contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String toString(){
		return this.getContactId()+" : "+this.getContactName()+" : "+this.getEmail()+" : "+this.getContactNumber();
	}
	
}

public class ContactService{
	static List<Contact> contacts;
	static void display(List<Contact> contacts) {
		Iterator it = contacts.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	static void addContact(Contact contact,List<Contact> contacts) {
		contacts.add(contact);
	}
	public static void main(String[] args) {
		contacts = new ArrayList<Contact>();
		Contact contact = new Contact();
		contact.setContactId(5);
		contact.setContactName("Mohan");
		contact.setEmail("mohan@gmail.com");
		String contactNo[] = {"123456789","4567891234"};
		contact.setContactNumber(Arrays.asList(contactNo));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		addContact(contact,contacts);
		display(contacts);
	}
}

