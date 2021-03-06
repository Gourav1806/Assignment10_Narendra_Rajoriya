package p1;
import java.io.*;
import java.util.*;
import java.sql.*;
class Contact implements Serializable{
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
class SortByName implements Comparator<Contact>{
	public int compare(Contact c1,Contact c2){
		return c1.getContactName().compareTo(c2.getContactName());
		
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
	static List<Contact> searchContactByNumber(String number,List<Contact> contacts)throws ContactNotFoundException{
		Iterator it = contacts.iterator();
		Contact con = new Contact();
		int t=0;
		List<Contact> ls = new ArrayList<Contact>();
		while(it.hasNext()) {
			con= (Contact)it.next();
			List<String> num = con.getContactNumber();
			for(int i=0;i<num.size();i++) {
				String s = num.get(i);
				if(s.contains(number)) {
					ls.add(con);
					t=1;
					break;
				}
			}
		}
		if(t==0)
			throw new ContactNotFoundException("No such contact no found");
		
		return ls;
		
	}
	static void addContactNumber(int contactId,String contactNo,List<Contact> contacts) {
		for(Contact con : contacts) {
			if(con.getContactId()==contactId) {
				List<String> ls = con.getContactNumber();
				ls.add(contactNo);
				break;
			}
		}
	}
	static void sortContactByName(List<Contact> contacts) {
		Collections.sort(contacts,new SortByName());
	}
	static void readContactFromFile(String fileName,List<Contact> contacts) {
		File file = new File(fileName);
		try {
		FileInputStream fi = new FileInputStream(file);
		byte[] arr = new byte[(int)file.length()];
		fi.read(arr);
		String str = new String(arr);
		String count[] = str.split("\n");
		for(int i=0;i<count.length;i++) {
			Contact con = new Contact();
			String[] data = count[i].split(",");
			con.setContactId(Integer.parseInt(data[0]));
			con.setContactName(data[1]);
			con.setEmail(data[2]);
			if(data.length>=4) {
				List<String> cno = new ArrayList<String>();
				for(int j=3;j<data.length;j++) {
					cno.add(data[j]);
				}
				con.setContactNumber(cno);
			}	
			contacts.add(con);
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	static void serializeContactDetails(List<Contact> contacts,String fileName) {
		
		try {
			File f = new File(fileName);
			if(f.exists()) {
				f.createNewFile();
				System.out.println("done");
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(contacts);
			oos.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	static List<Contact> deserializeContactDetails(String fileName){
		List<Contact> ls = new ArrayList<Contact>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
			ls=(ArrayList<Contact>)ois.readObject();
			ois.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
	static Set<Contact> populateContactFromDb(){
		Set<Contact> set = new HashSet<Contact>();
		Contact c ;
		List<String> ls ;
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactdb","root","1234");
		PreparedStatement ps = con.prepareStatement("select * from contact_tbl");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			c=new Contact();
			c.setContactId(rs.getInt(1));
			c.setContactName(rs.getString(2));
			c.setEmail(rs.getString(3));
			ls=new ArrayList<String>();
			String s = rs.getString(4);
			if(!(s==null)) {
				String[] cno = s.split(",");
				for(int i=0;i<cno.length;i++) {
					ls.add(cno[i]);
				}
			}
			c.setContactNumber(ls);
			set.add(c);	
		}
		}catch(ClassNotFoundException|SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	static Boolean addContacts(List<Contact> existingContact,Set<Contact> newContacts){
		existingContact.addAll(newContacts);
		return null;
	}
	public static void main(String[] args) {
		contacts = new ArrayList<Contact>();
		List<String> contactNo = new ArrayList<String>();
		Contact contact = new Contact();
		contact.setContactId(5);
		contact.setContactName("Mohan");
		contact.setEmail("mohan@gmail.com");
		contactNo.add("123456789");
		contactNo.add("4567891234");
		contact.setContactNumber(contactNo);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		addContact(contact,contacts);
		contactNo = new ArrayList<String>();
		contact=new Contact();
		contact.setContactId(6);
		contact.setContactName("Gohan");
		contact.setEmail("Gohan@gmail.com");
		contactNo.add("6754903212");
		contactNo.add("4567891234");
		contact.setContactNumber(contactNo);
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
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		try {
//			Contact con = searchContactByName("Gohan",contacts);
//			System.out.println(con);
//		}catch(ContactNotFoundException e) {
//			e.printStackTrace();
//		}
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		List<Contact> ls=new ArrayList<Contact>();
//		try {
//			ls=searchContactByNumber("34", contacts);
//		}catch(ContactNotFoundException e) {
//			e.printStackTrace();
//		}
//		display(ls);
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		addContactNumber(5, "9675112200", contacts);
//		display(contacts);
		
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		sortContactByName(contacts);
//		display(contacts);
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		readContactFromFile("C:\\Users\\NARENDRA\\git\\Assignment10_Narendra_Rajoriya\\Ass10_Narendra_Rajoriya\\TextFiles\\Contacts.txt", contacts);
		//display(contacts);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		serializeContactDetails(contacts, "C:\\Users\\NARENDRA\\git\\Assignment10_Narendra_Rajoriya\\Ass10_Narendra_Rajoriya\\TextFiles\\serialize_contacts.txt");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		List<Contact> ls=new ArrayList<Contact>();
		ls=deserializeContactDetails("C:\\Users\\NARENDRA\\git\\Assignment10_Narendra_Rajoriya\\Ass10_Narendra_Rajoriya\\TextFiles\\serialize_contacts.txt");
		display(ls);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Set<Contact> set = populateContactFromDb();
		for(Contact c : set)
			System.out.println(c);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		addContacts(contacts,set);
		display(contacts);
		
	}
}

