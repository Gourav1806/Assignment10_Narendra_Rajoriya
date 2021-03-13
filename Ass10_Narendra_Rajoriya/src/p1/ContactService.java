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
class ContactNotFoundException extends Exception{
	ContactNotFoundException(){
		
	}
	ContactNotFoundException(String message){
		super(message);
	}
}
public class ContactService{
	static List<Contact> contacts;
	static void display(List<Contact> contacts) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Iterator it = contacts.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	static void addContact(Contact contact,List<Contact> contacts) {
		contacts.add(contact);
	}
	static void removeContact(String name,List<Contact> contacts) throws ContactNotFoundException{
		Iterator it = contacts.iterator();
		int t = 0;
		while(it.hasNext()) {
			Contact con = (Contact)it.next();
			String n1 = con.getContactName();
			if(n1.equals(name)) {
				t=1;
				contacts.remove(con);
				break;
			}
		}
		if(t==0)
			throw new ContactNotFoundException("there is no contact with entered name..");
	}
	static Contact searchContactByName(String name,List<Contact> contacts)throws ContactNotFoundException{
		Iterator it = contacts.iterator();
		Contact con=new Contact();
		int t = 0;
		while(it.hasNext()) {
			con = (Contact)it.next();
			String n1 = con.getContactName();
			if(n1.equals(name)) {
				t=1;
				break;
			}
		}
		if(t==0)
			throw new ContactNotFoundException("there is no contact with entered name..");
		return con;
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
		contact=new Contact();
		contact.setContactId(6);
		contact.setContactName("Gohan");
		contact.setEmail("Gohan@gmail.com");
		String contactN[] = {"6754903212","4567891234"};
		contact.setContactNumber(Arrays.asList(contactN));
		addContact(contact,contacts);
		display(contacts);
		
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		try {
//			removeContact("Sohan",contacts);
//		}catch(ContactNotFoundException e) {
//			e.printStackTrace();
//		}
//		display(contacts);
//		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		try {
			Contact con = searchContactByName("Gohan",contacts);
			System.out.println(con);
		}catch(ContactNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}

